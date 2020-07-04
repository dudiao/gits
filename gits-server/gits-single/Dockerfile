FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER idudiao@163.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /gits

WORKDIR /gits

EXPOSE 8200

ADD ./target/gits-single-1.0.0-SNAPSHOT.jar ./

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar gits-single-1.0.0-SNAPSHOT.jar