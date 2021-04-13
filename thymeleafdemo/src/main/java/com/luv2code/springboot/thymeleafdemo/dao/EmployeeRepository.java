package com.luv2code.springboot.thymeleafdemo.dao;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByOrderByLastNameDesc();

    List<Employee> findAllByOrderByFirstNameAsc();

    @Query(value = "select e from Employee e Where " +
            "lower(e.firstName) like lower(CONCAT('%', :filter, '%')) " +
            " or lower(e.lastName) like lower(CONCAT('%', :filter, '%')) ")
    List<Employee> search(@Param("filter") String filter);

}
