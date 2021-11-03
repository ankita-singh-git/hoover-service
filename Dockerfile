FROM adoptopenjdk/maven-openjdk11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn clean test package

FROM adoptopenjdk/openjdk11:alpine-jre
ARG WAR_FILE=target/hoover-service-0.0.1-SNAPSHOT.jar
COPY --from=build /home/app/${WAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]