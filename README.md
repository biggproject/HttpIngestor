# Introduction

This is the generic ingestor. It currently requires just a String of data as input.

The ingestor uses Spring Kafka, all properties are configurable using the present Spring Kafka properties.


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
docker composec exec kafka /bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic bigg
```
