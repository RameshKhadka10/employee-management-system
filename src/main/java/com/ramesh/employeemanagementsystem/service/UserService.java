package com.ramesh.employeemanagementsystem.service;

import com.ramesh.employeemanagementsystem.model.Employee;
import com.ramesh.employeemanagementsystem.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    void saveUser(User user);

    User getUserByUsername(String username);

}
