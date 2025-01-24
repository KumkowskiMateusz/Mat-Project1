package com.revature.Project1.models;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "worlds")
public class World {

    private static final Logger log = LogManager.getLogger(World.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer seachingFee;

    private Set<Duck> worldInfo;

    @NonNull
    private String worldName;

    public World(@NonNull String worldName, Integer seachingFee) {
        log.info("Parametrized constructor for World " + worldName);
        this.worldName = worldName;
        this.seachingFee = seachingFee;
    }

    public World() {
        log.info("No-args constructor for World");
        this.seachingFee = 0;
        this.worldName = "Default";
        this.worldInfo =  new HashSet<Duck>();
    }

    public Set<Duck> getWorldInfo() {
        return worldInfo;
    }

    public void setWorldInfo(Set<Duck> worldInfo) {
        log.info("Setting worldInfo to " + worldInfo);
        this.worldInfo = worldInfo;
    }

    public Integer getSeachingFee() {
        return seachingFee;
    }

    public void setSeachingFee(Integer seachingFee) {
        log.info("Setting seachingFee to " + seachingFee);
        this.seachingFee = seachingFee;
    }

    @NonNull
    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(@NonNull String worldName) {
        log.info("Setting worldName to " + worldName);
        this.worldName = worldName;
    }

    public int getId() {
        return id;
    }
}
