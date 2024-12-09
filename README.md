# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/gradle-plugin/packaging-oci-image.html)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/3.4.0/reference/packaging/native-image/introducing-graalvm-native-images.html)
* [Cloud Stream](https://docs.spring.io/spring-cloud-stream/reference/)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.4.0/reference/web/reactive.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.4.0/reference/messaging/kafka.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/3.4.0/how-to/aot.html)

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks
If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./gradlew bootBuildImage
```

Then, you can run the app like any other container:

```
$ docker run --rm demo:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools
Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./gradlew nativeCompile
```

Then, you can run the app as follows:
```
$ build/native/nativeCompile/demo
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./gradlew nativeTest
```

### Gradle Toolchain support

There are some limitations regarding Native Build Tools and Gradle toolchains.
Native Build Tools disable toolchain support by default.
Effectively, native image compilation is done with the JDK used to execute Gradle.
You can read more about [toolchain support in the Native Build Tools here](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html#configuration-toolchains).


### Access Broadcast Messages

1. Start Kafka Locally:

``` docker compose up -d --build ```

2. Start the source application: 

``` $ ./gradlew cashcard-transaction-source:bootRun ```

3. Start the enricher application:

``` $ ./gradlew cashcard-transaction-enricher:bootRun ```

4. Start the sink application:

``` $ ./gradlew cashcard-transaction-sink:bootRun ```

And finally, approved transactions should be logged in

``` cashcard-transaction-sink/build/tmp/transactions-output.csv ```

And if you want to run the application locally without the native image

``` ./gradlew bootRun  ```


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
