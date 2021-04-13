package com.luv2code.springboot.thymeleafdemo.user;


import com.luv2code.springboot.thymeleafdemo.validation.FieldMatch;
import com.luv2code.springboot.thymeleafdemo.validation.ValidEmail;


import javax.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "Passwords don't match")
})
public class WebUser {

    @NotNull(message = "is required")
    private String formRole;

    private int enabled;


    @NotNull(message = "is required")
    @Size(min = 2, message = "At least two characters")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;


    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 2, message = "At least two characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Please only characters")
    private String lastName;


    @ValidEmail
    private String email;


    public WebUser() {

    }

    public String getFormRole() {
        return formRole;
    }

    public void setFormRole(String formRole) {
        this.formRole = formRole;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
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


    public WebUser(String formRole, String userName, String password, String matchingPassword, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.lastName = lastName;
        this.email = email;
        this.formRole = formRole;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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
}
