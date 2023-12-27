package com.ramesh.employeemanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramesh.employeemanagementsystem.filter.AuthEntryPointJwt;
import com.ramesh.employeemanagementsystem.filter.JwtAuthFilter;
import com.ramesh.employeemanagementsystem.repository.UserRepository;
import com.ramesh.employeemanagementsystem.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

   /* @Autowired
    private UserDetailsService userDetailsService;*/

    @Autowired
    private JwtAuthFilter authFilter;

 /*   @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder encoder) {
        UserDetails admin = User.withUsername("ramesh")
                .password(encoder.encode("ramesh123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("suresh")
                .password(encoder.encode("suresh123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }*/

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        System.out.println("inside authentication provider");
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//
//        return daoAuthenticationProvider;
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new AuthEntryPointJwt(new ObjectMapper()))
                .and().authorizeHttpRequests().requestMatchers("/ping", "/auth/**").permitAll()
                .and().authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
