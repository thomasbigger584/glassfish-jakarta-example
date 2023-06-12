#!/usr/bin/env bash

docker-compose down --remove-orphans
docker-compose rm

mvn clean install

docker-compose up -d
docker-compose logs -f
