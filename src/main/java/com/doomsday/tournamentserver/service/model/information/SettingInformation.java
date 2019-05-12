package com.doomsday.tournamentserver.service.model.information;

import com.doomsday.tournamentserver.service.model.TournamentTimeSetting;
import com.doomsday.tournamentserver.service.model.TypeScheme;

import java.time.LocalTime;

public class SettingInformation {

    private long idTournament;
    private long idSetting;
    private TypeScheme typeScheme;
    private int countPlayers;
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
