# Use a Java base image
FROM openjdk:21-slim

# Set a build argument for the jar file
ARG JAR_FILE=customer-service-0.0.1-SNAPSHOT.jar

# Working directory
WORKDIR /app

# Copy the packaged application jar file
COPY target/${JAR_FILE} /app/customer-service.jar

# Copy the bash script into the container
COPY start.sh /app/start.sh

# Execute permissions to the bash script
RUN chmod +x /app/start.sh

# Expose standard 8080 and 8443 port
EXPOSE 8080 8443

# Entry point
CMD ["/app/start.sh"]
