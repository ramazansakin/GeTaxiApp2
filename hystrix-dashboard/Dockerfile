FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar hystrix-service-1.0-RELEASE.jar
EXPOSE 9090

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=docker", "/hystrix-service-1.0-RELEASE.jar"]