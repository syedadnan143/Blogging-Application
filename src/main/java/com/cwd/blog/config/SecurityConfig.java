package com.cwd.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cwd.blog.Security.CustomUserDetailService;
import com.cwd.blog.Security.JwtAuthenticationEntryPoint;
import com.cwd.blog.Security.JwtAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity (prePostEnabled = true)


public class SecurityConfig{
	
	public static final String[] PUBLIC_URLS = {"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**sss"};
	@Bean
    public PasswordEncoder passwordEncoder() {
	// TODO Auto-generated method stub
	return new BCryptPasswordEncoder();
} 
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
  private CustomUserDetailService customUserDetailService;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		http.csrf()
		.disable()
		.authorizeHttpRequests()
		.requestMatchers(PUBLIC_URLS).permitAll()
//		.requestMatchers(HttpMethod.POST).permitAll()
	   // .requestMatchers(HttpMethod.OPTIONS).permitAll()
		.requestMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
		
		http.authenticationProvider(daoAuthenticationProvider());
		return defaultSecurityFilterChain;
	}
    @Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
    	}

      
	@Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
	  return configuration.getAuthenticationManager();
  }
    
}
