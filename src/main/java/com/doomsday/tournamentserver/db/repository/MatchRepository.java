package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface MatchRepository extends JpaRepository<Match, Long> {
    Match findByDate(Date date);
}
