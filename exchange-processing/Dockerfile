FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine
ADD target/*.jar app.jar

ENV DB_HOST=localhost
ENV EUREKA_HOST=localhost
ENV CURRENCY_URL="http://localhost:8085"


EXPOSE 8090

ENTRYPOINT [ "sh", "-c", "java \
    -Dspring.profiles.active=cloud \
    -jar /app.jar \
    --cloud.db-host=$DB_HOST \
    --cloud.eureka-host=$EUREKA_HOST --cloud.currency-url=$CURRENCY_URL \
    " ]
