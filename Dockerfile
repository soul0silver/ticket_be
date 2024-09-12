
FROM openjdk:17-alpine

# Thiết lập thư mục làm việc
WORKDIR /edumall-app

# Sao chép tệp JAR đã được xây dựng từ bước trước vào thư mục /quizz-app trong container
COPY build/libs/*.jar app.jar

# Mở cổng 9192
EXPOSE 8000

# Lệnh chạy ứng dụng Spring Boot
CMD ["java", "-jar", "app.jar"]
