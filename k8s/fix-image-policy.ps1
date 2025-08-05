# Fix image pull policy for local images

# Eureka Server
(Get-Content eureka-deployment.yaml) -replace 'image: eureka-server:latest', 'image: eureka-server:latest
        imagePullPolicy: Never' | Set-Content eureka-deployment.yaml

# Product Service
(Get-Content product-service-deployment.yaml) -replace 'image: product-service:latest', 'image: product-service:latest
        imagePullPolicy: Never' | Set-Content product-service-deployment.yaml

# User Service
(Get-Content user-service-deployment.yaml) -replace 'image: user-service:latest', 'image: user-service:latest
        imagePullPolicy: Never' | Set-Content user-service-deployment.yaml

# API Gateway
(Get-Content api-gateway-deployment.yaml) -replace 'image: api-gateway:latest', 'image: api-gateway:latest
        imagePullPolicy: Never' | Set-Content api-gateway-deployment.yaml

Write-Host 'Image pull policy fixed for all deployments!'
