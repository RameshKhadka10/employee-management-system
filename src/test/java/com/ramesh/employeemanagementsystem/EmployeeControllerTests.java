package com.ramesh.employeemanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramesh.employeemanagementsystem.controller.EmployeeController;
import com.ramesh.employeemanagementsystem.model.Employee;
import com.ramesh.employeemanagementsystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.hamcrest.CoreMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(value = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployeeTest() throws Exception {
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(new Employee(1, "Ramesh", "Khadka", "ramesh.khadka@gmail.com", 25, "Engineering"));
        listOfEmployees.add(new Employee(2, "Suresh", "Khadka", "suresh.khadka@gmail.com", 21, "Accountant"));
        listOfEmployees.add(new Employee(3, "Ganesh", "Khadka", "ganesh.khadka@gmail.com", 21, "Finance"));
        when(employeeService.getAllEmployees()).thenReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employee/"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        //response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployees.size())));
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        Employee employee = new Employee(1, "Ramesh", "Khadka", "ramesh.khadka@gmail.com", 25, "Engineering");

        when(this.employeeService.getEmployeeById(1)).thenReturn(employee);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employee/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        //response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployees.size())));
    }

    @Test
    void addEmployeeTest() throws Exception {
        Employee employee = new Employee(1, "Ramesh", "Khadka", "ramesh.khadka@gmail.com", 25, "Engineering");

        doNothing().when(employeeService).saveEmployee(employee);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateEmployeeTest() throws Exception {
        Employee employee = new Employee(1, "Suresh", "Khadka", "suresh.khadka@gmail.com", 25, "Engineering");

        doNothing().when(employeeService).saveEmployee(employee);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteEmployeeByIdTest() throws Exception {
        Employee employee = new Employee(1, "Ramesh", "Khadka", "ramesh.khadka@gmail.com", 25, "Engineering");

        doNothing().when(employeeService).deleteEmployeeById(1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
