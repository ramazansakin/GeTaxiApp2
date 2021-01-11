FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar location-tracker-service-1.0-RELEASE.jar
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/location-tracker-service-1.0-RELEASE.jar"]