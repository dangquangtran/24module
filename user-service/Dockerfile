# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy entire project structure
COPY . .

# Build common-library first
WORKDIR /app/common-library
RUN mvn clean install -DskipTests

# Build user-service
WORKDIR /app/user-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/user-service/target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user-service.jar"]