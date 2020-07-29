FROM openjdk:8-alpine

WORKDIR /app

COPY ./build/libs/todo-list-bff-1.0.0-all.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "todo-list-bff-1.0.0-all.jar"]
