FROM openjdk:17-jdk-slim

WORKDIR /app

COPY libs libs/

COPY resources resources/

COPY classes classes/

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Xmx2048m", "-cp", "/app/resources:/app/classes:/app/libs/*", "com.example.app.AppApplication"]

EXPOSE 8080