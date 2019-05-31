package com.doomsday.tournamentserver.domain.builder;

import com.doomsday.tournamentserver.domain.tournament.Tournament;

public interface TournamentBuilder {

    TournamentBuilder setPlayers();
    TournamentBuilder setLocations();
    TournamentBuilder setSetting();
    TournamentBuilder generateSchedule();
    TournamentBuilder generateScheme();
    Tournament build();

}
