# KangooZE_BatteryOptimization

**Author:** Jaime Sanchez Cotta  
**Thesis Completion Date:** July 7, 2023  
*Erasmus period at Frankfurt University Of Applied Sciences*

## Abstract
This README provides detailed information about the bachelor thesis project on Development of a Battery optimization for the Renault Kangoo ZE.

## Prerequisites
To contribute to this project, you should have knowledge in the following:

- Java programming language
- Git version control system
- MQTT (Message Queuing Telemetry Transport) basics
- JSON (JavaScript Object Notation) basics
- Android Studio programming basics
- Familiarity with Raspberry Pi environment and interfacing

## Development Elements Required
The following elements are required during the development process of this thesis:

### JSON Tests
For JSON tests, the following zip files are available:

1. `getDataFromJSON_1stAttempt.zip`: This file requires Android Studio and performs a JSON call to gather forecast weather data required by the application.
2. `getDataFromJSON_2ndAttempt.zip`: Similar to the previous project, but with an additional button to trigger the weather forecast call.
3. `getDataFromJSON_3rdAttempt.zip`: The final program that gathers energy forecast values on a second activity of the Android Studio project.

### MQTT Tests
For MQTT tests, the following zip files are available:

1. `mqttHiveMQ.zip`: Enables MQTT connection from the HiveMQ broker to the Android Studio app.
2. `mqttPylontech.zip`: Enables connection between the data published by the Pylontech Battery and the Android Studio app.

### Additional Files
In addition to the above, the following files are needed:

- `pylog130.zip`: An executable program that allows connection to the Pylontech battery and data transmission via the RS485 wire.
- `ChargingAlgorithm-1.0-SNAPSHOT-jar-with-dependencies.jar` and `ChargingAlgorithm` directory: These elements are necessary for executing the solution to the problem. Knowing when it should be charged the Kangoo Battery and when the Pylontech.
- `KangooZE_App.zip`: An Android Studio project created for estimating the Kangoo ZE, as described in the thesis.

## Execution Instructions
To execute the programs, follow these instructions:

- Charging Algorithm: Run the following command in the terminal or command prompt:  
  `java -jar ChargingAlgorithm-1.0-SNAPSHOT-jar-with-dependencies.jar 50 40`  
  (This example command sets the desired battery charge at 50% and the actual Kangoo State of Charge (SoC) at 40%)
- KangooZE_App: First, execute the PyLog130 on the Raspberry Pi, which should be connected to the Pylontech battery beforehand. Then, run the app by clicking the run button in the Android Studio environment. Note: Ensure the phone emulator is properly configured.

Feel free to explore and utilize the provided resources to understand and optimize the battery charging process for the Renault Kangoo ZE.
