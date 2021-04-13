package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@Controller
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;


    @GetMapping("/add")
    public String AddingForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "EmployeeForm";
    }

    @PostMapping("/adding")
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/list")
    public String getAllEmployees(Model model) {
        List<Employee> list = employeeService.findAllByOrderByFirstNameAsc();
        model.addAttribute("searchResult", list);
        return "EmployeeTable";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeService.find(id);
        model.addAttribute("employee", employee);
        return "EmployeeForm";
    }

    @GetMapping("/delete/{id}")
    public String getAllEmployees(@PathVariable int id) {
        employeeService.delete(id);
        return "redirect:/employee/list";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute Employee employee, @RequestParam("filter") String filter,
                         Model theModel) {

        theModel.addAttribute(employee);
        if (filter != null && !filter.isEmpty()) {
            theModel.addAttribute("searchResult", employeeService.search(filter));
        } else {
            theModel.addAttribute("searchResult", employeeService.findAll());
        }
        return "EmployeeTable";
    }
}
