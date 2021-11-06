FROM gradle:jdk15 as compile

COPY . /home/java

WORKDIR /home/java

USER root

RUN chown -R gradle /home/java

USER gradle

RUN gradle clean build -x test

FROM openjdk:15-alpine

WORKDIR /home/app/java

COPY --from=compile "/home/java/build/libs/Backend-0.0.1-SNAPSHOT.jar" .

RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime \
    && echo "Asia/Tokyo" > /etc/timezone \
    && apk del tzdata

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/home/app/java/Backend-0.0.1-SNAPSHOT.jar"]
