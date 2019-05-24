package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament findByName(String name);
    Tournament findByIdAndUser_Id(long id, Long user_id);
    boolean existsByName(String name);
    List<Tournament> findAllByUser_Id(Long user_id);
}
