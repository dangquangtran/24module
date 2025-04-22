FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar

EXPOSE 8761

CMD ["java", "-jar", "eureka-server.jar"]
