package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
