# Contributing to College ERP

Thank you for your interest in contributing to the College ERP project! This document provides guidelines for contributing to the project.

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Git
- Supabase account (for database)

### Development Setup
1. Fork the repository
2. Clone your fork: `git clone https://github.com/your-username/collage-erp.git`
3. Set up your development environment
4. Create a feature branch: `git checkout -b feature/your-feature-name`

## 🔄 Development Workflow

### 1. Code Style
- Follow standard Java conventions
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Keep methods small and focused

### 2. Commit Guidelines
- Use descriptive commit messages
- Follow conventional commits format:
  ```
  type(scope): description
  
  feat(auth): add JWT token validation
  fix(database): resolve connection pool issue
  docs(readme): update installation instructions
  ```

### 3. Pull Request Process
1. Ensure your code follows the project style
2. Update documentation if needed
3. Add tests for new features
4. Ensure all tests pass
5. Create a descriptive pull request

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserControllerTest

# Run with coverage
mvn test jacoco:report
```

### Writing Tests
- Write unit tests for new functionality
- Use meaningful test names
- Follow AAA pattern (Arrange, Act, Assert)
- Mock external dependencies

## 📋 Issue Guidelines

### Reporting Bugs
When reporting bugs, please include:
- Steps to reproduce
- Expected behavior
- Actual behavior
- Environment details (OS, Java version, etc.)
- Log files if relevant

### Feature Requests
For feature requests, please provide:
- Clear description of the feature
- Use case or problem it solves
- Proposed implementation approach (if you have one)

## 🏗️ Project Structure

```
src/
├── main/java/com/example/
│   ├── springbootdemo/          # Main application
│   └── collegeerp/              # ERP modules
│       ├── controller/          # REST endpoints
│       ├── model/              # JPA entities
│       ├── repository/         # Data access
│       ├── service/            # Business logic
│       └── security/           # Security config
├── main/resources/
│   ├── static/                 # Frontend assets
│   └── application*.properties # Configuration
└── test/                       # Test classes
```

## 🔧 Code Review Process

1. All changes must be reviewed before merging
2. Reviewers will check for:
   - Code quality and style
   - Test coverage
   - Documentation updates
   - Security considerations
   - Performance implications

## 📖 Documentation

- Update README.md for significant changes
- Add Javadoc for new public APIs
- Update API documentation if endpoints change
- Include examples for new features

## 🚦 Branch Strategy

- `main`: Production-ready code
- `develop`: Integration branch for features
- `feature/*`: New features
- `bugfix/*`: Bug fixes
- `hotfix/*`: Critical production fixes

## 📞 Getting Help

- Open an issue for questions
- Check existing issues before creating new ones
- Contact maintainers via GitHub

## 🙏 Recognition

Contributors will be recognized in:
- GitHub contributors list
- Release notes
- Project documentation

Thank you for contributing to College ERP! 🎓
