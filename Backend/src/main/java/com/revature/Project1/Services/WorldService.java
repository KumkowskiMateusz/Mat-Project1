package com.revature.Project1.Services;

import com.revature.Project1.Daos.WorldDAO;
import com.revature.Project1.Daos.WorldDuckDAO;
import com.revature.Project1.Exceptions.ClientSideException;
import com.revature.Project1.Models.World;
import com.revature.Project1.Models.WorldDuck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WorldService {

    private final WorldDAO worldDAO;
    private final WorldDuckDAO worldDuckDAO;

    @Autowired
    public WorldService(WorldDAO worldDAO, WorldDuckDAO worldDuckDAO){
        this.worldDAO = worldDAO;
        this.worldDuckDAO = worldDuckDAO;
    }

    public World createWorld(World world){
        return worldDAO.save(world);
    }

    public World getWorldValuesById(int id) throws  ClientSideException{
        Optional<World> resultWorld = worldDAO.findById(id);
        if(resultWorld.isEmpty()) throw new ClientSideException();
        return resultWorld.get();
    }

    public WorldDuck getDuckRank(Set<WorldDuck> worldDucks){
        Integer denominator = 0;
        Integer rank;
        Integer summedValues = 0;
        List<WorldDuck> listDucks = worldDucks.stream().sorted().toList();
        for(WorldDuck i : listDucks){
            denominator += i.getChance();
        }
        rank = (int)(Math.random() * denominator);
        for(WorldDuck i : listDucks){
            summedValues += i.getChance();
            if(summedValues >= rank){
                i.setAmount(i.getAmount() - 1);
                worldDuckDAO.save(i);
                return i;
            }
        }
        return null;
    }

    public World setWorldValues(World world) throws ClientSideException {

        if(worldDAO.findById(world.getId()).isEmpty()) throw new ClientSideException();
        World innerWorld = worldDAO.findById(world.getId()).get();


        worldDAO.save(innerWorld);
        return innerWorld;
    }
}
