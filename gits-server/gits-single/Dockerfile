FROM openjdk:8-jre-slim
MAINTAINER idudiao@163.com

ENV PARAMS=""

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /gits
WORKDIR /gits
EXPOSE 8200

ADD target/gits-single-1.0.0-SNAPSHOT.jar ./

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /gits/gits-single-1.0.0-SNAPSHOT.jar $PARAMS"]