# ktor-koin-sample-app
ktor-koin-sample-app

curl -XGET -H 'Content-Type: application/json'"http://localhost:8080/messages" 
curl -XDELETE -H 'Content-Type: application/json' "http://localhost:8080/message/9"	
curl -XPOST -H 'Content-Type: application/json' "http://localhost:8080/message" -d '{"text": "crisun grande mestre"}'
curl -XPUT -H 'Content-Type: application/json' "http://localhost:8080/message/1" -d '{"text": "crisun grande mestre"}'

