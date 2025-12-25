./gradlew clean build
eval $(minikube docker-env)  
docker build -t user-service:1.0 .
kubectl delete pod -l app=user-service
kubectl get pods -l app=user-service