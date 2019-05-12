package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findAllByAge(Date age);
    Player findAllByFirstName(String firstName);
    Player findByNumber(int number);
}
