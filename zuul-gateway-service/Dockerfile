FROM openjdk:8-jdk-alpine

MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar zuul-gateway-service-1.0-RELEASE.jar

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=docker", "/zuul-gateway-service-1.0-RELEASE.jar"]

EXPOSE 9000