package com.luv2code.springboot.thymeleafdemo.service;


import com.luv2code.springboot.thymeleafdemo.dao.UserDao;
import com.luv2code.springboot.thymeleafdemo.entity.Role;
import com.luv2code.springboot.thymeleafdemo.entity.User;
import com.luv2code.springboot.thymeleafdemo.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User findByUserName(String userName) {


        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(WebUser crmUser) {
        User user = new User();


        user.setUserName(crmUser.getUserName());


        crmUser.setEnabled(1);

        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setEnabled(crmUser.getEnabled());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());


        user.setRoles(Arrays.asList(new Role(crmUser.getFormRole())));



        userDao.save(user);

    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws
            UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new
                org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>
    mapRolesToAuthorities(Collection<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new
                    SimpleGrantedAuthority(role.getName());
            list.add(simpleGrantedAuthority);
        }

        return list;
    }
}