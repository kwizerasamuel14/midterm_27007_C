package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.dto.EmployeeDTO;
import com.fieldmanagement.system.entity.Employee;
import com.fieldmanagement.system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO dto) {
        Employee employee = employeeService.saveEmployee(dto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<Page<Employee>> getEmployeesWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Page<Employee> employees = employeeService.getAllEmployeesWithPaginationAndSorting(page, size, sortBy, direction);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/province/code/{provinceCode}")
    public ResponseEntity<List<Employee>> getEmployeesByProvinceCode(@PathVariable String provinceCode) {
        return ResponseEntity.ok(employeeService.getEmployeesByProvinceCode(provinceCode));
    }
    
    @GetMapping("/province/name/{provinceName}")
    public ResponseEntity<List<Employee>> getEmployeesByProvinceName(@PathVariable String provinceName) {
        return ResponseEntity.ok(employeeService.getEmployeesByProvinceName(provinceName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
}
