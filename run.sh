echo `docker network create -d overlay springcloud-overlay` &&
cd docker &&
docker stack deploy -c db.yml db           &&
docker stack deploy -c center.yml center   &&
docker stack deploy -c config.yml config   &&
cd ../ &&
docker stack ls