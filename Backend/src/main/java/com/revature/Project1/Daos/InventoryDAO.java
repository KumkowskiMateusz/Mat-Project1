package com.revature.Project1.Daos;

import com.revature.Project1.Models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDAO extends JpaRepository<Inventory,Integer> {
}
