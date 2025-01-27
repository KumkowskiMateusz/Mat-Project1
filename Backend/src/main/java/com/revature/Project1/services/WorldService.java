package com.revature.Project1.services;

import com.revature.Project1.daos.WorldDAO;
import com.revature.Project1.exceptions.ClientSideException;
import com.revature.Project1.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class WorldService {

    private final WorldDAO worldDAO;

    @Autowired
    public WorldService(WorldDAO worldDAO) {
        this.worldDAO = worldDAO;
    }

    public World createWorld(World world){
        return worldDAO.save(world);
    }

    public World getWorldValuesById(int id) throws  ClientSideException{
        Optional<World> resultWorld = worldDAO.findById(id);
        if(resultWorld.isEmpty()) throw new ClientSideException();
        return resultWorld.get();
    }

    public World setWorldValues(World world) throws ClientSideException {

        if(worldDAO.findById(world.getId()).isEmpty()) throw new ClientSideException();
        World innerWorld = worldDAO.findById(world.getId()).get();


        worldDAO.save(innerWorld);
        return innerWorld;
    }
}
