FROM maven:alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn verify clean --fail-never

COPY ./src ./src
RUN mvn package

FROM jetty:alpine
USER jetty:jetty
COPY --from=build /app/target/hapi-fhir-jpaserver-example.war /var/lib/jetty/webapps/root.war
VOLUME [ "/var/lib/jetty/target" ]
EXPOSE 8084