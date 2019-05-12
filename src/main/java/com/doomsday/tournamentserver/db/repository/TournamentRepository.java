package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament findByName(String name);
}
