# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set work directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run with Java 21 JDK
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the packaged JAR from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (change as per your app config)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]