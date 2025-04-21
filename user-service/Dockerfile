# Sử dụng image Java 17 (hoặc phù hợp với phiên bản bạn đang sử dụng)
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép file .jar vào container
COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

# Mở cổng ứng dụng Spring Boot (mặc định là 8080)
EXPOSE 8080

# Chạy ứng dụng Spring Boot khi container bắt đầu
ENTRYPOINT ["java", "-jar", "user-service.jar"]
