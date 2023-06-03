# Requirements Specification

1. [ Requirement 1.1: The Device should be able to learn the IR control signals (learning mode) ](#ref1)
2. [ Requirement 1.2: The Device should be able to emit IR control signals ](#ref2)

## Requirement 1.1 <a name="ref1"></a>
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

## Requirement 1.2 <a name="ref2"></a>
**The Device should be able to emit IR control signals**

## BLE Protocol

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

## Requirement 1.3
**The App will allow the user to set schedules to turn on and off an air conditioner**

## BLE Protocol

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











## Requirement 1.1
**The Device should be able to learn the IR control signals (learning mode)**

## BLE Protocol

Custom characteristic | -
--- | ---
Name | IR Codes learn
UUID | TBD
Properties | WRITE, WRITE NO RESPONSE
Type | ???

### Comments
TODO

### Questions
TODO

