FROM ubuntu:latest AS build

RUN apt update
RUN apt upgrade -y
RUN apt install openjdk-17-jdk -y
COPY . .
RUN apt install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/todo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]