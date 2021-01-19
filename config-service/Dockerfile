FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
ADD target/*.jar config-service-1.0-RELEASE.jar
EXPOSE 8888
ENV EUREKA_SERVER_URL=""

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/config-service-1.0-RELEASE.jar"]