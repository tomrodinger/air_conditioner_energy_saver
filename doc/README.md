# Formats

* An IR-code is represented by a hexadecimal string with max value of FF I.e. 255.
* A trigger id is represented by a hexadecimal string with max value of FF I.e. 255.
* Time duration is represented by a hexadecimal string with max value of 15180 I.e. 86400. That is 24 hours. Duration unit is seconds.
  Examples:
  - Duration 1 hour is **E10** (3600 seconds)
  - Duration 3 hours is **2a30** (10800 seconds)
 * Time is represented by UTF8 string and has the format: hhmm.
   Examples:
   - Time: 09:30 is **0930**
   - Time: 17:45 is **1745**
 * Days of week are represented by a hexadecimal string with max value of FF I.e. 255. The hex needs to be converted to binary representation to get what days are represented. In binary most significant bit is Monday and least significant bit is Sunday.
   Examples:
   - All days of the week: Hex: **7F** = Binary: 1111111
   - Sunday and Monday: Hex: **41** = Binary: 1000001
   - Weekdays: Hex: **7C** = Binary: 1111100
   - Weekends: Hex: **3** = Binary: 0000011

  ## Trigger command
  General format: **[trigger-mode]-[trigger-id]**
  * A trigger command is a UTF8 String with maximum length of 20 characters (20 bytes).
  * A trigger command can have one of the 2 modes: _create_ or _delete_. Create is represented by the character **c**. Delete is represented by the character **d**.
  * A trigger command starts with trigger mode followed by trigger id and separated by the character **-**.
  * Examples:
    - Create a trigger with id 1: **c-1**
    - Create a trigger with id 123: **c-7b**
    - Delete a trigger with id 15: **d-f**

  ## Triggers from the app
  This section is a quick reference with examples. For more detailed specifications please read the next chapter: Requirements Specification.

  * Trigger command: Create/Delete trigger that emits an ir-code when motion is detected. (Motion sensitivity is set by the app using another Characteristic).
    - Characteristic: 09391527-6941-4a91-9d3a-a483fd2a1dd6
    - Format: [trigger-mode]-[trigger-id]-[ir-code]
    - Examples:
      - Create Create a trigger with id 122 that emits ir-code 15: **c-7a-f**
      - Detele trigger with id 122: **d-7a**

  * Trigger command: (Req.1.3) Create/Delete trigger that emits an ir-code based on a specific time and date.
    - Characteristic: 09391527-6941-4a91-9d3a-a483fd2a1dd6
    - Format: [trigger-mode]-[trigger-id]-[ir-code]-[days]-[time1]
    - Example:
      - Create a trigger with id 122 that emits ir-code 15 every day at 23:15: **c-7a-f-7f-2315**
        - Explanation: c: create. id 122: 7a in hex. id 15: f in hex. Every day binary 111111: 7f in hex. Time 23:15 is 2315.
  
  * Trigger command: (Req.1.13) Create/Delete trigger that emits an ir-code based on a time window and date.
    - Characteristic: 09391527-6941-4a91-9d3a-a483fd2a1dd6
    - Format: [trigger-mode]-[trigger-id]-[ir-code]-[days]-[time1]-[time2]
    - - Example:
      - Create a trigger with id 122 that emits ir-code 15 every day between 08:00 and 23:15: **c-7a-f-7f-0800-2315**
        - Explanation: c: create. id 122: 7a in hex. id 15: f in hex. Every day binary 111111: 7f in hex. Time 08:00 is 0800. Time 23:15 is 2315.

  * Trigger command: (Req.1.5) Create/Delete trigger that emits an ir-code when motion stops for specific duration during given days.
    - Characteristic: 09391526-6941-4a91-9d3a-a483fd2a1dd6
    - Format: [trigger-mode]-[trigger-id]-[ir-code]-[days]-[duration]
    - Example:
      - Create a trigger with id 122 that emits ir-code 15 every day if no motion is sensed for 2 minutes: **c-7a-f-7f-78**
        - Explanation: c: create. id 122: 7a in hex. id 15: f in hex. Every day binary 111111: 7f in hex. Duraion 2 minutes = 120 seconds = 78 in hex.
  

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
14. [ Requirement 1.14: The App will allow to delete an IR code](#req1.14)

## Requirement 1.1 <a name="req1.1"></a>
**The Device should be able to learn the IR control signals (learning mode)**

### BLE Protocol

Custom characteristic | 09391532-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | IR Codes learn
UUID | 0x1532
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Specification
Ir code: hexadecimal string with max value of FF

### Comments
- The App tells the Device what IR code is to be learned.

### Questions

## Requirement 1.2 <a name="req1.2"></a>
**The Device should be able to emit IR control signals**

### BLE Protocol

Custom characteristic | 09391533-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | IR Codes emit
UUID | 0x1533
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Specification
Ir code: hexadecimal string with max value of FF

### Comments
- The App tells the Device what IR control signal to send.

### Questions

## Requirement 1.3 <a name="req1.3"></a>
**The App will allow time based scheduling of IR code triggering**

### BLE Protocol

Custom characteristic | 09391527-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | Set schedule
UUID | 0x1527
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Specification
"[trigger-mode]-[trigger-id]-[ir-code]-[days]-[time1]-[time2]"

Example 1: Trigger id 1 with IR code 1, every Monday and Friday, at 18:00

The app would send the string:
"c-1-1-44-1800"

Example 2: Trigger id 2 with IR code 4, everyday, at 23:15

The app would send the string:
"c-2-4-7f-2315"

Example 3: Trigger id 3 with IR code 16, weekdays, within 09:00 to 17:00

The app would send the string:
"c-3-10-7c-0900-1700"

Example 4: Delete Trigger id 4

The app would send the string:
"d-4"

### Comments

- Supports time and days of week.
- Example: Turn on air conditioner every weekday on 7:00 am.

The App tells the Device:
- What IR code to trigger
- When to trigger it

### Questions

## Requirement 1.4 <a name="req1.4"></a>
**The App will allow the motion sensor sensitivity to be set**

### BLE Protocol

Custom characteristic | 09391525-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | Motion sensor sensitivity
UUID | 0x1525
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Comments

### Questions

## Requirement 1.5 <a name="req1.5"></a>
**The App will allow a timeout to be set (to turn off the air conditioner if no motion is sensed for a while)**

### BLE Protocol

Custom characteristic | 09391526-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | Motion sensor timeout
UUID | 0x1526
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Specification
- See the "Formats" section for more details.
- Time duration is represented by a hexadecimal.
- Ir code: hexadecimal string with max value of FF.
- Days of week are represented by a hexadecimal string with max value of FF I.e. 255.

Example: Create a trigger with id 122 that emits ir-code 15 every day if no motion is sensed for 2 minutes:
- Create: c
- Trigger id: 7a
- IR code X: f
- Every day: 7f (binary: 1111111)
- 2 minutes(120 seconds): 78
- => The app would send: **c-7a-f-7f-78**

### Comments
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

Custom characteristic | 09391529-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | Battery voltage
UUID | 0x1529
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

Custom characteristic | 09391524-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | RTC Set
UUID | 0x1524
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Comments
- RTC: Real Time Clock. The App will send epoch time in order to syncronize the time on the Device. Epoch time in seconds.

### Questions

## Requirement 1.12 <a name="req1.12"></a>
**The App will display Device informatione**

### BLE Protocol

Device information service. | UUID: 0x180A
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

## Requirement 1.14 <a name="req1.14"></a>
**The App will allow to delete an IR code**

### BLE Protocol

Custom characteristic | 09391534-6941-4a91-9d3a-a483fd2a1dd6
--- | ---
Name | IR Codes emit
UUID | 0x1534
Properties | WRITE, WRITE NO RESPONSE
Type | UTF-8 String

### Specification
Ir code: hexadecimal string with max value of FF

### Comments
- The App tells the Device what IR control signal to delete.

### Questions
