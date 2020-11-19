FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8282
WORKDIR /project
ADD ./target/springboot-cache-redis-0.0.1.SNAPSHOT.jar  /project
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]