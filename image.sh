#!/usr/bin/env bash
#docker pull registry.cn-hangzhou.aliyuncs.com/mengstar/mongo:latest &&
#docker pull registry.cn-hangzhou.aliyuncs.com/mengstar/redis:latest &&
#docker pull registry.cn-hangzhou.aliyuncs.com/mengstar/mysql:latest &&
#docker pull registry.cn-hangzhou.aliyuncs.com/mengstar/rabbitmq:latest &&
mvn install &&
cd mb-center && mvn clean package docker:build && cd ../ &&
cd mb-config && mvn clean package docker:build && cd ../ &&
cd mb-gateway && mvn clean package docker:build && cd ../ &&
cd mb-user && mvn clean package docker:build && cd ../ &&
docker images