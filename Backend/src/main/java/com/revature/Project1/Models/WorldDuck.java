package com.revature.Project1.Models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "world_ducks")
public class WorldDuck {
    private static final Logger log = LogManager.getLogger(WorldDuck.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer worldId;
    private String rank;
    private Integer price;
    private Integer chance;
    private Integer amount;

    public WorldDuck(Integer worldId, String rank, Integer value, Integer chance, Integer price) {
        log.trace("Parametrized constructor for WorldDuck");
        this.worldId = worldId;
        this.rank = rank;
        this.price = value;
        this.chance = chance;
        this.amount = 0;
    }

    public WorldDuck() {
        log.trace("No-args constructor for WorldDuck");
        this.worldId = 0;
        this.rank = "Default";
        this.price = 0;
        this.chance = 0;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        log.trace("Setting chance to " + chance);
        this.chance = chance;
    }

    public Integer getValue() {
        return price;
    }

    public void setValue(Integer value) {
        log.trace("Setting value to " + value);
        this.price = value;
    }

    public Integer getWorldId() {
        return worldId;
    }

    public void setWorldId(Integer worldId) {
        log.trace("Setting worldId to " + worldId);
        this.worldId = worldId;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        log.trace("Setting rank to " + rank);
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }
}
