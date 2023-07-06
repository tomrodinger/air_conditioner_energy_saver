#ifndef BSP_H
#define BSP_H

/**
 * @brief Pinout definition
 */
#ifdef BOARD_PCA10040
  #define PIN_TXD         6
  #define PIN_RXD         8
  
  #define PIN_RED_LED     17
  #define PIN_GREEN_LED   18
  #define PIN_BLUE_LED    19

  #define PIN_SWITCH1     13
  #define PIN_SWITCH2     14
#else
  #define PIN_TXD         20
  #define PIN_RXD         18

  #define PIN_RED_LED     15
  #define PIN_GREEN_LED   14
  #define PIN_BLUE_LED    12

  #define PIN_SWITCH1     28
  #define PIN_SWITCH2     30
#endif

#define PIN_IR_TRANSMIT 6
#define PIN_IR_RECEIVE  4
#define PIN_IR_RECEIVER_POWER_CONTROL 2

/**
 * @brief Initialize device peripherals
 */
void bsp_init(void);

/**
 * @brief Turn on red LED
 */
void red_led_on(void);

/**
 * @brief Turn off red LED
 */
void red_led_off(void);

/**
 * @brief Toggle red LED
 */
void red_led_toggle(void);

/**
 * @brief Turn on green LED
 */
void green_led_on(void);

/**
 * @brief Turn off green LED
 */
void green_led_off(void);

/**
 * @brief Toggle Green LED
 */
void green_led_toggle(void);

/**
 * @brief Toggle IR LED
 */
void IR_led_toggle(void);

/**
 * @brief Turn on the IR receiver
 */
void IR_receiver_power_on(void);

/**
 * @brief Turn off IR receiver
 */
void IR_receiver_power_off(void);

/**
 * @brief Delay function
 */
void delay_ticks(uint32_t ticks);

#endif /* BSP_H */

