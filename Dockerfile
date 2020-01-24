FROM openjdk:8-alpine

WORKDIR /app

COPY ./build/libs/ktor-sample-app-1.0.0-all.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "ktor-sample-app-1.0.0-all.jar"]
