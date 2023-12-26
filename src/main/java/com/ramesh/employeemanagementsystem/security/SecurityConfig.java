package com.ramesh.employeemanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder encoder) {
        UserDetails admin = User.withUsername("ramesh")
                .password(encoder.encode("ramesh123"))
                .roles("admin")
                .build();

        UserDetails user = User.withUsername("suresh")
                .password(encoder.encode("suresh123"))
                .roles("user")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeHttpRequests().requestMatchers("/ping").permitAll()
                .and().authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and().formLogin().and().build();
    }
}
