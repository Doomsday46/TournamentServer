package com.doomsday.tournamentserver.db.repository;


import com.doomsday.tournamentserver.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
