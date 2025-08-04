# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy entire project structure
COPY . .

# Build common-library first
WORKDIR /app/common-library
RUN mvn clean install -DskipTests

# Build product-service
WORKDIR /app/product-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/product-service/target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "product-service.jar"]
