docker-compose down -v
docker rm -f $(docker ps -q)
docker rmi identify-docker-image:latest
docker build -t identify-docker-image:latest .
docker-compose up -d db
sleep 10
docker-compose up app

