FROM openjdk:17-jdk-alpine3.14
COPY ./build/libs/crm-base-service-0.0.1-SNAPSHOT.jar crm-base-service.jar
ENTRYPOINT ["java", "-jar", "crm-base-service.jar"]
