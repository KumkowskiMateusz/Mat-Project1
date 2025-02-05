package com.revature.Project1.Controllers;

import com.revature.Project1.Components.Encoder;
import com.revature.Project1.Components.FileLogger;
import com.revature.Project1.Components.JwtUtil;
import com.revature.Project1.Dtos.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.revature.Project1.Exceptions.ConflictException;
import com.revature.Project1.Exceptions.PasswordException;
import com.revature.Project1.Exceptions.UsernameException;
import com.revature.Project1.Models.User;
import com.revature.Project1.Services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge=3600)
@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;
    private final Encoder encoder;
    private final Logger log;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService , Encoder encoder, FileLogger log, JwtUtil jwtUtil){
        this.userService = userService;
        this.encoder = encoder;
        this.log = log.log;
        this.jwtUtil = jwtUtil;
    }


    @PreAuthorize("permitAll()")
    @GetMapping()
    public ResponseEntity<?> getAuth(@RequestBody UserDetails userDetails, @CookieValue(value = "JWTToken", defaultValue = "") String auth) {
        if (jwtUtil.validateToken(auth, userDetails)) {
            return ResponseEntity.status(HttpStatus.OK).body("Valid");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("csrf")
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest servlet){
        return ResponseEntity.status(HttpStatus.OK).body((CsrfToken)servlet.getAttribute("_csrf"));
    }

    @PreAuthorize("permitAll()")
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

    @PreAuthorize("permitAll()")
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
                    String tokenGenerated = jwtUtil.generateToken(userDTO.getUsername());
                    Cookie cookie = new Cookie("JWTToken", tokenGenerated);
                    servlet.addCookie(cookie);
                    log.trace("User Logged In");
                    return ResponseEntity.status(HttpStatus.OK).body(tokenGenerated);
                } else{
                    log.error("Invalid Username or Password");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password");
                }
            }catch(Exception e){
                log.error("Error");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
            }
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "logout")
    public ResponseEntity<?> logout(HttpServletResponse servlet){
        log.trace("User Logged Out");
        return ResponseEntity.status(HttpStatus.OK).body("Logged Out");
    }


    }

