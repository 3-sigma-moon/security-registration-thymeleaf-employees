package com.luv2code.springboot.thymeleafdemo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String userName;


    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private List<Role> roles;


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(String userName, String password, int enabled, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.lastName = lastName;
        this.email = email;
        this.roles = new ArrayList<>();

    }

    public User() {

    }


    public Long getId() {
        return id;
    }

    public void ListId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void addRole(Role role) {
        if (role == null) {
            this.roles = new ArrayList<>();
        }
        roles.add(role);

    }


    @Override
    public String toString() {
        return "\nId: " + id + "\t\tName: " + userName;
    }
}