package com.ramesh.employeemanagementsystem.service;

import com.ramesh.employeemanagementsystem.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployeeById(long id);

    void deleteEmployeeById(long id);
}
