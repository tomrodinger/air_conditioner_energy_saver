# Requirements Specification

1. [ Requirement 1.1: The Device should be able to learn the IR control signals](#req1.1)
2. [ Requirement 1.2: The Device should be able to emit IR control signals](#req1.2)
3. [ Requirement 1.3: The App will allow time based scheduling of IR code triggering](#req1.3)
4. [ Requirement 1.4: The App will allow the motion sensor sensitivity to be set](#req1.4)
5. [ Requirement 1.5: The App will allow a timeout to be set](#req1.5)
6. [ Requirement 1.6: The App will allow manual control of the air conditioner](#req1.6)
7. [ Requirement 1.7: The App will show a battery state of charge indicator](#req1.7)
8. [ Requirement 1.8: The App will display a notification when the battery is getting low](#req1.8)
9. [ Requirement 1.9: The App will allow turning on the air conditioner when motion is sensed](#req1.9)
10. [ Requirement 1.10: The App will support QR code reading to connect to the Device](#req1.10)
11. [ Requirement 1.11: The App will synchronize time with the Device](#req1.11)
12. [ Requirement 1.12: The App will display Device information](#req1.12)
13. [ Requirement 1.13: The App will allow time windowed scheduling of IR code triggering](#req1.13)

## Requirement 1.1 <a name="req1.1"></a>
**The Device should be able to learn the IR control signals (learning mode)**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | IR Codes learn
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | uint32

### Specification
Ir code: 6 bits (64 possible IR codes)

### Comments
- The App tells the Device what IR code is to be learned.

### Questions

## Requirement 1.2 <a name="req1.2"></a>
**The Device should be able to emit IR control signals**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | IR Codes emit
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | uint32

### Specification
Ir code: 6 bits (64 possible IR codes)

### Comments
- The App tells the Device what IR control signal to send.

### Questions

## Requirement 1.3 <a name="req1.3"></a>
**The App will allow time based scheduling of IR code triggering**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | Set schedule
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Specification
"command:[schedule|delete];ircode:[numeric_code];days:[uint32(7 bits)];window1:[hhmm];window2:[hhmm|-1]"

Example 1: Trigger IR code 1, every Monday and Friday, at 18:00

The app would send the string:
"command:schedule;ircode:1;days:68;window1:1800;window2:-1"

Example 2: Trigger IR code 4, everyday, at 23:15

The app would send the string:
"command:schedule;ircode:4;days:127;window1:2315;window2:-1"

Example 3: Trigger IR code 16, weekdays, within 09:00 to 17:00

The app would send the string:
"command:schedule;ircode:16;days:124;window1:0900;window2:1700"

Example 4: Delete IR code 4, everyday, at 23:15

The app would send the string:
"command:delete;ircode:4;days:127;window1:2315;window2:-1"

### Comments

- Supports time and days of week.
- Example: Turn on air conditioner every weekday on 7:00 am.
- We need a command to disable/erase a schedule.

The App tells the Device:
- What IR code to trigger
- When to trigger it

### Questions

## Requirement 1.4 <a name="req1.4"></a>
**The App will allow the motion sensor sensitivity to be set**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | Motion sensor sensitivity
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | uint32

### Comments

### Questions
What are the possible values for sensor sensitivity?

## Requirement 1.5 <a name="req1.5"></a>
**The App will allow a timeout to be set (to turn off the air conditioner if no motion is sensed for a while)**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | Motion sensor timeout
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | uint32

### Specification
- Timeout duration in seconds (max value: 24 hours = 86400 seconds): 17 bits 
- Ir code: 6 bits (64 possible IR codes)
- Days: 7 bits
- Total: 30 bits

Example: Emit IR code X every day if no motion is sensed for 2 minutes
- IR code X: 000001
- Every day: 1111111
- 2 minutes(120 seconds): 1111000
- => The app would send: 991169

### Comments
- We need a command to disable/erase a schedule.
- The App tells the Device the timeout value.
- The App tells the Device what IR code to emit when timeout value is reached.
- The App tells at what days the timeout is valid

### Questions

## Requirement 1.6 <a name="req1.6"></a>
**The App will allow the air conditioner to be controlled manually using buttons in the App (for testing purposes mainly)**

### BLE Protocol
Same as 1.2

### Comments
- Completely handled by the App. Given that IR control signals have been learned.

### Questions

## Requirement 1.7 <a name="req1.7"></a>
**The App will show a battery state of charge indicator**

### BLE Protocol

Battery service | UUID: 0x180F
--- | ---
Battery level characteristic | -
Name | Battery level
UUID | 0x2A19
Properties | NOTIFY, READ
Type | uint8

Custom characteristic | -
--- | ---
Name | Battery voltage
UUID | TBD
Properties | NOTIFY, READ
Type | uint16

### Comments

### Questions
- Why is a custom characteristic needed? Isn’t it enough with the Battery service?

## Requirement 1.8 <a name="req1.8"></a>
**The App will display a notification when the battery is getting low**

### BLE Protocol
N/A

### Comments
- Completely handled by the App

### Questions

## Requirement 1.9 <a name="req1.9"></a>
**The App will allow turning on the air conditioner when motion is sensed**

### BLE Protocol
See 1.3

### Comments

### Questions

## Requirement 1.10 <a name="req1.10"></a>
**The App will support QR code reading to connect to the Device**

### BLE Protocol

### Comments

### Questions
- More information needed about the information that will be provided by the QR code.

## Requirement 1.11 <a name="req1.11"></a>
**The App will synchronize time with the Device**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | RTC Set
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | uint32

### Comments
- RTC: Real Time Clock. The App will send epoch time in order to syncronize the time on the Device.

### Questions

## Requirement 1.12 <a name="req1.12"></a>
**The App will display Device informatione**

### BLE Protocol

Device information service. | UUID: 0x180F
--- | ---
Hardware Revision characteristic | -
Name | Hardware Revision string
UUID | 0x2A27
Properties | READ
Type | UTF-8 String Ex:("1.0.0")
---- | ----  
Firmware Revision characteristic. | -
Name | Firmware Revision string
UUID | 0x2A26
Properties | READ
Type | UTF-8 String Ex:("1.0.0")

### Comments

### Questions

## Requirement 1.13 <a name="req1.13"></a>
**The App will allow time windowed scheduling of IR code triggering**

### BLE Protocol
See 1.3

### Comments
The logic here is that if motion is detected and it is sufficiently strong to pass the threshold of the sensitivity setting and the time at which the motion occurs is within the time window (between the begin time and end time) then the IR code will be triggered. For example, I want to set up this device to turn on my air conditioner in the office during the weekdays Monday to Friday during office hours 9am to 5pm (the time window) and only if a person walks directly onto the room and not a person walking outside the office (hence sensitivity adjustmemt).

### Questions
