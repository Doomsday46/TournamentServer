package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
