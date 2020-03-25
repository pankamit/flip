package com.flip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.flip.enumeration.Roles;
import com.flip.filter.JwtRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.userDetailsService(userDetailsService);
//    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        		.antMatchers("/h2-console/**").permitAll()
                .antMatchers("/admin/**").hasRole(Roles.ADMIN.name())
                .antMatchers("/user/**").permitAll()
//                .antMatchers("/user/**").hasRole(Roles.USER.name())
                .antMatchers("/helloUser").permitAll()
                .antMatchers("/addUser").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/forgetpassword/**").permitAll()   
                .antMatchers("/validateForgetpassword").permitAll() 
                .anyRequest().authenticated()
                .and().formLogin();
        
        http.headers().frameOptions().disable();
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
    }
    
    
    @Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
        
    }
    
}
