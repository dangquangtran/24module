#!/bin/bash

# Deploy to Kubernetes
echo "Deploying to Kubernetes..."

# 1. Create namespace
echo "Creating namespace..."
kubectl apply -f namespace.yaml

# 2. Create secrets and configmaps
echo "Creating secrets and configmaps..."
kubectl apply -f secrets.yaml
kubectl apply -f configmaps.yaml

# 3. Deploy infrastructure
echo "Deploying infrastructure..."
kubectl apply -f postgres-deployment.yaml
kubectl apply -f redis-deployment.yaml
kubectl apply -f kafka-deployment.yaml
kubectl apply -f temporal-deployment.yaml
kubectl apply -f zipkin-deployment.yaml

# 4. Deploy applications
echo "Deploying applications..."
kubectl apply -f eureka-deployment.yaml
kubectl apply -f product-service-deployment.yaml
kubectl apply -f user-service-deployment.yaml
kubectl apply -f api-gateway-deployment.yaml

# 5. Check status
echo "Checking deployment status..."
kubectl get pods -n assignment1-24module
kubectl get services -n assignment1-24module

echo "Deployment completed!"
echo "Access API Gateway: kubectl port-forward service/api-gateway 8090:80 -n assignment1-24module"
