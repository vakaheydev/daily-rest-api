FROM openjdk:21

RUN mkdir /app

WORKDIR /app

COPY vaka-daily-1.0-SNAPSHOT-spring-boot.jar /app/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]