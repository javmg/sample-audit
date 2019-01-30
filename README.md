# sample-audit

This is a simple PoC for auditing

## Requirements to run the application

* Java 8
* Maven

## Building the application

We just run:

> mvn clean install

## Running the application

First we need to configure the properties of the environment to tun.

If we want to run in development mode we'd configure application-dev.properties.

Then we can run the application in development mode with:

> mvn spring-boot:run -DSPRING_PROFILES_ACTIVE=dev

The application (swagger) should be up and kicking under 8080 port:

> http://localhost:8080

The h2-console - when enabled - should be under:

> http://localhost:8080/h2-console

## Running the tests

We only have Integration Tests so we can run:

> mvn clean verify
 