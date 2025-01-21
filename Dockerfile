FROM docker.io/openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs libs/

COPY build/resources resources/

COPY build/classes classes/

COPY build/libs/*.war app.war

RUN ls -la

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Xmx2048m", "-jar", "app.war"]

EXPOSE 8080