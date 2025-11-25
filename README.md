
# Cashcard Application [![Java CI with Gradle](https://github.com/manonworldrepository/spring-cloud-stream-cashcard-application/actions/workflows/gradle.yml/badge.svg)](https://github.com/manonworldrepository/spring-cloud-stream-cashcard-application/actions/workflows/gradle.yml)

This project demonstrates a simple, event-driven system using Spring Cloud Stream, Kafka, and Spring Boot with GraalVM native images. All services are designed to run within a Docker environment.

### Prerequisites

*   Java 21+
*   Docker and Docker Compose

### Building and Running the System

The entire system, including the applications and the end-to-end tests, is managed through Docker Compose.

**1. Build the Application Docker Images**

First, build the native Docker images for the `source`, `enricher`, `sink`, and `e2e-tester` services using the Spring Boot Gradle plugin.

```
$ ./gradlew clean bootBuildImage
```

### To run everything in docker containers:

```
$ docker compose up -d --build
```

The container image E2E will perform the tests upon completion of the creation of the docker container

### Application's Endpoint URL: 

``` http://localhost:8080/pub ```

HTTP Method: ``` POST ```

Example Payload:

```
{
    "id": 1234,
    "cashCard": {
        "id": 12345,
        "owner": "testOwner",
        "amountRequestedForAuth": "3.14"
    }
}
```

### Architecture Overview

![Architecture Overview](./system-with-sink.svg "Architecture Overview")


TODO: Assign a port number for each application and use its actuator endpoint
