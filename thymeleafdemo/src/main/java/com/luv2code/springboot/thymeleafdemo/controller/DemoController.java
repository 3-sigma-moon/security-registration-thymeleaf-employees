package com.luv2code.springboot.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping({"/", "/home"})
    public String showHome() {

        return "home";
    }


    @GetMapping("/leaders")
    public String showLeaders() {

        return "leaders";
    }


    @GetMapping("/systems")
    public String showSystems() {

        return "systems";
    }

}










