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
    private Integer referenceId;
    private String rank;
    private String nickname;
    private Integer chance;

    public Duck(){
        log.trace("No-args constructor for Duck");
    }

    public Duck(int referenceId, String rank, String nickname, int value, int chance) {
        log.trace("Parametrized constructor for Duck " + referenceId);
        this.rank = rank;
        this.nickname = nickname;
        this.referenceId = referenceId;
        this.value = value;
        this.chance = chance;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
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

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
