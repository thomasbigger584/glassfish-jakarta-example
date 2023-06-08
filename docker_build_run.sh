#!/usr/bin/env bash

docker-compose down --remove-orphans
docker-compose rm

mvn clean install

mkdir -p ./deployments/
rm -rf ./deployments/*
cp ./target/RestGlassfishHelloWorld-1.0-SNAPSHOT.war ./deployments/

docker-compose up -d
docker-compose logs -f
