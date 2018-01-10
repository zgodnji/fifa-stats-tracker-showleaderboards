FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/showLeaderboards-1.0.0.jar /app

EXPOSE 8085

CMD ["java", "-jar", "showLeaderboards-1.0.0.jar"]
