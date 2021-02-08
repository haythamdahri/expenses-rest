package com.engagetech.expenses.dao;

import com.engagetech.expenses.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Haytham DAHRI
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
