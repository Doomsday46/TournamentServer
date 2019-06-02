package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.Entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament findByName(String name);
    Tournament findByIdAndUser_Id(long id, Long user_id);
    boolean existsByName(String name);
    List<Tournament> findAllByUser_Id(Long user_id);
}
