#
# Build image
#
FROM maven:3.6.1-jdk-11 AS build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Djavax.net.ssl.trustStorePassword=changeit

#
# Image
#
FROM openjdk:11.0-jre
COPY --from=build /usr/src/app/target/*.jar /app.jar

ENV JAVA_OPTS=""
ENV SERVER_PORT 8080

EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=10s --timeout=3s \
CMD curl -v --fail http://localhost:${SERVER_PORT} || exit 1

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
