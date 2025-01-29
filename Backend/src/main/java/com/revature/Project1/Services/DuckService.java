package com.revature.Project1.Services;

import com.revature.Project1.Daos.DuckDAO;
import com.revature.Project1.Exceptions.AuthorizationException;
import com.revature.Project1.Exceptions.ClientSideException;
import com.revature.Project1.Exceptions.NotFound;
import com.revature.Project1.Models.Duck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DuckService {

    private final DuckDAO duckDAO;

    @Autowired
    public DuckService(DuckDAO duckDAO) {
        this.duckDAO = duckDAO;
    }

    public Duck getDuckById(int id)  throws NotFound {
        Optional<Duck> foundDuck = duckDAO.findById(id);
        if(foundDuck.isEmpty()) throw new NotFound("Duck not found");
        return foundDuck.get();
    }

    public List<Duck> getDucksByForeignId(int referenceId){
        return duckDAO.findAllDuckByReferenceId(referenceId);
    }


    public Duck setDuckNicknameById(Duck duck) throws ClientSideException, AuthorizationException {
        Optional<Duck> resultDuck = duckDAO.findById(duck.getId());
        if(resultDuck.isEmpty()) throw new ClientSideException();
        if(duck.getNickname().trim().isEmpty()) throw new ClientSideException();
        if(duck.getReferenceId() != resultDuck.get().getReferenceId()) throw new AuthorizationException();
        resultDuck.get().setNickname(duck.getNickname());
        duckDAO.save(resultDuck.get());
        return resultDuck.get();
    }

    public Optional<Duck> deleteDuckById(Duck duck) throws ClientSideException {
        Optional<Duck> resultDuck = duckDAO.findById(duck.getId());
        if(resultDuck.isEmpty()) throw new ClientSideException();
        duckDAO.deleteById(duck.getId());
        return  resultDuck;
    }

    public Duck createDuck(Duck duck) throws ClientSideException{
        if(duck.getNickname().trim().isEmpty()) throw new ClientSideException();
        return duckDAO.save(duck);
    }

}
