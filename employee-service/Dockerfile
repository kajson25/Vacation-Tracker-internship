#FROM ubuntu:latest
#LABEL authors="kajson"
#
#ENTRYPOINT ["top", "-b"]

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build scripts
COPY gradlew .
COPY gradle/ gradle/

# Copy the entire project
COPY . .

# Install dependencies and build the application
RUN ./gradlew clean build -x test

# Copy the built JAR file into the container
COPY build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Expose the port your Spring Boot application runs on
EXPOSE 8081

# Environment variables for database configuration
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydatabase
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_MAIN_ALLOW_BEAN_DEFINITION_OVERRIDING=true
ENV SPRING_SQL_INIT_MODE=always
