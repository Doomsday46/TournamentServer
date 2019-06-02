package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByDate(Date date);
    List<Game> findAllByTournament_IdAndTournament_User_Id(long tournament_id, Long tournament_user_id);
    List<Game> findAllByTournament_IdAndTournament_User_IdAndState(long tournament_id, Long tournament_user_id, boolean state);
}
