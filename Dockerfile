FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./target/msvc-template.jar .

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "msvc-template.jar"]