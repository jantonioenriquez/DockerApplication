# Use a lightweight OpenJDK 21 runtime based on Alpine Linux
# Provides a small image size while supporting Java 21 applications
FROM eclipse-temurin:21-alpine

# Define a build-time argument to specify the path to the JAR file
# Defaults to any JAR inside the target/ directory (typical Maven output)
ARG JAR_FILE=target/*.jar

# Copy the built JAR file from the host into the container
# The file is renamed to 'app.jar' inside the container for consistency
COPY ${JAR_FILE} app.jar

# Define the container entry point
# Runs the Java application when the container starts
# Uses the -jar option to execute the Spring Boot fat JAR
ENTRYPOINT ["java", "-jar", "app.jar"]