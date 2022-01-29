# Automation tests for PlanIT website

 ## Overview
 Sample framework identifies the operating system and installed version of browser, based on the result it picks up the appropriate webdriver and version. For sample project Chrome version 96.0, 97.0 setup is done for Windows, Linux and Mac however this can easily extended to other browser and OS versions. This setup is done to eliminate different testers using different operating systems and browsers. As Webdriver is created as an interface we can have implementations for using Docker and third party tools as Browserstack.
  
 **PageObject Design Pattern** is used with added abstraction layer of PageActions to keep all behaviour seperate from element identifiers.
 
  For this sample test _emphasis is given on project structure and project setup than test coverage_ as once setup is done extending test coverage is easy. 

## Pre-requisites
These are the pre-requisites for the project setup. You will also need to have your workstation setup accordingly.

- **Java 8** -  _The project also works with Java 8 and above although Java 8 is the preferred setup_
- **Maven** -  _Maven is the build tool used to sample project_
- **Lombok Plugin** for your IDE (https://plugins.jetbrains.com/plugin/6317-lombok if using IntelliJ)

## Technology Stack
- Java 8
- Selenium Webdriver
- Spring framework
- Maven as build tool
- JUnit

# Setup
 - Clone the repository
 - Import the project into your IDE.
 - Run `mvn clean install` against the repository (with tests skipped) from within your IDE or via the console. This should download all the required artifacts.
 
 # Running Tests
Tests can be executed within your IDE using a JUnit runner or a Maven runner. You can also execute using Maven from the terminal.

Examples of how to do this are shown below:

## JUnit
Running the **JupiterTestRunner** will trigger all tests against the **dev** environment using chrome.

You can also run a test with a specific tag by passing this into the runner. The environment and browser can also be specified:

`-Dspring.profiles.active=dev -Dweb.driver.browser=chrome`
 

