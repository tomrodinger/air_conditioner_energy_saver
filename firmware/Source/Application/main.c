#include <stdint.h>
#include <string.h>
#include "nordic_common.h"
#include "nrf.h"
#include "app_error.h"
#include "lib_ble.h"
#include "bsp.h"
#include "hw_tests.h"

#include "app_timer.h"
#include "nrf_pwr_mgmt.h"

#include "nrf_log.h"
#include "nrf_log_ctrl.h"
#include "nrf_log_default_backends.h"

#include "nrf_delay.h"
#include "nrf_gpio.h"

int main(void) {

  /* Initialize board peripherals */
  bsp_init();

#ifdef HW_TESTS
  /* Launch specific hardware test */
  hw_tests();
#endif

  /* Configure low power mode */
  ret_code_t err_code = nrf_pwr_mgmt_init();
  APP_ERROR_CHECK(err_code);
  red_led_on();
  
  /* Launch specific configuration services */
  if(0) {
    /* BLE configuration service*/
    lib_ble_init();
    lib_ble_advertising_start();
  }

  while(1)
  {
    if (NRF_LOG_PROCESS() == false)
    {
        nrf_pwr_mgmt_run();
    }  
  }
}
