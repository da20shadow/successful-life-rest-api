# Use a base image
#FROM openjdk:17-jre-slim
FROM amazoncorretto:17.0.6

# Set the working directory
WORKDIR /app

# Copy the application JAR file and its dependencies
COPY build/libs/successful-life-rest-api.jar /app

# Expose the application's port
EXPOSE 8090

# Start the application
CMD ["java", "-jar", "successful-life-rest-api.jar"]