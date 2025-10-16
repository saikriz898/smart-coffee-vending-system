# Multi-stage build for Java Coffee Vending System
FROM maven:3.8.6-openjdk-11 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:11-jre-slim

# Install required packages
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxrandr2 \
    libasound2 \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/CoffeeVendingSystem-1.0-SNAPSHOT.jar app.jar

# Copy configuration
COPY src/main/resources/config.properties ./config.properties

# Expose port (if needed for web interface)
EXPOSE 8080

# Set display for GUI
ENV DISPLAY=:0

# Run the application
CMD ["java", "-jar", "app.jar"]