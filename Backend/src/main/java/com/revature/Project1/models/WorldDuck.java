package com.revature.Project1.models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "worldDucks")
public class WorldDuck {
    private static final Logger log = LogManager.getLogger(WorldDuck.class);
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer worldId;
    private String rank;
    private Integer value;

    public WorldDuck(Integer worldId, String rank, Integer value) {
        log.trace("Parametrized constructor for WorldDuck");
        this.worldId = worldId;
        this.rank = rank;
        this.value = value;
    }

    public WorldDuck() {
        log.trace("No-args constructor for WorldDuck");
        this.worldId = 0;
        this.rank = "Default";
        this.value = 0;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        log.trace("Setting value to " + value);
        this.value = value;
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
