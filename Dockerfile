# ✅ Use GraalVM Native Image base image with native-image preinstalled
FROM ghcr.io/graalvm/graalvm-community:21 as builder

WORKDIR /app

# Copy only the necessary Gradle files and source code for the build
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY cashcard-transaction-domain ./cashcard-transaction-domain
COPY cashcard-transaction-e2e ./cashcard-transaction-e2e

# Compile native image using Gradle
RUN ./gradlew :cashcard-transaction-e2e:nativeCompile --no-daemon

# ✅ Use minimal runtime image for final stage
FROM paketobuildpacks/run-jammy-tiny

WORKDIR /workspace

# Copy compiled native binary
COPY --from=builder /app/cashcard-transaction-e2e/build/native/nativeCompile/cashcard-transaction-e2e .

# ✅ Copy the feature files from the builder stage into the final image
COPY --from=builder /app/cashcard-transaction-e2e/src/main/resources/features ./features

# Set entrypoint to the native binary
ENTRYPOINT ["./cashcard-transaction-e2e"]
