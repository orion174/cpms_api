FROM eclipse-temurin:17-jdk-alpine

WORKDIR /home/cpms_api

COPY cpms_api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
