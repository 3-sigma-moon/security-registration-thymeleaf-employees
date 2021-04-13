package com.luv2code.springboot.thymeleafdemo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {
    @GetMapping({"/showMyLoginPage"})
    protected String showMyLoginPage() {
        return "plain-login";
    }

    @GetMapping({"/Access-denied"})
    protected String showNotAuthorized() {
        return "Access-denied";
    }
}
