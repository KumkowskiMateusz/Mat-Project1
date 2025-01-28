package com.revature.Project1.services;

import com.revature.Project1.Components.Encoder;
import com.revature.Project1.Components.FileLogger;
import com.revature.Project1.daos.InventoryDAO;
import com.revature.Project1.daos.UserDAO;
import com.revature.Project1.exceptions.*;
import com.revature.Project1.models.Inventory;
import com.revature.Project1.models.User;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final Encoder encoder;
    private final Logger log;
    private final InventoryDAO inventoryDAO;

    @Autowired
    public UserService(UserDAO userDAO, Encoder encoder, FileLogger log, InventoryDAO inventoryDAO){
        this.userDAO = userDAO;
        this.encoder = encoder;
        this.log = log.log;
        this.inventoryDAO = inventoryDAO;
    }

    public User getUserById(Integer id) throws NotFound {
        Optional<User> foundUser = userDAO.findById(id);
        if(foundUser.isEmpty()) throw new NotFound("User not found");
        return foundUser.get();
    }

    public User getUserByUsername(User user){
        Optional<User> foundUser = userDAO.findUserByUsername(user.getUsername());
        if(foundUser.isEmpty()) throw new NotFound("User not found");
        return foundUser.get();
    }

    public User getUserByUsername(String user){
        Optional<User> foundUser = userDAO.findUserByUsername(user);
        if(foundUser.isEmpty()) throw new NotFound("User not found");
        return foundUser.get();
    }

//    public String SetRefreshToken(Integer id){
//        log.trace("Setting refresh token for user with id " + id);
//        Optional<User> foundUser = userDAO.findById(id);
//        if(foundUser.isEmpty()) throw new NotFound("User not found");
//        User user = foundUser.get();
//
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[64];
//        random.nextBytes(bytes);
//        user.setRefreshToken(bytes.toString());
//
//        userDAO.save(user);
//        return bytes.toString();
//    }
//
//    public String SetLoginToken(Integer id){
//        log.trace("Setting login token for user with id " + id);
//        Optional<User> foundUser = userDAO.findById(id);
//        if(foundUser.isEmpty()) throw new NotFound("User not found");
//        User user = foundUser.get();
//
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[64];
//        random.nextBytes(bytes);
//
//        userDAO.save(user);
//        return bytes.toString();
//    }

    @Transactional
    public User createUser(User user) throws PasswordException, UsernameException, ConflictException {
        log.trace("Creating user with username " + user.getUsername());
        if(user.getUsername().trim().isEmpty()) throw new UsernameException();
        /*
            * Password must contain at least one digit [0-9]
            * Password must contain at least one lowercase/uppercase Latin character [a-z]
            * Password must contain at least one special character like ! @ # & ( )
         */
        String regex = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_]).{8,}$";
        if(!user.getPassword().matches(regex)) throw new PasswordException();
        if(userDAO.findUserByUsername(user.getUsername()).isPresent()) throw new ConflictException();
        user.setPassword(encoder.passwordEncoder.encode(user.getPassword()));
        Inventory newInventory = new Inventory();
        user.setInventory(newInventory);
        inventoryDAO.save(newInventory);
        return userDAO.save(user);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User setUserBankAccount(User user, Double amount) throws NotFound{
        Optional<User> userObtained = userDAO.findById(user.getId());
        if(userObtained.isEmpty()) throw new NotFound("User not found");
        User returnUser = userObtained.get();
        returnUser.getInventory().setBankAccount((double)(returnUser.getInventory().getBankAccount() + amount));

        inventoryDAO.save(returnUser.getInventory());
        userDAO.save(returnUser);

        return returnUser;
    }

    public User setUserBackpackAmount(User user) throws NotFound{
        log.trace("Setting backpack amount for user with id " + user.getId());
        Optional<User> userObtained = userDAO.findById(user.getId());
        if(userObtained.isEmpty()) throw new NotFound("User not found");
        User returnUser = userObtained.get();
        returnUser.getInventory().setBackpackSpace((returnUser.getInventory().getBackpackSpace() + 1));

        inventoryDAO.save(returnUser.getInventory());
        userDAO.save(returnUser);
        return returnUser;
    }

}
