Jawaban Untuk no 1,3,4 sudah include di project ini berbentuk file txt

# Spring Boot with Kafka Producer Consumer Test

This Project covers how to use Spring Boot with Spring Kafka to Consume String message
## Start Zookeeper
- `bin/zookeeper-server-start.bat config/zookeeper.properties`

## Start Kafka Server
- `bin/kafka-server-start.bat config/server.properties`

## Running springboot:
run KafkaTestApplication

## Publish message via WebService
- `http://localhost:8085`
- the output would be store at the project 'src/test/file/result.txt'
