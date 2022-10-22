/*********************************************************************
*                    SEGGER Microcontroller GmbH                     *
*                        The Embedded Experts                        *
**********************************************************************

-------------------------- END-OF-HEADER -----------------------------

File    : main.c
Purpose : Generic application start

*/

#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define NRF52810_XXAA
#include "nrf52810.h"
#include "nrf.h"

#define PIN_TXD         20
#define PIN_RXD         18
//#define PIN_TXD         6
//#define PIN_RXD         8
#define PIN_RED_LED     15
#define PIN_GREEN_LED   14
//#define PIN_RED_LED     17
//#define PIN_GREEN_LED   18
#define PIN_BLUE_LED    12
#define PIN_SWITCH1     28
#define PIN_SWITCH2     30
//#define PIN_SWITCH1     14
//#define PIN_SWITCH2     13
#define PIN_IR_TRANSMIT 6
#define PIN_IR_TRANSMIT_SECONDARY 9
#define PIN_IR_RECEIVE  4

#define N_IR_SIGNAL_TRANSITiON_DATAPOINTS 100

/* 1000 msec = 1 sec */
#define SLEEP_TIME_MS   500


void delay_ticks(uint32_t ticks)
{
  NRF_RTC0->TASKS_CLEAR = 1; // clear the counter to 0
  while(NRF_RTC0->COUNTER != 0); // the clear task will not execute right away. need to wait for the low frequency clock to have a transition for it to take effect.
  while(NRF_RTC0->COUNTER < ticks);
}


void test_rtc(void)
{
	while(1) {
		if(NRF_RTC0->COUNTER & (1 << 14)) {
			NRF_P0->OUTSET = (1 << PIN_RED_LED);
		}
		else {
			NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		}
	}
}



static char hello_world[] = "Hello World!\n";


void test_uart(void)
{
  static uint8_t state = 0;
	// Configure the UARTE with no flow control, one parity bit and 230400 baud rate
	NRF_UARTE0->CONFIG = 0; // no hardware flow control, no parity bit, one stop bit
	NRF_UARTE0->BAUDRATE = UARTE_BAUDRATE_BAUDRATE_Baud230400 << UARTE_BAUDRATE_BAUDRATE_Pos;
	
	// Select TX and RX pins
	NRF_UARTE0->PSEL.TXD = PIN_TXD;
	NRF_UARTE0->PSEL.RXD = PIN_RXD;
  
// Enable the UART (starts using the TX/RX pins)
	NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Enabled << UARTE_ENABLE_ENABLE_Pos;
  
  	while (1) {
                if(state) {
                  NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
                }
                else {
                  NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
                }
                state = 1 - state;
		// Configure transmit buffer and start the transfer
		NRF_UARTE0->TXD.MAXCNT = strlen(hello_world);
		NRF_UARTE0->TXD.PTR = (uint32_t)hello_world;
		NRF_UARTE0->TASKS_STARTTX = 1;
		// Wait until the transfer is complete
		NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		while(NRF_UARTE0->EVENTS_ENDTX == 0);
		NRF_P0->OUTSET = (1 << PIN_RED_LED);
                NRF_UARTE0->EVENTS_ENDTX = 0;
		// Stop the UART TX
//		NRF_UARTE0->TASKS_STOPTX = 1;
		// Wait until we receive the stopped event
//		while(NRF_UARTE0->EVENTS_TXSTOPPED == 0);
//                NRF_UARTE0->EVENTS_TXSTOPPED = 0;
		// A 100ms delay
		delay_ticks(10000);
		
		// Disable the UARTE (pins are now available for other use)
//		NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Disabled << UARTE_ENABLE_ENABLE_Pos;
	}
}



// an infinint while loop and inside we just make the red light on if the switch1 is on and we make the green led in if the switch2 is on
void test_buttons_and_leds(void)
{
  while(1)
  {
    delay_ticks(1000);

    if(NRF_P0->IN & (1 << PIN_SWITCH1))
    {
            printf("Green On\n");
            NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
    }
    else {
            printf("Green Off\n");
            NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
    }

    if(NRF_P0->IN & (1 << PIN_SWITCH2))
    {
            printf("Red On\n");
            NRF_P0->OUTCLR = (1 << PIN_RED_LED);
    }
    else {
            printf("Red Off\n");
            NRF_P0->OUTSET = (1 << PIN_RED_LED);
    }
  }
}


// the clock to the PWM is 16MHz and we have a prescalar of 1
#define PWM_PERIOD 421 // 38 kHz
#define PWM_50_PERCENT_DUTY_CYCLE (PWM_PERIOD / 2)
#define PWM_CH0_50_PERCENT_DUTY_CYCLE PWM_50_PERCENT_DUTY_CYCLE
static uint16_t pwm_seq0[] = {PWM_CH0_50_PERCENT_DUTY_CYCLE, 0, 0, 0};
static uint16_t pwm_seq1[] = {0, 0, 0, 0};

void init_pwm(void)
{
	NRF_PWM0->PSEL.OUT[0] = (PIN_RED_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
//	NRF_PWM0->PSEL.OUT[1] = (PIN_GREEN_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
	NRF_PWM0->ENABLE = (PWM_ENABLE_ENABLE_Enabled << PWM_ENABLE_ENABLE_Pos);
	NRF_PWM0->MODE = (PWM_MODE_UPDOWN_Up << PWM_MODE_UPDOWN_Pos);
	NRF_PWM0->PRESCALER = (PWM_PRESCALER_PRESCALER_DIV_1 << PWM_PRESCALER_PRESCALER_Pos);	
	NRF_PWM0->COUNTERTOP  = PWM_PERIOD;
	NRF_PWM0->LOOP = 1; // this means that it will run 2 sequences, which are sequences 0 and 1
	NRF_PWM0->DECODER = (PWM_DECODER_LOAD_Individual << PWM_DECODER_LOAD_Pos) | (PWM_DECODER_MODE_RefreshCount << PWM_DECODER_MODE_Pos); // will load all 4 PWM channel values at each step and the next step will be triggered automatically after the previous step has been completed
	NRF_PWM0->SEQ[0].PTR = (uint32_t)pwm_seq0;
	NRF_PWM0->SEQ[0].CNT = sizeof(pwm_seq0) / sizeof(uint16_t);
//	NRF_PWM0->SEQ[0].REFRESH = 38000;
	NRF_PWM0->SEQ[0].ENDDELAY = 0;
	NRF_PWM0->SEQ[1].PTR = (uint32_t)pwm_seq1;
	NRF_PWM0->SEQ[1].CNT = sizeof(pwm_seq1) / sizeof(uint16_t);
//	NRF_PWM0->SEQ[1].REFRESH = 38000;
	NRF_PWM0->SEQ[1].ENDDELAY = 0;
//	NRF_PWM0->TASKS_SEQSTART[0] = 1;
}


#define LEVEL1 0
#define LEVEL2 PWM_50_PERCENT_DUTY_CYCLE
#define LEVEL_DONT_CARE 0
#define PWM_CH0_DUTY_A LEVEL1
#define PWM_CH1_DUTY_A LEVEL2
#define PWM_CH2_DUTY_A LEVEL_DONT_CARE
#define PWM_CH3_DUTY_A LEVEL_DONT_CARE
#define PWM_CH0_DUTY_B LEVEL2
#define PWM_CH1_DUTY_B LEVEL1
#define PWM_CH2_DUTY_B LEVEL_DONT_CARE
#define PWM_CH3_DUTY_B LEVEL_DONT_CARE
#define LEVEL3 0
#define LEVEL4 PWM_50_PERCENT_DUTY_CYCLE
#define PWM_CH0_DUTY_C LEVEL3
#define PWM_CH1_DUTY_C LEVEL4
#define PWM_CH2_DUTY_C LEVEL_DONT_CARE
#define PWM_CH3_DUTY_C LEVEL_DONT_CARE
#define PWM_CH0_DUTY_D LEVEL4
#define PWM_CH1_DUTY_D LEVEL3
#define PWM_CH2_DUTY_D LEVEL_DONT_CARE
#define PWM_CH3_DUTY_D LEVEL_DONT_CARE

static void test_pwm(void)
{
	static uint16_t pwm_seq0[] = {PWM_CH0_DUTY_A, PWM_CH1_DUTY_A, PWM_CH2_DUTY_A, PWM_CH3_DUTY_A, PWM_CH0_DUTY_B, PWM_CH1_DUTY_B, PWM_CH2_DUTY_B, PWM_CH3_DUTY_B};
	static uint16_t pwm_seq1[] = {PWM_CH0_DUTY_C, PWM_CH1_DUTY_C, PWM_CH2_DUTY_C, PWM_CH3_DUTY_C, PWM_CH0_DUTY_D, PWM_CH1_DUTY_D, PWM_CH2_DUTY_D, PWM_CH3_DUTY_D};
	NRF_PWM0->PSEL.OUT[0] = (PIN_RED_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
	NRF_PWM0->PSEL.OUT[1] = (PIN_GREEN_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
	NRF_PWM0->ENABLE = (PWM_ENABLE_ENABLE_Enabled << PWM_ENABLE_ENABLE_Pos);
	NRF_PWM0->MODE = (PWM_MODE_UPDOWN_Up << PWM_MODE_UPDOWN_Pos);
	NRF_PWM0->PRESCALER = (PWM_PRESCALER_PRESCALER_DIV_1 << PWM_PRESCALER_PRESCALER_Pos);	
	NRF_PWM0->COUNTERTOP  = PWM_PERIOD;
//	NRF_PWM0->COUNTERTOP  = 0x7fff; // 488 Hz
	NRF_PWM0->LOOP = 1; // this means that it will run 2 sequences, which are sequences 0 and 1
	NRF_PWM0->DECODER = (PWM_DECODER_LOAD_Individual << PWM_DECODER_LOAD_Pos) | (PWM_DECODER_MODE_RefreshCount << PWM_DECODER_MODE_Pos); // will load all 4 PWM channel values at each step and the next step will be triggered automatically after the previous step has been completed
	NRF_PWM0->SEQ[0].PTR = (uint32_t)pwm_seq0;
	NRF_PWM0->SEQ[0].CNT = sizeof(pwm_seq0) / sizeof(uint16_t);
	NRF_PWM0->SEQ[0].REFRESH = 38000;
//	NRF_PWM0->SEQ[0].REFRESH = 400;
	NRF_PWM0->SEQ[0].ENDDELAY = 0;
	NRF_PWM0->SEQ[1].PTR = (uint32_t)pwm_seq1;
	NRF_PWM0->SEQ[1].CNT = sizeof(pwm_seq1) / sizeof(uint16_t);
	NRF_PWM0->SEQ[1].REFRESH = 38000;
	NRF_PWM0->SEQ[1].ENDDELAY = 0;
	NRF_PWM0->TASKS_SEQSTART[0] = 1;
	while(1);
}



void test_ir_receive(void)
{
	while(1) {
		if(NRF_P0->IN & (1 << PIN_IR_RECEIVE)) {
			NRF_P0->OUTSET = (1 << PIN_RED_LED);
		}
		else {
			NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		}
	}
}



/*********************************************************************
*
*       main()
*
*  Function description
*   Application entry point.
*/
int main(void) {
  volatile uint32_t i;

  NRF_CLOCK->TASKS_HFCLKSTART = 1;
  NRF_CLOCK->LFCLKSRC = (CLOCK_LFCLKSRC_SRC_Xtal << CLOCK_LFCLKSRC_SRC_Pos); // choose the 32kHz crystal as the source (rather than the RC oscillator)
  NRF_CLOCK->TASKS_LFCLKSTART = 1; // start the 32kHz clock

  NRF_P0->PIN_CNF[PIN_GREEN_LED] = 0x3;    // make this GPIO an output
  NRF_P0->OUTSET = (1 << PIN_RED_LED);
  NRF_P0->PIN_CNF[PIN_RED_LED] = 0x3;      // make this GPIO an output
  NRF_P0->PIN_CNF[PIN_IR_RECEIVE] = 0x0;   // make this GPIO an input
  NRF_P0->PIN_CNF[PIN_IR_TRANSMIT] = 0x3;  // make this GPIO an output
  NRF_P0->OUTCLR = (1 << PIN_IR_TRANSMIT); // make sure that the IR LED is off initially
  NRF_P0->OUTSET = (1 << PIN_RED_LED);     // set high to turn off the red LED
  NRF_P0->OUTSET = (1 << PIN_GREEN_LED);   // set high to turn off the green LED
  NRF_P0->PIN_CNF[PIN_SWITCH1] = 0x0;      // make this GPIO an input
  NRF_P0->PIN_CNF[PIN_SWITCH2] = 0x0;      // make this GPIO an input

  while (NRF_CLOCK->EVENTS_LFCLKSTARTED == 0); // wait for the 32kHz clock to start
  NRF_RTC0->TASKS_START = 1; // start the Real time counter

  delay_ticks(16000);

  test_uart();
  test_buttons_and_leds();

}

/*************************** End of file ****************************/


#if 0
#include <stdio.h>
#include <stdlib.h>
#define NRF52810_XXAA
#include "nrf52810.h"
#include "nrf.h"

//#include <zephyr.h>
//#include <drivers/gpio.h>

#ifdef TEST_BLUETOOTH
	#include <zephyr/types.h>
	#include <stddef.h>
	#include <sys/printk.h>
	#include <sys/util.h>
	#include <bluetooth/bluetooth.h>
	#include <bluetooth/hci.h>
#endif

//#define PIN_TXD         20
//#define PIN_RXD         18
#define PIN_TXD         6
#define PIN_RXD         8
//#define PIN_RED_LED     15
//#define PIN_GREEN_LED   14
#define PIN_RED_LED     17
#define PIN_GREEN_LED   18
#define PIN_BLUE_LED    12
//#define PIN_SWITCH1     28
//#define PIN_SWITCH2     30
#define PIN_SWITCH1     14
#define PIN_SWITCH2     13
#define PIN_IR_TRANSMIT 6
#define PIN_IR_TRANSMIT_SECONDARY 9
#define PIN_IR_RECEIVE  4

#define N_IR_SIGNAL_TRANSITiON_DATAPOINTS 100

/* 1000 msec = 1 sec */
#define SLEEP_TIME_MS   500

static uint8_t hello_world[] = "Hello World!\n";

/* The devicetree node identifier for the "led0" alias. */
//#define LED0_NODE DT_ALIAS(led0)

/*
 * A build error on this line means your board is unsupported.
 * See the sample documentation for information on how to fix this.
 */
//static const struct gpio_dt_spec led0 = GPIO_DT_SPEC_GET(LED0_NODE, gpios);


void delay_ticks(uint32_t ticks)
{
	NRF_RTC0->TASKS_CLEAR = 1; // clear the counter to 0
	while(NRF_RTC0->COUNTER < ticks);
}


// the clock to the PWM is 16MHz and we have a prescalar of 1
#define PWM_PERIOD 421 // 38 kHz
#define PWM_50_PERCENT_DUTY_CYCLE (PWM_PERIOD / 2)
#define PWM_CH0_50_PERCENT_DUTY_CYCLE PWM_50_PERCENT_DUTY_CYCLE
static uint16_t pwm_seq0[] = {PWM_CH0_50_PERCENT_DUTY_CYCLE, 0, 0, 0};
static uint16_t pwm_seq1[] = {0, 0, 0, 0};

void init_pwm(void)
{
	NRF_PWM0->PSEL.OUT[0] = (PIN_RED_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
//	NRF_PWM0->PSEL.OUT[1] = (PIN_GREEN_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
	NRF_PWM0->ENABLE = (PWM_ENABLE_ENABLE_Enabled << PWM_ENABLE_ENABLE_Pos);
	NRF_PWM0->MODE = (PWM_MODE_UPDOWN_Up << PWM_MODE_UPDOWN_Pos);
	NRF_PWM0->PRESCALER = (PWM_PRESCALER_PRESCALER_DIV_1 << PWM_PRESCALER_PRESCALER_Pos);	
	NRF_PWM0->COUNTERTOP  = PWM_PERIOD;
	NRF_PWM0->LOOP = 1; // this means that it will run 2 sequences, which are sequences 0 and 1
	NRF_PWM0->DECODER = (PWM_DECODER_LOAD_Individual << PWM_DECODER_LOAD_Pos) | (PWM_DECODER_MODE_RefreshCount << PWM_DECODER_MODE_Pos); // will load all 4 PWM channel values at each step and the next step will be triggered automatically after the previous step has been completed
	NRF_PWM0->SEQ[0].PTR = (uint32_t)pwm_seq0;
	NRF_PWM0->SEQ[0].CNT = sizeof(pwm_seq0) / sizeof(uint16_t);
//	NRF_PWM0->SEQ[0].REFRESH = 38000;
	NRF_PWM0->SEQ[0].ENDDELAY = 0;
	NRF_PWM0->SEQ[1].PTR = (uint32_t)pwm_seq1;
	NRF_PWM0->SEQ[1].CNT = sizeof(pwm_seq1) / sizeof(uint16_t);
//	NRF_PWM0->SEQ[1].REFRESH = 38000;
	NRF_PWM0->SEQ[1].ENDDELAY = 0;
//	NRF_PWM0->TASKS_SEQSTART[0] = 1;
}


#define LEVEL1 0
#define LEVEL2 PWM_50_PERCENT_DUTY_CYCLE
#define LEVEL_DONT_CARE 0
#define PWM_CH0_DUTY_A LEVEL1
#define PWM_CH1_DUTY_A LEVEL2
#define PWM_CH2_DUTY_A LEVEL_DONT_CARE
#define PWM_CH3_DUTY_A LEVEL_DONT_CARE
#define PWM_CH0_DUTY_B LEVEL2
#define PWM_CH1_DUTY_B LEVEL1
#define PWM_CH2_DUTY_B LEVEL_DONT_CARE
#define PWM_CH3_DUTY_B LEVEL_DONT_CARE
#define LEVEL3 0
#define LEVEL4 PWM_50_PERCENT_DUTY_CYCLE
#define PWM_CH0_DUTY_C LEVEL3
#define PWM_CH1_DUTY_C LEVEL4
#define PWM_CH2_DUTY_C LEVEL_DONT_CARE
#define PWM_CH3_DUTY_C LEVEL_DONT_CARE
#define PWM_CH0_DUTY_D LEVEL4
#define PWM_CH1_DUTY_D LEVEL3
#define PWM_CH2_DUTY_D LEVEL_DONT_CARE
#define PWM_CH3_DUTY_D LEVEL_DONT_CARE

static void test_pwm(void)
{
	static uint16_t pwm_seq0[] = {PWM_CH0_DUTY_A, PWM_CH1_DUTY_A, PWM_CH2_DUTY_A, PWM_CH3_DUTY_A, PWM_CH0_DUTY_B, PWM_CH1_DUTY_B, PWM_CH2_DUTY_B, PWM_CH3_DUTY_B};
	static uint16_t pwm_seq1[] = {PWM_CH0_DUTY_C, PWM_CH1_DUTY_C, PWM_CH2_DUTY_C, PWM_CH3_DUTY_C, PWM_CH0_DUTY_D, PWM_CH1_DUTY_D, PWM_CH2_DUTY_D, PWM_CH3_DUTY_D};
	NRF_PWM0->PSEL.OUT[0] = (PIN_RED_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
	NRF_PWM0->PSEL.OUT[1] = (PIN_GREEN_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
	NRF_PWM0->ENABLE = (PWM_ENABLE_ENABLE_Enabled << PWM_ENABLE_ENABLE_Pos);
	NRF_PWM0->MODE = (PWM_MODE_UPDOWN_Up << PWM_MODE_UPDOWN_Pos);
	NRF_PWM0->PRESCALER = (PWM_PRESCALER_PRESCALER_DIV_1 << PWM_PRESCALER_PRESCALER_Pos);	
	NRF_PWM0->COUNTERTOP  = PWM_PERIOD;
//	NRF_PWM0->COUNTERTOP  = 0x7fff; // 488 Hz
	NRF_PWM0->LOOP = 1; // this means that it will run 2 sequences, which are sequences 0 and 1
	NRF_PWM0->DECODER = (PWM_DECODER_LOAD_Individual << PWM_DECODER_LOAD_Pos) | (PWM_DECODER_MODE_RefreshCount << PWM_DECODER_MODE_Pos); // will load all 4 PWM channel values at each step and the next step will be triggered automatically after the previous step has been completed
	NRF_PWM0->SEQ[0].PTR = (uint32_t)pwm_seq0;
	NRF_PWM0->SEQ[0].CNT = sizeof(pwm_seq0) / sizeof(uint16_t);
	NRF_PWM0->SEQ[0].REFRESH = 38000;
//	NRF_PWM0->SEQ[0].REFRESH = 400;
	NRF_PWM0->SEQ[0].ENDDELAY = 0;
	NRF_PWM0->SEQ[1].PTR = (uint32_t)pwm_seq1;
	NRF_PWM0->SEQ[1].CNT = sizeof(pwm_seq1) / sizeof(uint16_t);
	NRF_PWM0->SEQ[1].REFRESH = 38000;
	NRF_PWM0->SEQ[1].ENDDELAY = 0;
	NRF_PWM0->TASKS_SEQSTART[0] = 1;
	while(1);
}


// an infinint while loop and inside we just make the red light on if the switch1 is on and we make the green led in if the switch2 is on
void test_buttons_and_leds(void)
{
	while(1)
	{
		if(NRF_P0->IN & (1 << PIN_SWITCH1))
		{
			NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
		}
                else {
      			NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
                }

		if(NRF_P0->IN & (1 << PIN_SWITCH2))
		{
			NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
		}
                else {
                        NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
                }
        }
}


void test_ir_receive(void)
{
	while(1) {
		if(NRF_P0->IN & (1 << PIN_IR_RECEIVE)) {
			NRF_P0->OUTSET = (1 << PIN_RED_LED);
		}
		else {
			NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		}
	}
}


void test_rtc(void)
{
	while(1) {
		if(NRF_RTC0->COUNTER & (1 << 14)) {
			NRF_P0->OUTSET = (1 << PIN_RED_LED);
		}
		else {
			NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		}
	}
}


void test_uart(void)
{
	// Configure the UARTE with no flow control, one parity bit and 230400 baud rate
	NRF_UARTE0->CONFIG = 0; // no hardware flow control, no parity bit, one stop bit
	NRF_UARTE0->BAUDRATE = UARTE_BAUDRATE_BAUDRATE_Baud230400 << UARTE_BAUDRATE_BAUDRATE_Pos;
	
	// Select TX and RX pins
	NRF_UARTE0->PSEL.TXD = PIN_TXD;
	NRF_UARTE0->PSEL.RXD = PIN_RXD;
  
// Enable the UART (starts using the TX/RX pins)
	NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Enabled << UARTE_ENABLE_ENABLE_Pos;
  
  	while (1) {		
		NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
		// Configure transmit buffer and start the transfer
		NRF_UARTE0->TXD.MAXCNT = sizeof(hello_world);
		NRF_UARTE0->TXD.PTR = (uint32_t)&hello_world[1];
		NRF_UARTE0->TASKS_STARTTX = 1;
		// Wait until the transfer is complete
		while(NRF_UARTE0->EVENTS_ENDTX == 0);
		// A 100ms delay
		delay_ticks(10000);
		NRF_P0->OUTSET = (1 << PIN_RED_LED);
		NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
		// Stop the UART TX
		NRF_UARTE0->TASKS_STOPTX = 1;
		// Wait until we receive the stopped event
		while(NRF_UARTE0->EVENTS_TXSTOPPED == 0);
		
		// Disable the UARTE (pins are now available for other use)
//		NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Disabled << UARTE_ENABLE_ENABLE_Pos;

//		delay_ticks(8000);
//		delay_ticks(8000);
	}
}


uint16_t captured_ir_signal_transition_time[N_IR_SIGNAL_TRANSITiON_DATAPOINTS];
uint16_t captured_ir_signal_transition_time_index = 0;
uint8_t wait_for_falling_edge = 1;

void record_ir_pattern(void)
{
  	while (1) {
		if( (wait_for_falling_edge == 1) && ((NRF_P0->IN & (1 << PIN_IR_RECEIVE)) == 0) ) {
			if(captured_ir_signal_transition_time_index == 0) {
				NRF_RTC0->TASKS_CLEAR = 1; // clear the counter to 0
			}
			else {
				captured_ir_signal_transition_time[captured_ir_signal_transition_time_index++] = NRF_RTC0->COUNTER;
			}
			wait_for_falling_edge = 0;
			NRF_P0->OUTCLR = (1 << PIN_RED_LED);
		}
		else if( (wait_for_falling_edge == 0) && ((NRF_P0->IN & (1 << PIN_IR_RECEIVE)) != 0) ) {
			captured_ir_signal_transition_time[captured_ir_signal_transition_time_index++] = NRF_RTC0->COUNTER;
			wait_for_falling_edge = 1;
			NRF_P0->OUTSET = (1 << PIN_RED_LED);
		}
		if(captured_ir_signal_transition_time_index == N_IR_SIGNAL_TRANSITiON_DATAPOINTS) {
			NRF_P0->OUTSET = (1 << PIN_RED_LED);
			break;
		}
	}
}


void playback_ir_pattern(void)
{
	uint32_t i;
	uint16_t time_on = 0;
	uint16_t time_off = 0;
	uint32_t time_calculation;

	for(i = 0; i < 5; i++) {
		static uint16_t pwm_seq0[] = {PWM_CH0_DUTY_A, PWM_CH1_DUTY_A, PWM_CH2_DUTY_A, PWM_CH3_DUTY_A, PWM_CH0_DUTY_B, PWM_CH1_DUTY_B, PWM_CH2_DUTY_B, PWM_CH3_DUTY_B};
		NRF_PWM0->PSEL.OUT[0] = (PIN_RED_LED << PWM_PSEL_OUT_PIN_Pos) | (PWM_PSEL_OUT_CONNECT_Connected << PWM_PSEL_OUT_CONNECT_Pos);
		NRF_PWM0->ENABLE = (PWM_ENABLE_ENABLE_Enabled << PWM_ENABLE_ENABLE_Pos);
		NRF_PWM0->MODE = (PWM_MODE_UPDOWN_Up << PWM_MODE_UPDOWN_Pos);
		NRF_PWM0->PRESCALER = (PWM_PRESCALER_PRESCALER_DIV_1 << PWM_PRESCALER_PRESCALER_Pos);	
		NRF_PWM0->COUNTERTOP  = PWM_PERIOD;
		NRF_PWM0->LOOP = 0; // this means that it will run 2 sequences, which are sequences 0 and 1
		NRF_PWM0->DECODER = (PWM_DECODER_LOAD_Individual << PWM_DECODER_LOAD_Pos) | (PWM_DECODER_MODE_RefreshCount << PWM_DECODER_MODE_Pos); // will load all 4 PWM channel values at each step and the next step will be triggered automatically after the previous step has been completed
		NRF_PWM0->SEQ[0].PTR = (uint32_t)pwm_seq0;
		NRF_PWM0->SEQ[0].CNT = sizeof(pwm_seq0) / sizeof(uint16_t);
		NRF_PWM0->SEQ[0].REFRESH = 38000;
		NRF_PWM0->SEQ[0].ENDDELAY = 0;
		NRF_PWM0->TASKS_SEQSTART[0] = 1;
		while(1);
	}

	return;

	for(i = 0; i < captured_ir_signal_transition_time_index; i += 2) {
		if(i == 0) {
			time_on = captured_ir_signal_transition_time[0];
		}
		else {
			time_on = captured_ir_signal_transition_time[i + 1] - captured_ir_signal_transition_time[i];
		}
		if(i + 2 >= captured_ir_signal_transition_time_index) {
			time_off = 0;
		}
		else {
			time_off = captured_ir_signal_transition_time[i + 2] - captured_ir_signal_transition_time[i + 1];
		}
		time_calculation = (uint32_t)time_on * 38000;
		time_calculation = time_calculation / 32768; 
//		NRF_PWM0->SEQ[0].REFRESH = time_calculation;
		NRF_PWM0->SEQ[0].REFRESH = 38000;
		time_calculation = (uint32_t)time_off * 38000;
		time_calculation = time_calculation / 32768; 
//		NRF_PWM0->SEQ[1].REFRESH = time_calculation;
		NRF_PWM0->SEQ[1].REFRESH = 38000;


		NRF_PWM0->LOOP = 1; // this means that it will run 2 sequences, which are sequences 0 and 1
		NRF_PWM0->DECODER = (PWM_DECODER_LOAD_Individual << PWM_DECODER_LOAD_Pos) | (PWM_DECODER_MODE_RefreshCount << PWM_DECODER_MODE_Pos); // will load all 4 PWM channel values at each step and the next step will be triggered automatically after the previous step has been completed
		NRF_PWM0->SEQ[0].PTR = (uint32_t)pwm_seq0;
		NRF_PWM0->SEQ[0].CNT = sizeof(pwm_seq0) / sizeof(uint16_t);
		NRF_PWM0->SEQ[0].ENDDELAY = 0;
		NRF_PWM0->SEQ[1].PTR = (uint32_t)pwm_seq1;
		NRF_PWM0->SEQ[1].CNT = sizeof(pwm_seq1) / sizeof(uint16_t);
		NRF_PWM0->SEQ[1].ENDDELAY = 0;


		NRF_PWM0->EVENTS_SEQEND[1] = 1;
		NRF_PWM0->TASKS_SEQSTART[0] = 1;
		while(NRF_PWM0->EVENTS_SEQEND[1] == 0);
		delay_ticks(32000);
	}
}




#ifdef TEST_BLUETOOTH


#define DEVICE_NAME CONFIG_BT_DEVICE_NAME
#define DEVICE_NAME_LEN (sizeof(DEVICE_NAME) - 1)

/*
 * Set Advertisement data. Based on the Eddystone specification:
 * https://github.com/google/eddystone/blob/master/protocol-specification.md
 * https://github.com/google/eddystone/tree/master/eddystone-url
 */
static const struct bt_data ad[] = {
	BT_DATA_BYTES(BT_DATA_FLAGS, BT_LE_AD_NO_BREDR),
	BT_DATA_BYTES(BT_DATA_UUID16_ALL, 0xaa, 0xfe),
	BT_DATA_BYTES(BT_DATA_SVC_DATA16,
		      0xaa, 0xfe, /* Eddystone UUID */
		      0x10, /* Eddystone-URL frame type */
		      0x00, /* Calibrated Tx power at 0m */
		      0x00, /* URL Scheme Prefix http://www. */
		      'z', 'e', 'p', 'h', 'y', 'r',
		      'p', 'r', 'o', 'j', 'e', 'c', 't',
		      0x08) /* .org */
};

/* Set Scan Response data */
static const struct bt_data sd[] = {
	BT_DATA(BT_DATA_NAME_COMPLETE, DEVICE_NAME, DEVICE_NAME_LEN),
};

static void bt_ready(int err)
{
	char addr_s[BT_ADDR_LE_STR_LEN];
	bt_addr_le_t addr = {0};
	size_t count = 1;

	if (err) {
		printk("Bluetooth init failed (err %d)\n", err);
		return;
	}

	printk("Bluetooth initialized\n");

	/* Start advertising */
	err = bt_le_adv_start(BT_LE_ADV_NCONN_IDENTITY, ad, ARRAY_SIZE(ad),
			      sd, ARRAY_SIZE(sd));
	if (err) {
		printk("Advertising failed to start (err %d)\n", err);
		return;
	}


	/* For connectable advertising you would use
	 * bt_le_oob_get_local().  For non-connectable non-identity
	 * advertising an non-resolvable private address is used;
	 * there is no API to retrieve that.
	 */

	bt_id_get(&addr, &count);
	bt_addr_le_to_str(&addr, addr_s, sizeof(addr_s));

	printk("Beacon started, advertising as %s\n", addr_s);
}

void test_bluetooth(void)
{
	int err;

	printk("Starting Beacon Demo\n");

	/* Initialize the Bluetooth Subsystem */
	err = bt_enable(bt_ready);
	if (err) {
		printk("Bluetooth init failed (err %d)\n", err);
	}
	while(1);
}


#endif






int main(void)
{
	volatile uint32_t i;

        printf("Hello World!\n");
        while(1);

	NRF_CLOCK->TASKS_HFCLKSTART = 1;
	NRF_CLOCK->LFCLKSRC = (CLOCK_LFCLKSRC_SRC_Xtal << CLOCK_LFCLKSRC_SRC_Pos); // choose the 32kHz crystal as the source (rather than the RC oscillator)
	NRF_CLOCK->TASKS_LFCLKSTART = 1; // start the 32kHz clock

	NRF_P0->PIN_CNF[PIN_GREEN_LED] = 0x3;    // make this GPIO an output
	NRF_P0->OUTSET = (1 << PIN_RED_LED);
	NRF_P0->PIN_CNF[PIN_RED_LED] = 0x3;      // make this GPIO an output
	NRF_P0->PIN_CNF[PIN_IR_RECEIVE] = 0x0;   // make this GPIO an input
	NRF_P0->PIN_CNF[PIN_IR_TRANSMIT] = 0x3;  // make this GPIO an output
	NRF_P0->OUTCLR = (1 << PIN_IR_TRANSMIT); // make sure that the IR LED is off initially
	NRF_P0->OUTSET = (1 << PIN_RED_LED);     // set high to turn off the red LED
	NRF_P0->OUTSET = (1 << PIN_GREEN_LED);   // set high to turn off the green LED
	NRF_P0->PIN_CNF[PIN_SWITCH1] = 0x0;      // make this GPIO an input
	NRF_P0->PIN_CNF[PIN_SWITCH2] = 0x0;      // make this GPIO an input

	while (NRF_CLOCK->EVENTS_LFCLKSTARTED == 0); // wait for the 32kHz clock to start
	NRF_RTC0->TASKS_START = 1; // start the Real time counter

	delay_ticks(32000);

	test_buttons_and_leds();
//	test_pwm();
#ifdef TEST_BLUETOOTH
	test_bluetooth();
#endif
	test_uart();
//	test_ir_receive();
//	test_rtc();
//	init_pwm();


	// Configure the UARTE with no flow control, one parity bit and 230400 baud rate
	NRF_UARTE0->CONFIG = (UART_CONFIG_HWFC_Disabled   << UART_CONFIG_HWFC_Pos) |
						(UART_CONFIG_PARITY_Included << UART_CONFIG_PARITY_Pos); 
	
	NRF_UARTE0->BAUDRATE = UARTE_BAUDRATE_BAUDRATE_Baud230400 << UARTE_BAUDRATE_BAUDRATE_Pos;
	
	// Select TX and RX pins
	NRF_UARTE0->PSEL.TXD = PIN_TXD;
	NRF_UARTE0->PSEL.RXD = PIN_RXD;
  /*
// Enable the UART (starts using the TX/RX pins)
	NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Enabled << UARTE_ENABLE_ENABLE_Pos;
  
  	while (1) {		
		// Configure transmit buffer and start the transfer
		NRF_UARTE0->TXD.MAXCNT = sizeof(hello_world);
		NRF_UARTE0->TXD.PTR = (uint32_t)&hello_world[0];
		NRF_UARTE0->TASKS_STARTTX = 1;
		// Wait until the transfer is complete
		while(NRF_UARTE0->EVENTS_ENDTX == 0);
		// Stop the UART TX
		NRF_UARTE0->TASKS_STOPTX = 1;
		// Wait until we receive the stopped event
		while(NRF_UARTE0->EVENTS_TXSTOPPED == 0);
		
		// Disable the UARTE (pins are now available for other use)
//		NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Disabled << UARTE_ENABLE_ENABLE_Pos;

		NRF_P0->OUTSET = (1 << 15);
		NRF_P0->OUTSET = (1 << 14);
		for(i = 0; i < 1000000; i++);
//		k_msleep(SLEEP_TIME_MS);
		NRF_P0->OUTCLR = (1 << 15);
		NRF_P0->OUTCLR = (1 << 14);
//		k_msleep(SLEEP_TIME_MS);
		for(i = 0; i < 1000000; i++);
	}
*/
	while(1)
	{
		if(NRF_P0->IN & (1 << PIN_SWITCH1))
		{
			NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
			record_ir_pattern();
			NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
		}

		if(NRF_P0->IN & (1 << PIN_SWITCH2))
		{
			NRF_P0->OUTCLR = (1 << PIN_GREEN_LED);
			playback_ir_pattern();
			NRF_P0->OUTSET = (1 << PIN_GREEN_LED);
		}
	}
}


/*
void main(void)
{
//	int ret;
	#define RECEIVE_BUFFER_SIZE 100
	#define TRANSMIT_BUFFER_SIZE 100
	static uint8_t receive_buffer[RECEIVE_BUFFER_SIZE];
	static uint8_t transmit_buffer[TRANSMIT_BUFFER_SIZE];
	volatile uint32_t i;

	NRF_CLOCK->TASKS_HFCLKSTART = 1;
//	if (!device_is_ready(led0.port)) {
//		return;
//	}

//	ret = gpio_pin_configure_dt(&led0, GPIO_OUTPUT_ACTIVE);
//	if (ret < 0) {
//		return;
//	}

//	NRF_P0->DIR = (1 << 14) | (1 << 15);
	NRF_P0->PIN_CNF[14] = 0x3; // make this GPIO an output
	NRF_P0->PIN_CNF[15] = 0x3; // make this GPIO an output


	NRF_UARTE0->BAUDRATE = UARTE_BAUDRATE_BAUDRATE_Baud230400 << UARTE_BAUDRATE_BAUDRATE_Pos; // set to 230400 bps (actual is 231884)
//	NRF_UARTE0->RXD.PTR = (uint32_t)&receive_buffer[0];
//	NRF_UARTE0->TXD.PTR = (uint32_t)&transmit_buffer[0];
//	NRF_UARTE0->RXD.MAXCNT = RECEIVE_BUFFER_SIZE;
//	NRF_UARTE0->TXD.MAXCNT = TRANSMIT_BUFFER_SIZE;
	NRF_UARTE0->CONFIG = 0; // no hardware flow control, no parity bit, one stop bit
	NRF_UARTE0->PSEL.TXD = PIN_TXD; // select GPIO pin 20 for the TX line
	NRF_UARTE0->PSEL.RXD = PIN_RXD; // select GPIO pin 18 for the RX line
	NRF_UARTE0->ENABLE = UARTE_ENABLE_ENABLE_Enabled << UARTE_ENABLE_ENABLE_Pos; // enable the UART
  
	while (1) {
//		ret = gpio_pin_toggle_dt(&led0);
		NRF_P0->OUTSET = (1 << 15);
		NRF_P0->OUTSET = (1 << 14);
		for(i = 0; i < 1000000; i++);
//		k_msleep(SLEEP_TIME_MS);
		NRF_P0->OUTCLR = (1 << 15);
		NRF_P0->OUTCLR = (1 << 14);
//		k_msleep(SLEEP_TIME_MS);
		for(i = 0; i < 1000000; i++);
		transmit_buffer[0] = 'A';
		transmit_buffer[1] = 'B';


  


		NRF_UARTE0->EVENTS_ENDTX = 0;
		NRF_UARTE0->TXD.PTR = (uint32_t)&transmit_buffer[0];
		NRF_UARTE0->TXD.MAXCNT = 2;
//		NRF_UARTE0->TASKS_STARTTX = 1;

	    NRF_UARTE0->TASKS_STARTRX = 1;
	    NRF_UARTE0->TASKS_STARTTX = 1;
    	while(NRF_UARTE0->EVENTS_ENDTX == 0);
	    NRF_UARTE0->EVENTS_ENDTX = 0x0UL;

	}
}
*/
#endif