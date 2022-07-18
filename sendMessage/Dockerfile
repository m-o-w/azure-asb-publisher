FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} sendMessageApplication.jar
ENTRYPOINT ["java","-jar","/sendMessageApplication.jar"]
EXPOSE 8080