package com.revature.Project1.Models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "worlds")
public class World {

    private static final Logger log = LogManager.getLogger(World.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer seachingFee;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<WorldDuck> worldInfo;

    @NonNull
    private String worldName;

    public World(@NonNull String worldName, Integer seachingFee) {
        log.trace("Parametrized constructor for World " + worldName);
        this.worldName = worldName;
        this.seachingFee = seachingFee;
    }

    public World() {
        log.trace("No-args constructor for World");
        this.seachingFee = 0;
        this.worldName = "Default";
        this.worldInfo =  new HashSet<WorldDuck>();
    }

    public Set<WorldDuck> getWorldInfo() {
        return worldInfo;
    }

    public void setWorldInfo(Set<WorldDuck> worldInfo) {
        log.trace("Setting worldInfo to " + worldInfo);
        this.worldInfo = worldInfo;
    }

    public Integer getSeachingFee() {
        return seachingFee;
    }

    public void setSeachingFee(Integer seachingFee) {
        log.trace("Setting seachingFee to " + seachingFee);
        this.seachingFee = seachingFee;
    }

    @NonNull
    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(@NonNull String worldName) {
        log.trace("Setting worldName to " + worldName);
        this.worldName = worldName;
    }

    public int getId() {
        return id;
    }
}
