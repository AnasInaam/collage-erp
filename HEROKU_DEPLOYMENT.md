# Heroku Deployment Guide

## Deploy to Heroku

### Prerequisites:
- Install Heroku CLI
- Git repository

### Quick Deploy:
```bash
# 1. Login to Heroku
heroku login

# 2. Create Heroku app
heroku create your-college-erp

# 3. Add PostgreSQL addon
heroku addons:create heroku-postgresql:mini

# 4. Set environment variables
heroku config:set SPRING_PROFILES_ACTIVE=prod
heroku config:set JWT_SECRET=your-secure-jwt-secret

# 5. Deploy
git push heroku main
```

### Procfile (already exists):
```
web: java -jar target/college-erp-1.0.0.jar --server.port=$PORT
```

**Cost:** $7/month for basic dyno + database