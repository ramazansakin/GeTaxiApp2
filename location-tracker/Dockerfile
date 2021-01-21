FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar location-tracker-service-1.0-RELEASE.jar
EXPOSE 9005

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/location-tracker-service-1.0-RELEASE.jar"]