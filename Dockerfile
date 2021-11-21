FROM openjdk:8-alpine

COPY target/JWT-Template.jar /app/application.jar

CMD ["java", "-jar", "/app/application.jar"]