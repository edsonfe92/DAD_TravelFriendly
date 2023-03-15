package com.example.demo.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.security.*;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	RepositoryUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        
        //Un invitado unicamente podra buscar viajes,  pero no reservar. Las demas funcionalidades precisan de inicio de sesion 
        http.authorizeRequests().antMatchers("/Sesion").permitAll();
        http.authorizeRequests().antMatchers("/buscar").permitAll();
        http.authorizeRequests().antMatchers("/chat").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/chat/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/publicar").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/perfil").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/tusViajes").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/opinar/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/accionOpinar/*").hasAnyRole("USER");
        // form login 
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/Sesion");
        http.formLogin().failureUrl("/loginerror");
        
        //logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");


}}
