package com.luv2code.springboot.thymeleafdemo.service;


import com.luv2code.springboot.thymeleafdemo.entity.User;
import com.luv2code.springboot.thymeleafdemo.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save(WebUser crmUser);

}
