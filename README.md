# Introduction

This is a HTTP based ingestor, it uses Spring Boot REST. The ingestor also uses Spring Kafka, all properties are configurable using the present Spring Kafka properties.


# Endpoints

```
/ingest -> raw data
/ingest/harmonize -> interface for harmonizer
```


# Configuration

It is possible to configure the ingestor using Spring properties by passing **environment variables** into the docker container. Spring implements different formats to represent configuration properties ["relaxed bindings"](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-external-config.html#boot-features-external-config-relaxed-binding)). Injecting these properties as environment variables happens using the **uppercase format**.

[Find a list of all default Spring properties here](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
[Find a list of all available Kafka properties here](https://gist.github.com/geunho/77f3f9a112ea327457353aa407328771)

## Example Docker Compose Configuration Using Environment Variables

```
  bigg-ingestor:
    image: bigg-ingestor:1.0
    build:
      context: .
    ports:
      - '2181:2181'
    environment:
      SPRING_KAFKA_TEMPLATE_DEFAULTTOPIC: bigg
      SPRING_KAFKA_PRODUCER_BOOTSTRAPSERVERS: localhost:9092
      SPRING_KAFKA_PRODUCER_VALUESERIALIZER: org.springframework.kafka.support.serializer.JsonSerializer
```


# External Services

The project contains a docker compose file (`/external/docker-compose.yml`) to run both a Kafka and Zookeeper instance locally.


# Building / Pushing Docker Images

Images are pushed to the following registry: (*placeholder*) using these commands:

```
$ docker compose build
$ docker compose push
```


# Scripts

Start manual kafka producer inside container (example input: `1:data`):

```
/bin/kafka-console-producer --bootstrap-server localhost:9092 --topic bigg --property parse.key=true --property key.separator=:
```

Start consumer in kafka container to read data from topic:

```
docker compose exec kafka /bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic bigg
```
