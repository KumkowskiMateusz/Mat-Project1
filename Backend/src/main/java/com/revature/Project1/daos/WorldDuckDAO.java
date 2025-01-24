package com.revature.Project1.daos;

import com.revature.Project1.models.WorldDuck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldDuckDAO extends JpaRepository<WorldDuck,Integer> {
}
