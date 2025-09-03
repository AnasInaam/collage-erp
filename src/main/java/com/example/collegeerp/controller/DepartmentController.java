package com.example.collegeerp.controller;

import com.example.collegeerp.model.Department;
import com.example.collegeerp.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
@Tag(name = "Department Management", description = "APIs for managing departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping
    @Operation(summary = "Get all departments", description = "Retrieve all departments with pagination and sorting")
    public ResponseEntity<Map<String, Object>> getAllDepartments(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Search term") @RequestParam(required = false) String search) {
        
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Department> departments;
            
            if (search != null && !search.trim().isEmpty()) {
                departments = departmentService.searchDepartments(search, pageable);
            } else {
                departments = departmentService.getAllDepartments(pageable);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("departments", departments.getContent());
            response.put("currentPage", departments.getNumber());
            response.put("totalItems", departments.getTotalElements());
            response.put("totalPages", departments.getTotalPages());
            response.put("hasNext", departments.hasNext());
            response.put("hasPrevious", departments.hasPrevious());
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve departments: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/list")
    @Operation(summary = "Get simple list of departments", description = "Retrieve simple list of all active departments")
    public ResponseEntity<List<Department>> getDepartmentsList() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Retrieve a specific department by its ID")
    public ResponseEntity<Map<String, Object>> getDepartmentById(
            @Parameter(description = "Department ID") @PathVariable Long id) {
        try {
            Optional<Department> department = departmentService.getDepartmentById(id);
            if (department.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("department", department.get());
                response.put("facultyCount", departmentService.getFacultyCount(id));
                response.put("studentCount", departmentService.getStudentCount(id));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Department not found with id: " + id);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve department: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/code/{code}")
    @Operation(summary = "Get department by code", description = "Retrieve a department by its code")
    public ResponseEntity<Department> getDepartmentByCode(
            @Parameter(description = "Department code") @PathVariable String code) {
        try {
            Optional<Department> department = departmentService.getDepartmentByCode(code);
            return department.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new department", description = "Create a new department (Admin only)")
    public ResponseEntity<Map<String, Object>> createDepartment(
            @Parameter(description = "Department data") @Valid @RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.createDepartment(department);
            Map<String, Object> response = new HashMap<>();
            response.put("department", createdDepartment);
            response.put("message", "Department created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create department: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update department", description = "Update an existing department (Admin only)")
    public ResponseEntity<Map<String, Object>> updateDepartment(
            @Parameter(description = "Department ID") @PathVariable Long id,
            @Parameter(description = "Updated department data") @Valid @RequestBody Department departmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("department", updatedDepartment);
            response.put("message", "Department updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update department: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete department", description = "Delete a department (Admin only)")
    public ResponseEntity<Map<String, Object>> deleteDepartment(
            @Parameter(description = "Department ID") @PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Department deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete department: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
