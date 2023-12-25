FROM openjdk:17-jdk-alpine3.14
COPY ./build/libs/crm-base-service-1.0.1.jar crm-base-service.jar
ENTRYPOINT ["java", "-jar", "crm-base-service.jar"]
