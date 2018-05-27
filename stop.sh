#!/usr/bin/env bash
docker stack rm  base       &&
docker stack rm  center     &&
docker stack rm  config     &&
docker stack rm  gateway    &&
docker stack rm  mb