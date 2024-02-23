docker run --name nginx-web-client \
	-v "/work/projects/zagrebin/java-microservices/web-client/build/":/usr/share/nginx/html:ro \
	-v "/work/projects/zagrebin/java-microservices/web-client/conf/nginx.conf":/etc/nginx/nginx.conf:ro \
	-P -d -p 3000:80 nginx:1.14.2 