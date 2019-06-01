package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Entity.PrizePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface PrizePlaceRepository extends JpaRepository<PrizePlace, Long> {
    PrizePlace findByTournament_IdAndUser_IdAndNumber(long tournament_id, Long user_id, int number);
    PrizePlace findByUser_IdAndId(Long user_id, long id);
    List<PrizePlace> findAllByUser_IdAndTournament_Id(Long user_id, long tournament_id);
    List<PrizePlace> findAllByUser_IdAndTournament_IdAndTournament_Finished(Long user_id, long tournament_id, boolean tournament_finished);
}
