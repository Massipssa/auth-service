FROM adoptopenjdk/openjdk11
ARG JAR_NAME=auth-service.jar
ARG JAR_FILE=target/*.jar

COPY "${JAR_FILE}" "${JAR_NAME}"
EXPOSE 8092
ENTRYPOINT ["java","-jar","/auth-service.jar"]