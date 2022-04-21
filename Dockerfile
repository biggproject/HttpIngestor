FROM openjdk:18-alpine
ARG JAR_FILE=target/*.jar
ARG JAVA_OPTS=""
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]
