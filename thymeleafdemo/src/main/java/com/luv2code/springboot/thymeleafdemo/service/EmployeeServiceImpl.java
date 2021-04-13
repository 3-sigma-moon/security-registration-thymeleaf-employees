package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public List<Employee> findAll() {
      return  employeeRepository.findAll();
    }

    @Override
    public Employee find(int id) {
        return employeeRepository.getOne(id);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);

    }

    @Override
    public void delete(int id) {
        Employee employee = employeeRepository.getOne(id);
        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> findAllByOrderByFirstNameAsc() {

       return employeeRepository.findAllByOrderByFirstNameAsc();
    }



    @Override
    public List<Employee> search(String filter) {
        return employeeRepository.search(filter);
    }


}
