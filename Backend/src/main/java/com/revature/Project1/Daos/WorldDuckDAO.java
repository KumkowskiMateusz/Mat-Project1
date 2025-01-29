package com.revature.Project1.Daos;

import com.revature.Project1.Models.WorldDuck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldDuckDAO extends JpaRepository<WorldDuck,Integer> {
}
