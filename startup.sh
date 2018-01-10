#!/usr/bin/env bash

mvn clean package

docker build -t ancina/showleaderboards .

docker run -d -p 8085:8085 -t ancina/showleaderboards