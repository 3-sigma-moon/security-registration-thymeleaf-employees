package com.luv2code.springboot.thymeleafdemo.config;


import com.luv2code.springboot.thymeleafdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;


import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    // add a reference to our user service
    @Autowired
    private UserService userService;

    @Autowired
    private DataSource securityDataSource;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//	private final String usersQuery ="select username, password, '1' as enabled from user where username=? and enabled=1";
//
//	private final String rolesQuery = "select u.username, r.role_name from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.username=?" ;
//
//
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
//				.dataSource(securityDataSource).passwordEncoder(bCryptPasswordEncoder);
//	}


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .antMatchers("/employee/list").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers("/employee/add/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/employee/update/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/employee/delete/**").hasAuthority("ROLE_ADMIN")


                .and()
                .formLogin()
                .loginPage("/security/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/security/Access-denied");
        ;

    }

    @Bean
    public UserDetailsManager userDetailsManager() {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

        jdbcUserDetailsManager.setDataSource(securityDataSource);

        return jdbcUserDetailsManager;
    }


    //beans
    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

}






