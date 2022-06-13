# Introduction

This is a HTTP based ingestor, it uses Spring Boot REST. The ingestor also uses Spring Kafka, all properties are configurable using the present Spring Kafka properties.


# Specification

A generated OpenAPI specification document can be found [here](bigg-ingestor-openapi.json). The API has the following endpoints:

## /ingest

This endpoint takes in raw data and pushes it to kafka. It also takes a **topic** and a **key** as request parameters.

## /ingest/harmonize

This endpoint defines an interface with fields that are required for the **harmonization process**. It also takes a **topic** and a **key** as request parameters.


# Configuration

It is possible to configure the ingestor using Spring properties by passing **environment variables** into the docker container. Spring implements different formats to represent configuration properties ["relaxed bindings"](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-external-config.html#boot-features-external-config-relaxed-binding)). Injecting these properties as environment variables happens using the **uppercase format**.

[Find a list of all default Spring properties here](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)

[Find a list of all available Kafka properties here](https://gist.github.com/geunho/77f3f9a112ea327457353aa407328771)

## Example Docker Compose Configuration Using Environment Variables

```
  bigg-ingestor:
    image: biggregistry.azurecr.io/bigg-ingestor:1.0-SNAPSHOT
    build:
      context: .
    ports:
      - '2181:2181'
    environment:
      SPRING_KAFKA_TEMPLATE_DEFAULTTOPIC: bigg
      SPRING_KAFKA_PRODUCER_BOOTSTRAPSERVERS: localhost:9092
      SPRING_KAFKA_PRODUCER_VALUESERIALIZER: org.springframework.kafka.support.serializer.JsonSerializer
```

## Custom Properties

|property|default value|description|
|-|-|-|
|ingestor.kafka.send.timeout|10|How long the ingestor will wait **in seconds** to get and ACK from kafka, before returning an error.|
|spring.kafka.template.default-topic||The topic data is sent to if no topic is given for the specific request|
|spring.kafka.producer.bootstrap-servers||The hosts where kafka is deployed|
|spring.kafka.producer.value-serializer||The class used by default to convert messages to Java classes|


# External Services

The project contains a docker compose file (`/external/docker-compose.yml`) to run both a Kafka and Zookeeper instance locally.


# Building / Pushing Docker Images

Images are pushed to the following registry: `biggregistry.azurecr.io` using these commands:

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

# TODO

1. The current `HarmonizerInput` class needs to define the interface required by the Harmonizer. This interface is not known yet.
