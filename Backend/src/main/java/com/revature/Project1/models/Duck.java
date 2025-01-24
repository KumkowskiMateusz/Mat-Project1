package com.revature.Project1.models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "ducks")
public class Duck {
    private static final Logger log = LogManager.getLogger(Duck.class);
    /*
        id,
        reference_id
        rank
        nickname
         */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer value;
    private String rank;
    private String nickname;

    public Duck(){
        log.trace("No-args constructor for Duck");
    }

    public Duck(int referenceId, String rank, String nickname) {
        log.trace("Parametrized constructor for Duck " + referenceId);
        this.rank = rank;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        log.trace("Setting nickname to " + nickname);
        this.nickname = nickname;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        log.trace("Setting value to " + value);
        this.value = value;
    }
}
