package com.revature.Project1.controllers;

import java.util.Optional;

import com.revature.Project1.Components.Encoder;
import com.revature.Project1.Components.FileLogger;
import com.revature.Project1.Dtos.UserDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.revature.Project1.exceptions.ConflictException;
import com.revature.Project1.exceptions.PasswordException;
import com.revature.Project1.exceptions.UsernameException;
import com.revature.Project1.models.User;
import com.revature.Project1.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge=3600)
@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;
    private final Encoder encoder;
    private final Logger log;

    @Autowired
    public AuthController(UserService userService , Encoder encoder, FileLogger log){
        this.userService = userService;
        this.encoder = encoder;
        this.log = log.log;
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> registerAccount(@RequestBody User user){
        try{
            User resultUser = userService.createUser(user);
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(resultUser.getId());
            userDTO.setUsername(resultUser.getUsername());
            userDTO.setRole(resultUser.getUserType().toString());
            log.trace("User Created");
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }catch(ConflictException e){
            log.error("Username Taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username Taken");
        }catch(PasswordException e){
            log.error("Password Too Short");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Too Short");
        }catch(UsernameException e){
            log.error("Username Invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username Invalid");
        }catch(Exception e){
            log.error("Unknown Error");
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body("Error");
        }
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> loginAccount(@RequestBody User user,HttpServletResponse servlet){

            try{
                User resultUser = userService.getUserByUsername(user);
                if(encoder.passwordEncoder.matches(user.getPassword(), resultUser.getPassword())){
                    /*
                         On Success a cookie is created with the hashed user's id as the value
                         Returns the user's id, username, and role
                     */
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserId(resultUser.getId());
                    userDTO.setUsername(resultUser.getUsername());
                    userDTO.setRole(resultUser.getUserType().toString());

                    Cookie cookie = new Cookie("project1LoginCookie", encoder.passwordEncoder.encode(Integer.valueOf(user.getId()).toString()));
                    cookie.setMaxAge(100000);
                    servlet.addCookie(cookie);

                    log.trace("User Logged In");
                    return ResponseEntity.status(HttpStatus.OK).body(userDTO);
                } else{
                    log.error("Invalid Username or Password");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password");
                }
            }catch(Exception e){
                log.error("Error");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
            }
    }

    @PostMapping(value = "logout")
    public ResponseEntity<?> logout(HttpServletResponse servlet){
        Cookie pageCookie = new Cookie("pageCookie", null);
        pageCookie.setMaxAge(-100000);
        servlet.addCookie(pageCookie);
        Cookie cookie = new Cookie("project1LoginCookie", null);
        cookie.setMaxAge(-100000);
        servlet.addCookie(cookie);
        log.trace("User Logged Out");
        return ResponseEntity.status(HttpStatus.OK).body("Logged Out");
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("loginToken")
    public ResponseEntity<?> checkLoginToken(@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie,
                                            @AuthenticationPrincipal UserDetails userDetails){
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cookie Found");

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user.getLoginToken() != cookie) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("refreshToken")
    public ResponseEntity checkPageToken(@CookieValue(value = "pageCookie", defaultValue = "none") String cookie,
                                         @AuthenticationPrincipal UserDetails userDetails){
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cookie Found");

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user.getRefreshToken() != cookie) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");

        return ResponseEntity.ok().build();
        }

    @PutMapping("loginToken")
    public ResponseEntity<?> setLoginToken(@RequestBody User user){
        try{
            String token = userService.SetLoginToken(user.getId());
            log.trace("Login Token Set");
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch(Exception e){
            log.error("Error");
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body("Error");
        }
    }

    @PutMapping("refreshToken")
    public ResponseEntity<?> setRefreshToken(@RequestBody User user) {
        try {
            String token = userService.SetRefreshToken(user.getId());
            log.trace("Refresh Token Set");
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception e) {
            log.error("Error");
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body("Error");
        }
    }
    }

