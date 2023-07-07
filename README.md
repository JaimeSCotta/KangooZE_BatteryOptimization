# KangooZE_BatteryOptimization

The following README clarifies the details of the bachelor thesis made by Jaime Sanchez Cotta during an Erasmus period at Frankfurt University Of Applied Sciences, completed on July 7, 2023.

## Abstract
The objective of this thesis is to develop a battery optimization solution for charging the Renault Kangoo ZE in an optimal way.

## Prerequisites
To work on this project, you should have knowledge in the following:

- Java programming language
- Git version control system
- Basics of MQTT (Message Queuing Telemetry Transport)
- Basics of JSON (JavaScript Object Notation)
- Familiarity with interfacing with the Raspberry Pi environment

## Elements Required in the Thesis Development Process
During the development process of this thesis, the following elements were required:

### JSON Tests
To perform JSON tests, you will need the following zip files:

1. `getDataFromJSON_1stAttempt.zip`: This file requires the Android Studio software and performs a JSON call to gather forecast weather data that the application requires.
2. `getDataFromJSON_2ndAttempt.zip`: Similar to the previous project, but with the addition of a button that triggers the weather forecast call.
3. `getDataFromJSON_3rdAttempt.zip`: This is the final program which gathers energy forecast values on a second activity of the Android Studio project.

### MQTT Tests
For MQTT tests, the following zip files are required:

1. `mqttHiveMQ.zip`: This enables the connection via MQTT from the HiveMQ broker to the Android Studio app.
2. `mqttPylontech.zip`: This enables the connection between the data published by the Pylontech Battery and the Android Studio app.

### Additional Files
In addition to the above, the following files were needed:

- `pylog130.zip`: This executable program allows the connection to the Pylontech battery and possible transmission of data via the RS485 wire.
- `ChargingAlgorithm-1.0-SNAPSHOT-jar-with-dependencies.jar` and `Charging Algorithm`: These elements are required for the execution of the solution to the problem. Knowing when should be charged the Kangoo ZE and when the Pylontech battery.
- `KangooZE_App.zip`: This zip file contains the Android Studio project created for the estimation of the Kangoo ZE described in the thesis.

## Execution Instructions
To execute the programs, follow these instructions:

- Charging Algorithm: Run the following command in the terminal or command prompt: `java -jar ChargingAlgorithm.jar 50 40` (This is an example command, where 50 represents 50% of the desired battery charge and 40 represents 40% of the actual Kangoo State of Charge (SoC)).
- KangooZE_App: First, execute the PyLog130 on the Raspberry Pi, which should be previously connected to the Pylontech battery. After that, the app can be executed by pressing the run button in the Android Studio environment. Please note that the phone emulator should be properly configured.

Feel free to explore and utilize the provided resources to understand and optimize the battery charging process for the Renault Kangoo ZE.
