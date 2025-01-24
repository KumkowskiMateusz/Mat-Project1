package com.revature.Project1.models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private static final Logger log = LogManager.getLogger(User.class);
    /*
     * id (Primary Key)
     * username
     * password
     * bankAccount
     * backpackSpace
     * admin
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserTypes userType;

    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Duck> ducks;

    private String loginToken;
    private String refreshToken;

    public User(String password, String username, UserTypes userType) {
        log.trace("Parametrized constructor for User " + username);
        this.password = password;
        this.username = username;
        this.userType = userType;
    }

    public User(){
        log.trace("No-args constructor for User");
        this.username = "Default";
        this.password = "Default";
        this.userType = UserTypes.USER;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        log.trace("Setting username to " + username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        log.info("Set userType to " + userType);
        this.userType = userType;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        log.trace("Setting inventory");
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public Set<Duck> getDucks() {
        return ducks;
    }

    public void setDucks(Set<Duck> ducks) {
        log.trace("Setting ducks");
        this.ducks = ducks;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        log.trace("Setting loginToken");
        this.loginToken = loginToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        log.trace("Setting refreshToken");
        this.refreshToken = refreshToken;
    }
}
