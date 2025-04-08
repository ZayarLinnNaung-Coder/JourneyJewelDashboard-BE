FROM openjdk:17-jdk-slim as build

# Metadata about the image
LABEL maintainer="ZAYARLINNNAUNG"

# Add the application's jar to the container
COPY target/dms-0.0.1-SNAPSHOT.jar dms.jar

# Expose application port
EXPOSE 8090

# Execute the application
ENTRYPOINT ["java", "-jar", "/dms.jar"]