FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar config-service-1.0-RELEASE.jar
EXPOSE 8888
ENV EUREKA_SERVER_URL=""

ENTRYPOINT ["java", "-jar", "/config-service-1.0-RELEASE.jar"]