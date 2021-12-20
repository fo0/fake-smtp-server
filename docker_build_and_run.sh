#!/bin/bash

source ./docker_build.sh
docker run --rm -p 5080:5080 $DOCKER_IMAGE_NAME