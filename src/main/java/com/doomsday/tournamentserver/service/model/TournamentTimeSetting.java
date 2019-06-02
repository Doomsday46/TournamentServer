package com.doomsday.tournamentserver.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalTime;
import java.util.Date;

public class TournamentTimeSetting {

    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date beginTournament;
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date endTournament;
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime beginGameDayTime;
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endGameDayTime;

    public TournamentTimeSetting() {
    }

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
