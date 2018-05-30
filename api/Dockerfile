FROM maven:alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn verify clean --fail-never

COPY . .
RUN mvn package

FROM openjdk:alpine
WORKDIR /app

COPY --from=build /app/target/spring-boot-web-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]