# ktor-koin-sample-app
> Ktor is a web application framework written in Kotlin that utilizes Coroutines to provide the fully asynchronous, non-blocking behavior.
 Shows usage of  ktor to create a simple API to store simple text snippets.
## Build

Building the project
```sh
./gradlew clean && ./gradlew build
```

Compiling the project and generating a docker image
```sh
./gradlew clean && ./gradlew build && docker build --no-cache -t crisun/ktor-sample-app .
```

Compiling the project and generating a docker image with docker-compose
```sh
./gradlew clean && ./gradlew build && docker-compose build
```

## Start

Using docker-compose:
```sh
docker-compose up
```

Using docker:
```sh
docker run --rm -it -p 8080:8080 crisun/ktor-sample-app
```

Running manually:
```sh
java -jar ktor-sample-app-1.0.0-all.jar
```

## Example of use

Start the application and call the operations:

List all messages:
```sh
curl -i "http://localhost:8080/messages"
```
Query a message:
```sh
curl -i "http://localhost:8080/message/1"
```
Insert message:
```sh
curl -i -XPOST -H 'Content-Type: application/json' "http://localhost:8080/message" -d '{"text": "crisun grande mestre"}'
```
Update message:
```sh
curl -i -XPUT -H 'Content-Type: application/json' "http://localhost:8080/message/1" -d '{"text": "crisun grande mestre"}'
```
Delete message:
```sh
curl -i -XDELETE -H 'Content-Type: application/json' "http://localhost:8080/message/1"
```
Excluir uma rotina:
```sh
curl -i "http://localhost:8080/health"
```

## Libraries
Koin: https://insert-koin.io
flywaydb: https://flywaydb.org
Logback: http://logback.qos.ch
Spring Jdbc: https://spring.io
h2database: https://www.h2database.com
H2 Database: https://www.h2database.com
HikariCP: https://github.com/brettwooldridge/HikariCP
Kotlin Logging: https://github.com/MicroUtils/kotlin-logging
Ktor flyway feature: https://github.com/viartemev/ktor-flyway-feature
