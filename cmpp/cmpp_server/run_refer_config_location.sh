#!/usr/bin/env bash

if [ $# -ne 1 ];then
  echo "Usage: $0 <config-location>"
  exit 1
fi

java -jar target/cmpp_server-0.0.1-SNAPSHOT.jar --spring.config.additional-location="$1"