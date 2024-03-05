docker run --name logstash -h logstash --link elasticsearch:elasticsearch \
    -v "C:\Users\HUAWEI\Desktop\currency-exchanger-backend-app\currency-exchanger-backend-app\deploy\conf\pipelines.yml":/usr/share/logstash/config/pipelines.yml:ro \
    -v "C:\Users\HUAWEI\Desktop\currency-exchanger-backend-app\currency-exchanger-backend-app\deploy\conf":/usr/share/logstash/config/pipelines:ro \
    -d -p 5000:5000 logstash:8.3.3
