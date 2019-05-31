package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByDate(Date date);
}
