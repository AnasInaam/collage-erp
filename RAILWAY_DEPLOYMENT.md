# Railway Deployment Guide

## Quick Deploy to Railway

1. **Sign up at https://railway.app**
2. **Connect your GitHub account**
3. **Create new project from GitHub repo**
4. **Add PostgreSQL database**
5. **Set environment variables**

## Environment Variables Needed:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=postgresql://user:pass@host:port/db
SPRING_DATASOURCE_URL=${DATABASE_URL}
JWT_SECRET=your-secure-jwt-secret-key-here
PORT=8080
```

## Railway will automatically:
- Detect Spring Boot application
- Build with Maven
- Deploy with zero configuration
- Provide HTTPS domain
- Handle database connections

## Cost: FREE for small projects