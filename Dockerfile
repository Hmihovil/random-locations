FROM openjdk:8-jdk-alpine
COPY build/libs/*.jar app.jar

USER root

EXPOSE 8080
EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.jar"]
