FROM openjdk:8-jre-alpine

MAINTAINER song_yinyin@163.com

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /gits
WORKDIR /gits
EXPOSE 8200

ADD target/gits-single-1.0.0-SNAPSHOT.jar ./gits-single.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS  /gits/gits-single.jar $PARAMS"]