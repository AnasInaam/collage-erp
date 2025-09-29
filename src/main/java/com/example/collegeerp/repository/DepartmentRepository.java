package com.example.collegeerp.repository;

import com.example.collegeerp.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
        /**
     * Find department by code.
     * @param code the department code
     * @return Optional department
     */
    Optional<Department> findByCodeAndIsActiveTrue(String code);
    
    Optional<Department> findByName(String name);
    
    Boolean existsByCode(String code);
    
    Boolean existsByName(String name);
    
    @Query("SELECT d FROM Department d WHERE d.name LIKE %:name% OR d.code LIKE %:name%")
    Page<Department> findByNameOrCodeContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT d FROM Department d WHERE d.isActive = true")
    List<Department> findAllActiveDepartments();
    
    @Query("SELECT COUNT(f) FROM Faculty f WHERE f.department.id = :departmentId")
    Long countFacultyByDepartmentId(@Param("departmentId") Long departmentId);
    
    @Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :departmentId")
    Long countStudentsByDepartmentId(@Param("departmentId") Long departmentId);
}
