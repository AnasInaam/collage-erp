#!/usr/bin/env bash

# PostgreSQL Compatibility Test Script
# This script tests if your application can connect to PostgreSQL

echo "🧪 Testing PostgreSQL Compatibility..."

# Test 1: Check if PostgreSQL driver is in classpath
echo "📦 Checking PostgreSQL driver..."
mvn dependency:tree | grep postgresql && echo "✅ PostgreSQL driver found" || echo "❌ PostgreSQL driver missing"

# Test 2: Validate application properties
echo "⚙️ Validating application properties..."
if grep -q "spring.datasource.driver-class-name=org.postgresql.Driver" src/main/resources/application-prod.properties; then
    echo "✅ PostgreSQL driver configured"
else
    echo "❌ PostgreSQL driver not configured"
fi

# Test 3: Check for H2-specific configurations that might conflict
echo "🔍 Checking for H2 conflicts..."
if grep -q "h2" src/main/resources/application-prod.properties; then
    echo "⚠️ H2 references found in production config"
else
    echo "✅ No H2 conflicts in production"
fi

# Test 4: Build the application
echo "🔨 Building application..."
mvn clean compile -q && echo "✅ Build successful" || echo "❌ Build failed"

# Test 5: Check entity annotations for PostgreSQL compatibility
echo "🏗️ Checking entity annotations..."
if grep -r "GenerationType.IDENTITY" src/main/java/; then
    echo "✅ Using IDENTITY generation (PostgreSQL compatible)"
else
    echo "⚠️ Check ID generation strategy"
fi

echo "✨ PostgreSQL compatibility check complete!"

# Instructions for manual testing
echo ""
echo "🚀 Manual Testing Instructions:"
echo "1. Start a local PostgreSQL instance"
echo "2. Create database: CREATE DATABASE college_erp_test;"
echo "3. Update application-test.properties with PostgreSQL URL"
echo "4. Run: mvn spring-boot:run -Dspring-boot.run.profiles=test"
echo "5. Check if tables are created automatically"