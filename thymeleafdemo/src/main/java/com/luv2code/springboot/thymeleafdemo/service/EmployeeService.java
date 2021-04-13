package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;


import java.util.List;


public interface EmployeeService {
    List<Employee> findAll();
    Employee find(int id);
    void save(Employee employee);
    void delete(int id);
    List<Employee> findAllByOrderByFirstNameAsc();
    List<Employee> search(String filter);

}
