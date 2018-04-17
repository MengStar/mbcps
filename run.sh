#!/usr/bin/env bash
echo `docker network create -d overlay springcloud-overlay` &&
echo `docker network create -d overlay data-overlay` &&
cd docker &&
docker stack deploy -c base.yml base           &&
docker stack deploy -c center.yml center   &&
docker stack deploy -c config.yml config   &&
cd ../ &&
docker stack ls