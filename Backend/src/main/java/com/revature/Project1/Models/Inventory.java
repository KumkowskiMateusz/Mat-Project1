package com.revature.Project1.Models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "inventories")
public class Inventory {

    private static final Logger log = LogManager.getLogger(Inventory.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer backpackSpace;
    private Integer backpackSpaceUsed;

    public Integer getBackpackSpaceUsed() {
        return backpackSpaceUsed;
    }

    public void setBackpackSpaceUsed(Integer backpackSpaceUsed) {
        this.backpackSpaceUsed = backpackSpaceUsed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Double bankAccount;

    private Integer potionV1;

    private Integer potionV2;

    private Integer potionV3;

    public Inventory() {
        log.trace("No-args constructor for Inventory");
        this.backpackSpace = 6;
        this.potionV1 = 0;
        this.potionV2 = 0;
        this.potionV3 = 0;
        this.bankAccount = 0.0;
    }

    public Integer getBackpackSpace() {
        return backpackSpace;
    }

    public void setBackpackSpace(Integer backpackSpace) {
        log.trace("Setting backpackSpace to " + backpackSpace);
        this.backpackSpace = backpackSpace;
    }

    public Double getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Double bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getPotionV1() {
        return potionV1;
    }

    public void setPotionV1(Integer potionV1) {
        log.trace("Setting potionV1 to " + potionV1);
        this.potionV1 = potionV1;
    }

    public Integer getPotionV2() {
        return potionV2;
    }

    public void setPotionV2(Integer potionV2) {
        log.trace("Setting potionV2 to " + potionV2);
        this.potionV2 = potionV2;
    }

    public Integer getPotionV3() {
        return potionV3;
    }

    public void setPotionV3(Integer potionV3) {
        log.trace("Setting potionV3 to " + potionV3);
        this.potionV3 = potionV3;
    }

    public Integer getId() {
        return id;
    }
}
