package com.example.collegeerp.service;

import com.example.collegeerp.model.Department;
import com.example.collegeerp.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAllActiveDepartments();
    }
    
    public Page<Department> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
    
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
    
    public Optional<Department> getDepartmentByCode(String code) {
        return departmentRepository.findByCode(code);
    }
    
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByCode(department.getCode())) {
            throw new RuntimeException("Department code already exists: " + department.getCode());
        }
        if (departmentRepository.existsByName(department.getName())) {
            throw new RuntimeException("Department name already exists: " + department.getName());
        }
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        // Check if code is being changed and if new code already exists
        if (!department.getCode().equals(departmentDetails.getCode()) && 
            departmentRepository.existsByCode(departmentDetails.getCode())) {
            throw new RuntimeException("Department code already exists: " + departmentDetails.getCode());
        }
        
        // Check if name is being changed and if new name already exists
        if (!department.getName().equals(departmentDetails.getName()) && 
            departmentRepository.existsByName(departmentDetails.getName())) {
            throw new RuntimeException("Department name already exists: " + departmentDetails.getName());
        }
        
        department.setName(departmentDetails.getName());
        department.setCode(departmentDetails.getCode());
        department.setDescription(departmentDetails.getDescription());
        department.setHeadOfDepartment(departmentDetails.getHeadOfDepartment());
        
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        // Check if department has faculty or students
        Long facultyCount = departmentRepository.countFacultyByDepartmentId(id);
        Long studentCount = departmentRepository.countStudentsByDepartmentId(id);
        
        if (facultyCount > 0 || studentCount > 0) {
            throw new RuntimeException("Cannot delete department. It has " + facultyCount + 
                " faculty and " + studentCount + " students assigned.");
        }
        
        departmentRepository.delete(department);
    }
    
    public Page<Department> searchDepartments(String searchTerm, Pageable pageable) {
        return departmentRepository.findByNameOrCodeContaining(searchTerm, pageable);
    }
    
    public Long getFacultyCount(Long departmentId) {
        return departmentRepository.countFacultyByDepartmentId(departmentId);
    }
    
    public Long getStudentCount(Long departmentId) {
        return departmentRepository.countStudentsByDepartmentId(departmentId);
    }
}
