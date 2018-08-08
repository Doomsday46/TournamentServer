package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.scheme.SchemeType;

import java.time.LocalDateTime;

public interface TournamentInfo {

    SchemeType getScheme();
    String getTournamentName();
    Integer getPrizePlacesCount();
    LocalDateTime getStartDate();
    Integer getCountPlayers();

}
