# user-microservice

Spring Boot service that exposes user data through a REST API.

## Development
```
./gradlew bootRun
```

## Docker
```
docker build -t user-service:latest .
```

## Kubernetes
Apply `k8s-user.yaml` once you update the image reference.
