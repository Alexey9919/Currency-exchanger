FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine
ADD build/libs/notification-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8020

RUN mkdir /conf

VOLUME /conf

ENV EUREKA_HOST=localhost
ENV KAFKA_HOST=localhost
ENV AUTH_TOKEN_URL="http://localhost:9000/oauth/check_token"
ENV USER_INFO_URL="http://localhost:9000/user"


ENTRYPOINT [ "sh", "-c", "java \
    -Dspring.profiles.active=cloud \
    -jar /app.jar \
    --cloud.eureka-host=$EUREKA_HOST \
    --cloud.db-host=$DB_HOST --cloud.kafka-host=$KAFKA_HOST \
    --cloud.auth-token-url=$AUTH_TOKEN_URL --cloud.user-info-url=$USER_INFO_URL \
    " ]
