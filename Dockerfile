FROM maven:3.6.3-jdk-11-slim AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
COPY src ./src
RUN ls -la ./src
RUN mvn clean package -Dmaven.test.skip=true
RUN ls -la /app

FROM openjdk:11.0.6-jdk
LABEL maintainer="developer@mitocode.com"
WORKDIR /workspace
RUN ls -la /workspace
COPY --from=builder /app/target/api*.jar app.jar
RUN ls -la /workspace
ENTRYPOINT exec java -jar /workspace/app.jar