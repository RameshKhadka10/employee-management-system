package com.ramesh.employeemanagementsystem.controller;

import com.ramesh.employeemanagementsystem.model.Employee;
import com.ramesh.employeemanagementsystem.payload.response.RestResponse;
import com.ramesh.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee/")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<RestResponse> getEmployeeDetails(@PathVariable(value = "id") long id) {
        RestResponse response = RestResponse.success().build(this.employeeService.getEmployeeById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RestResponse> getAllEmployee() {
        RestResponse response = RestResponse.success().build(this.employeeService.getAllEmployees());
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<RestResponse> addEmployee(@Valid @RequestBody Employee employee) {
        this.employeeService.saveEmployee(employee);
        RestResponse response = RestResponse.success().build(null);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<RestResponse> updateEmployee(@Valid @RequestBody Employee employee) {
        this.employeeService.saveEmployee(employee);
        RestResponse response = RestResponse.success().build(null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestResponse> deleteEmployeeById(@PathVariable(value = "id") long id) {
        this.employeeService.deleteEmployeeById(id);
        RestResponse response = RestResponse.success().build(null);
        return ResponseEntity.ok(response);
    }

}
