package com.luv2code.springboot.thymeleafdemo.controller;


import com.luv2code.springboot.thymeleafdemo.entity.User;
import com.luv2code.springboot.thymeleafdemo.service.UserService;
import com.luv2code.springboot.thymeleafdemo.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @Autowired
    private UserDetailsManager userDetailsManager;



    @Autowired
    private UserService userService;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Logger logger = Logger.getLogger(getClass().getName());



    private Map<String, String> roles;

    @PostConstruct
    protected void loadRoles() {
        roles = new LinkedHashMap<>();

        roles.put("ROLE_EMPLOYEE", "Employee");
        roles.put("ROLE_MANAGER", "Manager");
        roles.put("ROLE_ADMIN", "Admin");
    }

    private boolean doesUserExist(String userName) {

        logger.info("Checking if user exists: " + userName);


        boolean exists = userDetailsManager.userExists(userName);

        logger.info("User: " + userName + ", exists: " + exists);

        return exists;
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {

        theModel.addAttribute("WebUser", new WebUser());
        theModel.addAttribute("roles", roles);

        return "registration-form";

    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
             @ModelAttribute("WebUser") WebUser theWebUser,
            BindingResult theBindingResult,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes,
            Model theModel) {

        redirectAttributes.addFlashAttribute("WebUser", theWebUser);

        String userName = theWebUser.getUserName();

        logger.info("Processing registration form for: " + userName);

        User existedUser = userService.findByUserName(userName);

        if (theBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.WebUser",
                    theBindingResult);
            theModel.addAttribute("WebUser",theWebUser);
            theModel.addAttribute("roles", roles);
            return "registration-form";
        }
        if (existedUser != null) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.WebUser",
                    theBindingResult);
            theModel.addAttribute("WebUser", theWebUser);
            theModel.addAttribute("registrationError", "User name already exists.");
            theModel.addAttribute("roles", roles);
            return "registration-form";
        }


        List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList();

        String formRole = theWebUser.getFormRole();
        if (!formRole.equals("ROLE_EMPLOYEE")) {
            auths.add(new SimpleGrantedAuthority(formRole));
        }


        userService.save(theWebUser);



        sessionStatus.setComplete();

        return "registration-confirmation";


    }


}
