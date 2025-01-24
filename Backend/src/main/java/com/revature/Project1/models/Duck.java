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

    @Column(name="referenceId")
    private int referenceId;
    private Integer value;
    private String rank;
    private String nickname;

    public Duck(){
        log.info("No-args constructor for Duck");
    }

    public Duck(int referenceId, String rank, String nickname) {
        log.info("Parametrized constructor for Duck " + referenceId);
        this.referenceId = referenceId;
        this.rank = rank;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }


    public int getReference_id() {
        return referenceId;
    }

    public void setReference_id(int reference_id) {
        log.info("Setting reference_id to " + reference_id);
        this.referenceId = reference_id;
    }

    public String getRank() {
        return rank;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        log.info("Setting nickname to " + nickname);
        this.nickname = nickname;
    }
}
