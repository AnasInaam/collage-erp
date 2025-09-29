package com.example.collegeerp.controller;

import com.example.collegeerp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.collegeerp.repository.DepartmentRepository;
import com.example.collegeerp.security.UserPrincipal;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Dashboard statistics and data APIs")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard statistics", description = "Get system statistics for dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY') or hasRole('STAFF') or hasRole('STUDENT') or hasRole('PARENT') or hasRole('LIBRARIAN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Get current user's role for role-specific data
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            List<String> userRoles = userPrincipal.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();
            
            // Get real data from database
            long totalUsers = userService.getTotalUsers();
            long totalDepartments = departmentRepository.count();
            
            // Calculate role-based statistics
            long estimatedStudents = Math.max(0, totalUsers - 5);
            long estimatedFaculty = Math.min(5, totalUsers);
            
            // Common stats for all roles
            stats.put("totalUsers", totalUsers);
            stats.put("totalDepartments", totalDepartments);
            stats.put("userRole", userRoles.get(0)); // Primary role
            stats.put("username", userPrincipal.getUsername());
            
            // Role-specific dashboard data
            if (userRoles.contains("ROLE_ADMIN")) {
                stats.put("totalStudents", estimatedStudents);
                stats.put("totalFaculty", estimatedFaculty);
                stats.put("totalCourses", totalDepartments * 3);
                stats.put("pendingApplications", 2);
                stats.put("systemStatus", "All systems operational");
                stats.put("dashboardType", "admin");
            } else if (userRoles.contains("ROLE_FACULTY")) {
                stats.put("myClasses", 3);
                stats.put("totalStudentsInMyClasses", 45);
                stats.put("pendingAssignments", 5);
                stats.put("upcomingExams", 2);
                stats.put("dashboardType", "faculty");
            } else if (userRoles.contains("ROLE_STUDENT")) {
                stats.put("enrolledCourses", 6);
                stats.put("completedAssignments", 12);
                stats.put("pendingAssignments", 3);
                stats.put("upcomingExams", 2);
                stats.put("currentSemester", "Spring 2025");
                stats.put("dashboardType", "student");
            } else if (userRoles.contains("ROLE_STAFF")) {
                stats.put("departmentId", 1);
                stats.put("managedStudents", estimatedStudents);
                stats.put("pendingTasks", 4);
                stats.put("dashboardType", "staff");
            } else if (userRoles.contains("ROLE_PARENT")) {
                stats.put("childrenCount", 1);
                stats.put("childProgress", "Good");
                stats.put("upcomingEvents", 3);
                stats.put("dashboardType", "parent");
            } else if (userRoles.contains("ROLE_LIBRARIAN")) {
                stats.put("totalBooks", 5000);
                stats.put("issuedBooks", 245);
                stats.put("pendingReturns", 12);
                stats.put("dashboardType", "librarian");
            } else if (userRoles.contains("ROLE_ACCOUNTANT")) {
                stats.put("totalFees", 125000);
                stats.put("collectedFees", 95000);
                stats.put("pendingFees", 30000);
                stats.put("dashboardType", "accountant");
            }
            
            // Recent activities based on role
            stats.put("recentActivities", getRoleSpecificActivities(userRoles));
            
        } catch (Exception e) {
            // Log error for debugging
            System.err.println("Error getting dashboard stats: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback to basic data
            stats.put("totalUsers", 42);
            stats.put("totalDepartments", 5);
            stats.put("error", "Using sample data due to error");
            stats.put("dashboardType", "basic");
        }
        
        return ResponseEntity.ok(stats);
    }
    
    private String[] getRoleSpecificActivities(List<String> userRoles) {
        if (userRoles.contains("ROLE_ADMIN")) {
            return new String[]{
                "New user registered: student123",
                "Department created: Computer Science", 
                "System backup completed",
                "User roles updated for faculty001"
            };
        } else if (userRoles.contains("ROLE_FACULTY")) {
            return new String[]{
                "Assignment submitted by student456",
                "New course material uploaded",
                "Exam scheduled for next week",
                "Student grades updated"
            };
        } else if (userRoles.contains("ROLE_STUDENT")) {
            return new String[]{
                "Assignment submitted: Math 101",
                "New course enrolled: Physics 201", 
                "Grade received: Chemistry A+",
                "Library book issued: Data Structures"
            };
        } else {
            return new String[]{
                "Welcome to College ERP System",
                "Profile updated successfully",
                "System notifications enabled",
                "Dashboard access granted"
            };
        }
    }

    @GetMapping("/activities")
    @Operation(summary = "Get recent activities", description = "Get recent system activities")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY') or hasRole('STAFF') or hasRole('STUDENT') or hasRole('PARENT') or hasRole('LIBRARIAN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<Map<String, Object>> getRecentActivities() {
        Map<String, Object> response = new HashMap<>();
        
        // Get current user's role for role-specific activities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        List<String> userRoles = userPrincipal.getAuthorities().stream()
            .map(authority -> authority.getAuthority())
            .toList();
        
        response.put("activities", getRoleSpecificActivities(userRoles));
        response.put("timestamp", System.currentTimeMillis());
        response.put("userRole", userRoles.get(0));
        
        return ResponseEntity.ok(response);
    }
}
