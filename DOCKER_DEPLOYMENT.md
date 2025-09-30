# Docker Deployment Options

## Local Docker Testing:
```bash
# Build image
docker build -t college-erp .

# Run container
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local college-erp
```

## Deploy to:

### 🐙 **DigitalOcean App Platform**
- Upload to Docker Hub
- Connect to DigitalOcean
- One-click deployment
- Cost: $12/month

### 🏃 **Google Cloud Run**
```bash
# Build and deploy
gcloud run deploy college-erp --source . --platform managed --region us-central1 --allow-unauthenticated
```
- Cost: Pay per request (very cheap)

### 🌊 **AWS ECS/Fargate**
- Enterprise-grade
- Auto-scaling
- Cost: ~$20/month

### 🦅 **Fly.io**
```bash
# Deploy with Dockerfile
flyctl launch
flyctl deploy
```
- Cost: $3/month