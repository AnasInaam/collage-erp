package com.example.collegeerp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.collegeerp.repository.UserRepository;
import com.example.collegeerp.repository.DepartmentRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Dashboard statistics and data APIs")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard statistics", description = "Get system statistics for dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY') or hasRole('STAFF')")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Get total users by role
            long totalUsers = userRepository.count();
            long totalDepartments = departmentRepository.count();
            
            // For now, we'll use sample data since we don't have separate student/faculty tables yet
            stats.put("totalStudents", Math.max(0, totalUsers - 5)); // Assuming some are admins/staff
            stats.put("totalFaculty", Math.min(5, totalUsers)); // Assuming max 5 faculty
            stats.put("totalDepartments", totalDepartments);
            stats.put("totalCourses", totalDepartments * 3); // Sample calculation
            stats.put("totalUsers", totalUsers);
            
            // Additional stats
            stats.put("activeEnrollments", stats.get("totalStudents"));
            stats.put("pendingApplications", 0);
            stats.put("recentActivities", new String[]{
                "New student enrollment: John Doe",
                "Department updated: Computer Science",
                "Course schedule published",
                "Fee payment received: Jane Smith"
            });
            
        } catch (Exception e) {
            // Fallback to sample data
            stats.put("totalStudents", 25);
            stats.put("totalFaculty", 12);
            stats.put("totalDepartments", 5);
            stats.put("totalCourses", 18);
            stats.put("totalUsers", 42);
            stats.put("activeEnrollments", 25);
            stats.put("pendingApplications", 3);
        }
        
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/activities")
    @Operation(summary = "Get recent activities", description = "Get recent system activities")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY') or hasRole('STAFF')")
    public ResponseEntity<Map<String, Object>> getRecentActivities() {
        Map<String, Object> response = new HashMap<>();
        
        // Sample activities - in real implementation, this would come from an audit log
        String[] activities = {
            "New user registered: mirzaanas937",
            "Department created: Computer Science",
            "System login: admin@college.edu",
            "Configuration updated by administrator",
            "New course added: Data Structures"
        };
        
        response.put("activities", activities);
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
}
