FROM adoptopenjdk/openjdk11:alpine-slim

ARG APP_VERSION

VOLUME /tmp

EXPOSE 5080
EXPOSE 5081
EXPOSE 5025

COPY build/libs/fake-smtp-server-*-SNAPSHOT.jar /opt/fake-smtp-server.jar
RUN ["touch", "/opt/fake-smtp-server.jar"]
ENV JAVA_OPTS=""
ENTRYPOINT exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /opt/fake-smtp-server.jar