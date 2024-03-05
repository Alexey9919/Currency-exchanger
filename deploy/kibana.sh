docker run --name kibana --link elasticsearch:elasticsearch \
    -e xpack.security.enabled=false -e ELASTICSEARCH_URL="http://elasticsearch:9300" \
    -d -p 5601:5601 kibana:8.3.3