package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findAllByAge(Date age);
    Player findAllByFirstName(String firstName);
    Player findByNumber(int number);
    Player findByIdAndUser_IdAndTournament_Id(long id, Long user_id, long tournament_id);
    Player findByIdAndUser_Id(long id, Long user_id);
    List<Player> findAllByUser_IdAndTournament_Id(Long user_id, long tournament_id);
    List<Player> findAllByUser_IdAndTournament_IdAndFirstNameAndSurname(Long user_id, long tournament_id, String firstName, String surname);
    List<Player> findAllByUser_IdAndTournament_IdAndFirstName(Long user_id, long tournament_id, String firstName);
    List<Player> findAllByUser_IdAndTournament_IdAndAge(Long user_id, long tournament_id, Date age);
    List<Player> findAllByUser_IdAndTournament_IdAndAgeBefore(Long user_id, long tournament_id, Date age);
}
