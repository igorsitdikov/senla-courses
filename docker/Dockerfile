FROM maven:3-jdk-8 as MAVEN_BUILDER

ARG LOGIN
ARG PASSWORD

RUN git clone --depth 1 --branch Final https://$LOGIN:$PASSWORD@git-courses.senlainc.com/senla-java-MINSK1-group/sitdzikau_ihar.git /opt/tmp && \
    cd /opt/tmp && \
    mvn clean -DskipTests install

WORKDIR /opt/tmp/

FROM openjdk:8-jre-alpine

MAINTAINER ihar.sitdzikau@yandex.by

COPY --from=MAVEN_BUILDER /opt/tmp/application/target/application-1.0.0.jar /opt/bulletin-board.jar

ENTRYPOINT ["java", "-jar", "/opt/bulletin-board.jar"]