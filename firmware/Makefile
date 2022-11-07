DEPENDENCY_PREFIX = toolchain_essentials/
#DEPENDENCY_PREFIX =
TOOLCHAIN = $(DEPENDENCY_PREFIX)toolchain_and_libraries/SES_ARM_toolchain_6.34a
SES_INCLUDE = $(DEPENDENCY_PREFIX)toolchain_and_libraries/SES_include
SES_LIB = $(DEPENDENCY_PREFIX)toolchain_and_libraries/SES_lib

all: build/executable.hex

build/main.o: Source/main.c
	"$(TOOLCHAIN)/bin/segger-cc" -cc1 -disable-free -disable-llvm-verifier -fgnuc-version=4.2.1 -mrelocation-model static -mconstructor-aliases -x c -std=gnu99 -triple thumbv7em-none-eabi -target-cpu cortex-m4 -target-feature -strict-align -target-feature +soft-float -target-feature +soft-float-abi -msoft-float -target-abi aapcs -mfloat-abi soft -target-feature -fp16 -target-feature -vfp2sp -target-feature -vfp3d16sp -target-feature -vfp4d16sp -fno-signed-char -fallow-half-arguments-and-returns -mllvm -arm-global-merge=false -nostdsysteminc -nobuiltininc "-isystem$(TOOLCHAIN)/include" -I. "-I$(SES_INCLUDE)" -D__SIZEOF_WCHAR_T=4 -D__ARM_ARCH_7EM__ -D__SES_ARM -D__HEAP_SIZE__=2048 -D__SES_VERSION=63401 -D__SEGGER_LINKER -D_LIBCPP_HAS_NO_THREADS -D_LIBCPP_HAS_NO_OFF_T_FUNCTIONS -D__NO_AEABI_ERR__ -DDEBUG -dependency-file "build/main.d" -sys-header-deps -MT build/main.o -dwarf-version=4 -debug-info-kind=standalone -debug-info-macro -debugger-tuning=gdb -mllvm -generate-arange-section -exception-model=dwarf -gpubnames -fno-dwarf-directory-asm -fmath-errno -ffunction-sections -fdata-sections -fshort-enums -fno-common "Source/main.c" -emit-obj -o build/main.o

build/Cortex_M_Startup.o: System/Cortex_M_Startup.s
	"$(TOOLCHAIN)/gcc/arm-none-eabi/bin/cc1" -fmessage-length=0 -fdiagnostics-color=never -fdiagnostics-show-caret -std=gnu99 -E -march=armv7e-m -mlittle-endian -mfloat-abi=soft -mthumb -nostdinc "-isystem$(TOOLCHAIN)/include" -I. "-I$(SES_INCLUDE)" -D__SIZEOF_WCHAR_T=4 -D__ARM_ARCH_7EM__ -D__SES_ARM -D__HEAP_SIZE__=2048 -D__SES_VERSION=63401 -D__SEGGER_LINKER -D_LIBCPP_HAS_NO_THREADS -D_LIBCPP_HAS_NO_OFF_T_FUNCTIONS -D__NO_AEABI_ERR__ -DDEBUG -MD "build/Cortex_M_Startup.d" -MQ build/Cortex_M_Startup.o -quiet -lang-asm "System/Cortex_M_Startup.s" -o "build/Cortex_M_Startup_PP.s"
	"$(TOOLCHAIN)/gcc/arm-none-eabi/bin/as" --traditional-format -I. -march=armv7e-m -mlittle-endian -mfloat-abi=soft -mthumb "build/Cortex_M_Startup_PP.s" -o build/Cortex_M_Startup.o -g -gdwarf-4
	"$(TOOLCHAIN)/bin/rm" -f "build/Cortex_M_Startup_PP.s"

build/SEGGER_THUMB_Startup.o: System/SEGGER_THUMB_Startup.s
	"$(TOOLCHAIN)/gcc/arm-none-eabi/bin/cc1" -fmessage-length=0 -fdiagnostics-color=never -fdiagnostics-show-caret -std=gnu99 -E -march=armv7e-m -mlittle-endian -mfloat-abi=soft -mthumb -nostdinc "-isystem$(TOOLCHAIN)/include" -I. "-I$(SES_INCLUDE)" -D__SIZEOF_WCHAR_T=4 -D__ARM_ARCH_7EM__ -D__SES_ARM -D__HEAP_SIZE__=2048 -D__SES_VERSION=63401 -D__SEGGER_LINKER -D_LIBCPP_HAS_NO_THREADS -D_LIBCPP_HAS_NO_OFF_T_FUNCTIONS -D__NO_AEABI_ERR__ -DDEBUG -MD "build/SEGGER_THUMB_Startup.d" -MQ build/SEGGER_THUMB_Startup.o -quiet -lang-asm "System/SEGGER_THUMB_Startup.s" -o "build/SEGGER_THUMB_Startup_PP.s"
	"$(TOOLCHAIN)/gcc/arm-none-eabi/bin/as" --traditional-format -I. -march=armv7e-m -mlittle-endian -mfloat-abi=soft -mthumb "build/SEGGER_THUMB_Startup_PP.s" -o build/SEGGER_THUMB_Startup.o -g -gdwarf-4
	"$(TOOLCHAIN)/bin/rm" -f "build/SEGGER_THUMB_Startup_PP.s"

build/executable.elf: build/main.o build/Cortex_M_Startup.o build/SEGGER_THUMB_Startup.o Setup/SEGGER_Flash.icf $(SES_LIB)/libcxx_noexcept_v7em_t_le_eabi_balanced.a
	"$(TOOLCHAIN)/bin/segger-ld" --add-region:FLASH1=0x00030000@0x00000000 --add-region:FLASH2=0x00001000@0x10001000 --add-region:RAM1=0x00006000@0x20000000 --defsym=__STACKSIZE__=2048 --defsym=__STACKSIZE_PROCESS__=0 --defsym=__HEAPSIZE__=2048 --full-section-headers --dedupe-code --merge-sections --merge-strings -eReset_Handler -EL -mcpu=7E-M --defsym=__SEGGER_RTL_vfprintf=__SEGGER_RTL_vfprintf_int_nwp --defsym=__SEGGER_RTL_vfscanf=__SEGGER_RTL_vfscanf_int --silent --list-all-undefineds --gc-sections -TSetup/SEGGER_Flash.icf --map-text --map-file=build/executable.map --map-standard -u_vectors -o "build/executable.elf" --emit-relocs "build/main.o" "build/Cortex_M_Startup.o" "build/SEGGER_THUMB_Startup.o" "$(SES_LIB)/libcxx_noexcept_v7em_t_le_eabi_balanced.a" "$(TOOLCHAIN)/lib/libc_v7em_t_le_eabi_balanced.a" "$(TOOLCHAIN)/lib/SEGGER_crtinit_v7em_little.o" "$(TOOLCHAIN)/lib/prinops_rtt_v7em_little.a" "$(TOOLCHAIN)/lib/heapops_basic_v7em_little.a" "$(TOOLCHAIN)/lib/heapops_disable_interrupts_locking_v7em_little.a"

build/executable.hex: build/executable.elf
	"$(TOOLCHAIN)/gcc/arm-none-eabi/bin/objcopy" build/executable.elf "build/executable.hex" -Oihex

program: build/executable.hex
	openocd -c 'adapter driver jlink; transport select swd; source [find target/nrf52.cfg]; program build/executable.hex verify reset exit'
	#openocd -f interface/cmsis-dap.cfg -f target/nrf52.cfg -c "reset_config none; program build/executable.hex verify reset exit"
	#openocd -f interface/stlink.cfg -f target/nrf52.cfg -c "reset_config none; program build/executable.hex verify reset exit"

clean:
	rm -f build/*
