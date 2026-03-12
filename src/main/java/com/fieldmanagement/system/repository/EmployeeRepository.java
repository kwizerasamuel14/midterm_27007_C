package com.fieldmanagement.system.repository;

import com.fieldmanagement.system.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    boolean existsByEmail(String email);
    
    @Query("SELECT e FROM Employee e WHERE e.province.code = :provinceCode")
    List<Employee> findByProvinceCode(@Param("provinceCode") String provinceCode);
    
    @Query("SELECT e FROM Employee e WHERE e.province.name = :provinceName")
    List<Employee> findByProvinceName(@Param("provinceName") String provinceName);
    
    Page<Employee> findAll(Pageable pageable);
}
