# Requirements Specification

1. [ Requirement 1.1: The Device should be able to learn the IR control signals](#req1.1)
2. [ Requirement 1.2: The Device should be able to emit IR control signals](#req1.2)
3. [ Requirement 1.3: The App will allow the user to set schedules to turn on and off an air conditioner](#req1.3)
4. [ Requirement 1.4: The App will allow the motion sensor sensitivity to be set](#req1.4)
5. [ Requirement 1.5: The App will allow a timeout to be set](#req1.5)
6. [ Requirement 1.6: The App will allow manual control of the air conditioner](#req1.6)
7. [ Requirement 1.7: The App will show a battery state of charge indicator](#req1.7)
8. [ Requirement 1.8: The App will display a notification when the battery is getting low](#req1.8)
9. [ Requirement 1.9: The App will allow turning on the air conditioner when motion is sensed](#req1.9)
10. [ Requirement 1.10: The App will support QR code reading to connect to the Device](#req1.10)

## Requirement 1.1 <a name="req1.1"></a>
The Device should be able to learn the IR control signals (learning mode)

### BLE Protocol

Custom characteristic | -
--- | ---
Name | IR Codes learn
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | ???

### Comments
- The App tells the Device to enter learning mode.
- The App tells the Device what IR control signal is to be learned.
- The App needs to map each IR control signal to a unique signal code.
- The app needs to store the signal code for later usage.

### Questions
- Will the App get confirmation from the Device that the code has been successfully learned?
- User needs to input to the App what signal is being learned (e.g. turn on air conditioner). No UI design for that today. Will there be design?

## Requirement 1.2 <a name="req1.2"></a>
**The Device should be able to emit IR control signals**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | IR Codes emit
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | ???

### Comments
- The App tells the Device what IR control signal to send.

### Questions
- Will the App get confirmation from the Device that the code has been successfully emitted?

## Requirement 1.3 <a name="req1.3"></a>
**The App will allow the user to set schedules to turn on and off an air conditioner**

### BLE Protocol

Custom characteristic | -
--- | ---
Name | Set schedule
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | ???

### Comments

- Not clear yet if days of week should be included
- We need a command to disable/erase a schedule.

The App tells the Device:
- On or Off
- When to trigger

Proposed type: UTF-8 String

Where the first char is “0”(On) or “1”(Off). Remaining chars are the scheduling data.

### Questions
- Include days of week?
- UTC time?

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

### Comments
- We need a command to disable/erase a schedule.
- The App tells the Device the timeout value.

Proposed timeout value: Timeout in seconds.

Example: Turn off air conditioner if no motion is sensed for 10 minutes: App sends: 600

### Questions
- Include days of week?
- UTC time?

## Requirement 1.6 <a name="req1.6"></a>
**The App will allow the air conditioner to be controlled manually using buttons in the App (for testing purposes mainly)**

### BLE Protocol
N/A

### Comments
- Completely handled by the App. Given that IR control signals have been learned.
- Double check if UI design is present for this requirement.

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
TODO

### Questions
- Why is a custom characteristic needed? Isn’t it enough with the Battery service?

## Requirement 1.8 <a name="req1.8"></a>
**The App will display a notification when the battery is getting low**

### BLE Protocol
N/A

### Comments
- Completely handled by the App

### Questions
TODO

## Requirement 1.9 <a name="req1.9"></a>
**The App will allow turning on the air conditioner when motion is sensed**

### BLE Protocol
TDB

### Comments
- The BLE protocol for this is missing

### Questions
TODO

## Requirement 1.10 <a name="req1.10"></a>
**The App will support QR code reading to connect to the Device**

### BLE Protocol

### Comments
TODO

### Questions
- More information needed about the information that will be handled by the QR code.
- 
