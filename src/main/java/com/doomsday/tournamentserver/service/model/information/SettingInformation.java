package com.doomsday.tournamentserver.service.model.information;

import com.doomsday.tournamentserver.service.model.TournamentTimeSetting;
import com.doomsday.tournamentserver.service.model.TypeScheme;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class SettingInformation {

    private long idTournament;
    private long idSetting;
    private TypeScheme typeScheme;
    private int countPlayers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
    private LocalTime durationMatch;
    private int countPrizePlace;
    private TournamentTimeSetting tournamentTimeSetting;

    public SettingInformation(long idTournament, long idSetting, TypeScheme typeScheme, int countPlayers, LocalTime durationMatch, int countPrizePlace, TournamentTimeSetting tournamentTimeSetting) {
        this.idTournament = idTournament;
        this.idSetting = idSetting;
        this.typeScheme = typeScheme;
        this.countPlayers = countPlayers;
        this.durationMatch = durationMatch;
        this.countPrizePlace = countPrizePlace;
        this.tournamentTimeSetting = tournamentTimeSetting;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public long getIdSetting() {
        return idSetting;
    }

    public TypeScheme getTypeScheme() {
        return typeScheme;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public LocalTime getDurationMatch() {
        return durationMatch;
    }

    public int getCountPrizePlace() {
        return countPrizePlace;
    }

    public TournamentTimeSetting getTournamentTimeSetting() {
        return tournamentTimeSetting;
    }
}
