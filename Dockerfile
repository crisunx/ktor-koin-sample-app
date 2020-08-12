FROM gradle:6.6-jdk14 AS build-stage
COPY . .
RUN gradle clean build


FROM openjdk:14-jdk-alpine AS production-stage

ENV APP_USER ktor
ENV APP_NAME todo-list-bff
ENV TIME_ZONE America/Sao_Paulo

RUN apk add --no-cache tzdata \
    && adduser -D -h /app $APP_USER \
    && ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE /etc/timezone

COPY --from=build-stage /home/gradle/build/libs/$APP_NAME-1.0.0-all.jar /app/$APP_NAME.jar
WORKDIR /app

RUN java -XX:DumpLoadedClassList=classes.lst -jar $APP_NAME.jar & sleep 5 && exit
RUN java -Xshare:dump -XX:SharedClassListFile=classes.lst -XX:SharedArchiveFile=shared.jsa -jar $APP_NAME.jar

EXPOSE 8080

USER $APP_USER

ENTRYPOINT java -Xshare:on -XX:SharedArchiveFile=shared.jsa -jar $APP_NAME.jar
