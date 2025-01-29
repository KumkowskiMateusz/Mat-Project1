package com.revature.Project1.Dtos;

import com.revature.Project1.Daos.UserDAO;
import com.revature.Project1.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public MyUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findUserByUsername(username);
        if(user.isEmpty()) throw new UsernameNotFoundException("User not found");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(user.get().getUserType().toString())
                .build();

        return userDetails;
    }
}
