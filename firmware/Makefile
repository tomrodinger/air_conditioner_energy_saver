PROJECT_NAME     := executable
TARGETS          := nrf52810_xxaa
OUTPUT_DIRECTORY := build

PROJ_DIR := .

HEX_APP := $(OUTPUT_DIRECTORY)/$(TARGETS).hex
HEX_SD  := $(PROJ_DIR)/Source/nRF_SoftDevice/hex/s112_nrf52_7.2.0_softdevice.hex


$(OUTPUT_DIRECTORY)/$(TARGETS).out: \
  LINKER_SCRIPT  := linker.ld

# Source files common to all targets
SRC_FILES += \
  $(PROJ_DIR)/Source/Application/main.c \
  $(PROJ_DIR)/Source/Application/bsp.c \
  $(PROJ_DIR)/Source/Application/hw_tests.c \
  $(PROJ_DIR)/Source/Application/lib_ble.c \
  $(PROJ_DIR)/Source/nRF_BLE/ble_advdata.c \
  $(PROJ_DIR)/Source/nRF_BLE/ble_conn_params.c \
  $(PROJ_DIR)/Source/nRF_BLE/ble_conn_state.c \
  $(PROJ_DIR)/Source/nRF_BLE/ble_srv_common.c \
  $(PROJ_DIR)/Source/nRF_BLE/nrf_ble_gatt.c \
  $(PROJ_DIR)/Source/nRF_BLE/nrf_ble_qwr.c \
  $(PROJ_DIR)/Source/nRF_BLE_Services/ble_lbs.c \
  $(PROJ_DIR)/Source/nRF_Libraries/app_error.c \
  $(PROJ_DIR)/Source/nRF_Libraries/app_error_handler_gcc.c \
  $(PROJ_DIR)/Source/nRF_Libraries/app_error_weak.c \
  $(PROJ_DIR)/Source/nRF_Libraries/app_timer2.c \
  $(PROJ_DIR)/Source/nRF_Libraries/app_util_platform.c \
  $(PROJ_DIR)/Source/nRF_Libraries/drv_rtc.c \
  $(PROJ_DIR)/Source/nRF_Libraries/hardfault_implementation.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_atfifo.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_atflags.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_atomic.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_balloc.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_memobj.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_pwr_mgmt.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_ringbuf.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_section_iter.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_sortlist.c \
  $(PROJ_DIR)/Source/nRF_Libraries/nrf_strerror.c \
  $(PROJ_DIR)/Source/nRF_Log/nrf_log_default_backends.c \
  $(PROJ_DIR)/Source/nRF_Log/nrf_log_frontend.c \
  $(PROJ_DIR)/Source/nRF_SoftDevice/nrf_sdh.c \
  $(PROJ_DIR)/Source/nRF_SoftDevice/nrf_sdh_ble.c \
  $(PROJ_DIR)/Source/nRF_SoftDevice/nrf_sdh_soc.c \
  $(PROJ_DIR)/Source/system/system_nrf52810.c \
  $(PROJ_DIR)/Source/startup/gcc_startup_nrf52810.S \

# Include folders common to all targets
INC_FOLDERS += \
  $(PROJ_DIR)/Source/Application \
  $(PROJ_DIR)/Source/config \
  $(PROJ_DIR)/Source/nRF_BLE \
  $(PROJ_DIR)/Source/nRF_BLE_Services \
  $(PROJ_DIR)/Source/nRF_Drivers \
  $(PROJ_DIR)/Source/nRF_Drivers/hal \
  $(PROJ_DIR)/Source/nRF_Libraries \
  $(PROJ_DIR)/Source/nRF_Log \
  $(PROJ_DIR)/Source/nRF_SoftDevice \
  $(PROJ_DIR)/Source/nRF_SoftDevice/headers \
  $(PROJ_DIR)/Source/system \
  $(PROJ_DIR)/Source/startup \
  $(PROJ_DIR)/toolchain_essentials/toolchain_and_libraries/SES_include \

# Libraries common to all targets
LIB_FILES += \

# Optimization flags
OPT = -Os -g3
# Uncomment the line below to enable link time optimization
#OPT += -flto

# C flags common to all targets
CFLAGS += $(OPT)
CFLAGS += -DAPP_TIMER_V2
CFLAGS += -DAPP_TIMER_V2_RTC1_ENABLED
#CFLAGS += -DBOARD_PCA10040
CFLAGS += -DCONFIG_GPIO_AS_PINRESET
CFLAGS += -DDEVELOP_IN_NRF52832
CFLAGS += -DFLOAT_ABI_SOFT
CFLAGS += -DNRF52810_XXAA
CFLAGS += -DNRF52_PAN_74
CFLAGS += -DNRFX_COREDEP_DELAY_US_LOOP_CYCLES=3
CFLAGS += -DNRF_SD_BLE_API_VERSION=7
CFLAGS += -DS112
CFLAGS += -DSOFTDEVICE_PRESENT
CFLAGS += -mcpu=cortex-m4
CFLAGS += -mthumb -mabi=aapcs
CFLAGS += -Wall
CFLAGS += -w
CFLAGS += -mfloat-abi=soft
# keep every function in a separate section, this allows linker to discard unused ones
CFLAGS += -ffunction-sections -fdata-sections -fno-strict-aliasing
CFLAGS += -fno-builtin -fshort-enums

# C++ flags common to all targets
CXXFLAGS += $(OPT)
# Assembler flags common to all targets
ASMFLAGS += -g3
ASMFLAGS += -mcpu=cortex-m4
ASMFLAGS += -mthumb -mabi=aapcs
ASMFLAGS += -mfloat-abi=soft
ASMFLAGS += -DAPP_TIMER_V2
ASMFLAGS += -DAPP_TIMER_V2_RTC1_ENABLED
#ASMFLAGS += -DBOARD_PCA10040
ASMFLAGS += -DCONFIG_GPIO_AS_PINRESET
ASMFLAGS += -DDEVELOP_IN_NRF52832
ASMFLAGS += -DFLOAT_ABI_SOFT
ASMFLAGS += -DNRF52810_XXAA
ASMFLAGS += -DNRF52_PAN_74
ASMFLAGS += -DNRFX_COREDEP_DELAY_US_LOOP_CYCLES=3
ASMFLAGS += -DNRF_SD_BLE_API_VERSION=7
ASMFLAGS += -DS112
ASMFLAGS += -DSOFTDEVICE_PRESENT

# Linker flags
LDFLAGS += $(OPT)
LDFLAGS += -mthumb -mabi=aapcs -L$(PROJ_DIR)/toolchain_essentials/toolchain_and_libraries/makefile -T$(LINKER_SCRIPT)
LDFLAGS += -mcpu=cortex-m4
# let linker dump unused sections
LDFLAGS += -Wl,--gc-sections
# use newlib in nano version
LDFLAGS += --specs=nano.specs

$(TARGETS): CFLAGS += -D__HEAP_SIZE=2048
$(TARGETS): CFLAGS += -D__STACK_SIZE=2048
$(TARGETS): ASMFLAGS += -D__HEAP_SIZE=2048
$(TARGETS): ASMFLAGS += -D__STACK_SIZE=2048

# Add standard libraries at the very end of the linker input, after all objects
# that may need symbols provided by these libraries.
LIB_FILES += -lc -lnosys -lm


.PHONY: default help

# Default target - first one defined
default: $(TARGETS)

# Print all targets that can be built
help:
	@echo following targets are available:
	@echo		nrf52810_xxaa
	@echo		flash_softdevice
	@echo		flash  - flashing the app binary

TEMPLATE_PATH := $(PROJ_DIR)/toolchain_essentials/toolchain_and_libraries/makefile
include $(TEMPLATE_PATH)/Makefile.common
$(foreach target, $(TARGETS), $(call define_target, $(target)))

.PHONY: flash flash_softdevice erase

# Flash the program
flash: default
	@echo Flashing: $(HEX_APP)
	nrfjprog -f nrf52 --program $(HEX_APP) --sectorerase
	nrfjprog -f nrf52 --reset
#	openocd -c 'adapter driver jlink; transport select swd; source [find target/nrf52.cfg]; program $(HEX_APP) verify reset exit'
#	openocd -f interface/cmsis-dap.cfg -f target/nrf52.cfg -c "reset_config none; program $(HEX_APP) verify reset exit"
#	openocd -f interface/stlink.cfg -f target/nrf52.cfg -c "reset_config none; program $(HEX_APP) verify reset exit"

# Flash softdevice
flash_softdevice:
	@echo Flashing: $(HEX_SD)
	nrfjprog -f nrf52 --program $(HEX_SD) --sectorerase
	nrfjprog -f nrf52 --reset
#	openocd -c 'adapter driver jlink; transport select swd; source [find target/nrf52.cfg]; program $(HEX_SD) verify reset exit'
#	openocd -f interface/cmsis-dap.cfg -f target/nrf52.cfg -c "reset_config none; program $(HEX_SD) verify reset exit"
#	openocd -f interface/stlink.cfg -f target/nrf52.cfg -c "reset_config none; program $(HEX_SD) verify reset exit"

erase:
	nrfjprog -f nrf52 --eraseall
