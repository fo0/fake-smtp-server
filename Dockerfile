FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="fo0@fo0me"

EXPOSE 5080
EXPOSE 5081
EXPOSE 5025

COPY build/libs/fake-smtp-server-*-SNAPSHOT.jar /opt/app.jar
WORKDIR /opt
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","app.jar"]