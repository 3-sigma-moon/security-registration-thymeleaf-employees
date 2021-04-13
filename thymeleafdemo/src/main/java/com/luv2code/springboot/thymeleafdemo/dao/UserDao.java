package com.luv2code.springboot.thymeleafdemo.dao;


import com.luv2code.springboot.thymeleafdemo.entity.User;

public interface UserDao  {
    User findByUserName(String userName);

    void save(User user);
}
