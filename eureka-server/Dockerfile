# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy entire project structure
COPY . .

# Build eureka-server
WORKDIR /app/eureka-server
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar

EXPOSE 8761

CMD ["java", "-jar", "eureka-server.jar"]
