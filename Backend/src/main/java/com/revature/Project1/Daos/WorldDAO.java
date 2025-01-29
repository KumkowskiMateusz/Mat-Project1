package com.revature.Project1.Daos;

import com.revature.Project1.Models.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldDAO extends JpaRepository<World,Integer> {
}
