FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar zuul-gateway-service-1.0-RELEASE.jar
ENV CONFIG_SERVER_URL=""
ENV SPR_PROFILE=""
EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/zuul-gateway-service-1.0-RELEASE.jar"]