package com.revature.Project1.Components;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        CsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;

        return http.build();
    }
}