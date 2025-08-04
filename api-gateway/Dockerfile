# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy entire project structure
COPY . .

# Build common-library first
WORKDIR /app/common-library
RUN mvn clean install -DskipTests

# Build api-gateway
WORKDIR /app/api-gateway
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar

EXPOSE 8080

CMD ["java", "-jar", "api-gateway.jar"]