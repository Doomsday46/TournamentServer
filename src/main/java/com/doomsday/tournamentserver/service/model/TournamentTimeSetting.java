package com.doomsday.tournamentserver.service.model;

import java.time.LocalTime;
import java.util.Date;

public class TournamentTimeSetting {

    private Date beginTournament;
    private Date endTournament;
    private LocalTime beginGameDayTime;
    private LocalTime endGameDayTime;

    public TournamentTimeSetting(Date beginTournament, Date endTournament, LocalTime beginGameDayTime, LocalTime endGameDayTime) {
        this.beginTournament = beginTournament;
        this.endTournament = endTournament;
        this.beginGameDayTime = beginGameDayTime;
        this.endGameDayTime = endGameDayTime;
    }

    public Date getBeginTournament() {
        return beginTournament;
    }

    public Date getEndTournament() {
        return endTournament;
    }

    public LocalTime getBeginGameDayTime() {
        return beginGameDayTime;
    }

    public LocalTime getEndGameDayTime() {
        return endGameDayTime;
    }
}
