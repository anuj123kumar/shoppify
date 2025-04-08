# Use an official OpenJDK runtime as a parent image
FROM openjdk:21
# Set the working directory in the container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/shoppify-0.0.1-SNAPSHOT.jar /app/shoppify-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "shoppify-0.0.1-SNAPSHOT.jar"]



