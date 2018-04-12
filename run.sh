cd docker &&
docker stack deploy -c db.yml db           &&
docker stack deploy -c center.yml center   &&
docker stack deploy -c config.yml config   &&
cd ../