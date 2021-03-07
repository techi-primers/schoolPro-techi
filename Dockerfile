FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} investMe.jar
ENTRYPOINT ["java","-jar","/investMe.jar"]