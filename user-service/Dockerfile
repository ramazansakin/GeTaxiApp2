FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar user-service-1.0-RELEASE.jar
ENV CONFIG_SERVER_URL=""
ENV SPR_PROFILE=""
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "/user-service-1.0-RELEASE.jar"]