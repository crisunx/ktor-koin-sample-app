# ktor-koin-sample-app
ktor-koin-sample-app

curl -i "http://localhost:8080/health"

curl -i "http://localhost:8080/messages"

curl -i "http://localhost:8080/message/1"

curl -i -XDELETE -H 'Content-Type: application/json' "http://localhost:8080/message/1"

curl -i -XPOST -H 'Content-Type: application/json' "http://localhost:8080/message" -d '{"text": "crisun grande mestre"}'

curl -i -XPUT -H 'Content-Type: application/json' "http://localhost:8080/message/2" -d '{"text": "crisun grande mestre"}'

