#!/usr/bin/env bash
docker push  registry.cn-hangzhou.aliyuncs.com/mengstar/mb-center:latest  &&
docker push  registry.cn-hangzhou.aliyuncs.com/mengstar/mb-config:latest  &&
docker push  registry.cn-hangzhou.aliyuncs.com/mengstar/mb-gateway:latest &&
docker push  registry.cn-hangzhou.aliyuncs.com/mengstar/mb-user:latest