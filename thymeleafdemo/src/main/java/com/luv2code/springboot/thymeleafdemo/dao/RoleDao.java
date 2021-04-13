package com.luv2code.springboot.thymeleafdemo.dao;


import com.luv2code.springboot.thymeleafdemo.entity.Role;

public interface RoleDao {

	 Role findRoleByName(String theRoleName);
	
}
