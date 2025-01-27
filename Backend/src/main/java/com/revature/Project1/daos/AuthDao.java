package com.revature.Project1.daos;

import com.revature.Project1.models.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthDao extends JpaRepository<Auth, Integer> {
}
