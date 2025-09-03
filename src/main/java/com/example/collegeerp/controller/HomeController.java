package com.example.collegeerp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Home", description = "Home page and status APIs")
public class HomeController {
    
    @GetMapping("/")
    @Operation(summary = "Welcome page", description = "Display welcome page with available endpoints")
    public String home() {
        return "index.html";
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard page", description = "User dashboard page")
    public String dashboard() {
        return "dashboard.html";
    }

    @GetMapping("/welcome")
    @ResponseBody
    @Operation(summary = "API Welcome", description = "Display welcome page with available endpoints")
    public String welcome() {
        return "<html><head><title>🎓 College ERP System</title></head><body>" +
               "<h1>🎓 Welcome to College ERP System!</h1>" +
               "<h2>🚀 Available Endpoints:</h2>" +
               "<ul>" +
               "<li><a href='/api/auth/signin'>POST /api/auth/signin</a> - User login</li>" +
               "<li><a href='/api/auth/signup'>POST /api/auth/signup</a> - User registration</li>" +
               "<li><a href='/swagger-ui.html'>📚 API Documentation</a> - Swagger UI</li>" +
               "<li><a href='/h2-console'>💾 H2 Database Console</a></li>" +
               "<li><a href='/actuator/health'>💚 Health Check</a></li>" +
               "</ul>" +
               "<h3>🔗 Sample API Calls:</h3>" +
               "<ul>" +
               "<li>GET /api/departments - Get all departments</li>" +
               "<li>GET /api/courses - Get all courses</li>" +
               "<li>GET /api/students - Get all students</li>" +
               "<li>GET /api/faculty - Get all faculty</li>" +
               "</ul>" +
               "<p><strong>Status:</strong> ✅ College ERP System is running successfully!</p>" +
               "<p><strong>Version:</strong> v1.0.0</p>" +
               "<p><strong>Environment:</strong> Development</p>" +
               "</body></html>";
    }
    
    @GetMapping("/status")
    @ResponseBody
    @Operation(summary = "System status", description = "Get system status and information")
    public String status() {
        return "{\n" +
               "  \"status\": \"running\",\n" +
               "  \"application\": \"College ERP System\",\n" +
               "  \"version\": \"1.0.0\",\n" +
               "  \"environment\": \"development\",\n" +
               "  \"message\": \"College ERP System is working perfectly!\",\n" +
               "  \"endpoints\": {\n" +
               "    \"authentication\": \"/api/auth/*\",\n" +
               "    \"departments\": \"/api/departments\",\n" +
               "    \"courses\": \"/api/courses\",\n" +
               "    \"students\": \"/api/students\",\n" +
               "    \"faculty\": \"/api/faculty\",\n" +
               "    \"documentation\": \"/swagger-ui.html\",\n" +
               "    \"database\": \"/h2-console\"\n" +
               "  }\n" +
               "}";
    }
}
