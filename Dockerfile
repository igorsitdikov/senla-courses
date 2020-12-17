FROM adoptopenjdk/openjdk8:jre

ENV HOME /home/user
RUN useradd --create-home --home-dir $HOME user \
	&& chown -R user:user $HOME

COPY ./application/target/application-1.0.0.jar /bulletin-board.jar

ENTRYPOINT ["java", "-jar", "/bulletin-board.jar"]