package com.revature.Project1.Components;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
    public PasswordEncoder passwordEncoder;


    public Encoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    };
}

