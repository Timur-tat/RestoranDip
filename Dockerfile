# Используем официальный образ OpenJDK 17
FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем JAR-файл приложения в контейнер
COPY build/libs/*.jar /app/app.jar

# Выставляем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]