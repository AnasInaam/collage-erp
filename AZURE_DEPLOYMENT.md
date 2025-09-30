# Azure App Service Deployment Guide

## Deploy to Azure App Service

### Option 1: Azure CLI
```bash
# 1. Login to Azure
az login

# 2. Create resource group
az group create --name college-erp-rg --location "East US"

# 3. Create App Service plan
az appservice plan create --name college-erp-plan --resource-group college-erp-rg --sku B1 --is-linux

# 4. Create web app
az webapp create --name your-college-erp --resource-group college-erp-rg --plan college-erp-plan --runtime "JAVA:21-java21"

# 5. Deploy JAR file
az webapp deploy --resource-group college-erp-rg --name your-college-erp --src-path target/college-erp-1.0.0.jar --type jar

# 6. Configure app settings
az webapp config appsettings set --resource-group college-erp-rg --name your-college-erp --settings SPRING_PROFILES_ACTIVE=prod JWT_SECRET=your-secret
```

### Option 2: GitHub Actions (automated)
Your GitHub Actions workflow can deploy to Azure!

**Cost:** ~$13/month for B1 basic plan