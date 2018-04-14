#!/usr/bin/env bash
docker stack rm  db       &&
docker stack rm  center   &&
docker stack rm  config