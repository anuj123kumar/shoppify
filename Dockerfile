# === Stage 1: Build the application using Maven ===
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven config and project files
COPY pom.xml ./
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests=true

# === Stage 2: Use lightweight Java 21 JRE to run the app ===
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
