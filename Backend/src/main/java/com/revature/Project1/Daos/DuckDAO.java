package com.revature.Project1.Daos;

import com.revature.Project1.Models.Duck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuckDAO extends JpaRepository<Duck,Integer> {
    public List<Duck> findAllDuckByReferenceId(int ReferenceId);
}
