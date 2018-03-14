package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.example.demo.service.impl.CustomUserService;

/**
 * Configuration to secure application using spring security
 * 
 * @author Sardesh Sharma
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	protected static final String CUSTOM_REALM = "Spring Security Realm";
	
	@Autowired
	CustomUserService customUserDetailService;
	
	/**
	 * configures authentication manager to add custom user support
	 * 
	 * @param authManagerBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder authManagerBuilder) throws Exception{
		authManagerBuilder.inMemoryAuthentication().withUser("Joe").password("password").authorities("USER");
		authManagerBuilder.userDetailsService(customUserDetailService);
						
								
	}
	
	/**
	 * secures url by applying security specific to matched urls
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/users/**").permitAll()
			.antMatchers("/contacts/**").hasAnyAuthority("USER")
			.anyRequest().authenticated()
			.and()
			.httpBasic().realmName(CUSTOM_REALM)
			.authenticationEntryPoint(getAuthenticationEntryPoint())
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}

	/**
	 * helper method
	 * @return
	 */
	private CustomBasicAuthenticationEntryPoint getAuthenticationEntryPoint() {
		
		return new CustomBasicAuthenticationEntryPoint();
	}

	/**
	 * ignores OPTION request sent by browser
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}

	
}
