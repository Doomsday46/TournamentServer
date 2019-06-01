package com.doomsday.tournamentserver.db.repository;


import com.doomsday.tournamentserver.db.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
