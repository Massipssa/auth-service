#!/bin/bash
USER="massipssa"
REPO="devs"
TAG="auth"
docker build -t "$USER"/"$REPO":"$TAG" .
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push "$USER"/"$REPO":"$TAG"