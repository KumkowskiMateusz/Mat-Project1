package com.revature.Project1.daos;

import com.revature.Project1.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDAO extends JpaRepository<Inventory,Integer> {
}
