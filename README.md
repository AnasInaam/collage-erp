# College ERP System

A comprehensive Enterprise Resource Planning (ERP) system for educational institutions, built with Spring Boot and PostgreSQL.

## 🚀 Features

- **User Management**: Role-based access control (Admin, Student, Faculty)
- **Authentication**: JWT-based secure authentication
- **Dashboard**: Role-specific dashboards with personalized content
- **RESTful API**: Complete REST API with Swagger documentation
- **Database**: PostgreSQL only (no H2 dependency)
- **Responsive UI**: Modern, mobile-friendly interface

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **Frontend**: HTML5, CSS3, JavaScript
- **Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database

## 🏃‍♂️ Quick Start (Local Development)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/AnasInaam/collage-erp.git
   cd collage-erp
   ```

2. **Set environment variables**:
   ```bash
   set DATABASE_URL=jdbc:postgresql://localhost:5432/college_erp
   set DB_USERNAME=your_username
   set DB_PASSWORD=your_password
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - Main App: http://localhost:8080
   - API Docs: http://localhost:8080/swagger-ui.html
   - Health Check: http://localhost:8080/actuator/health

## 🔑 Default Users

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@college.edu | admin123 |
| Student | student@college.edu | student123 |
| Faculty | faculty@college.edu | faculty123 |

## 📊 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Dashboard
- `GET /api/dashboard/admin` - Admin dashboard
- `GET /api/dashboard/student` - Student dashboard
- `GET /api/dashboard/faculty` - Faculty dashboard

## 🏗️ Project Structure

```
src/main/java/com/example/collegeerp/
├── controller/          # REST Controllers
├── service/             # Business Logic
├── repository/          # Data Access Layer
├── model/               # Entity Classes
├── config/              # Configuration
└── security/            # Security Config
```

## 🔧 Development Commands

```bash
# Build project
mvn clean compile

# Run tests
mvn test

# Create JAR
mvn clean package

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 📝 License

MIT License - see LICENSE file for details.