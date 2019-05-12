package com.doomsday.tournamentserver.service.model.view;

import com.doomsday.tournamentserver.service.model.TournamentTimeSetting;

public class SettingView {

    private long idTournament;
    private String typeScheme;
    private int countPlayers;
    private MatchTimeSettingView mathTimeSetting;
    private int countPrizePlace;
    private TournamentTimeSetting tournamentTimeSetting;

    public SettingView(long idTournament, String typeScheme, int countPlayers, MatchTimeSettingView mathTimeSetting, int countPrizePlace, TournamentTimeSetting tournamentTimeSetting) {
        this.idTournament = idTournament;
        this.typeScheme = typeScheme;
        this.countPlayers = countPlayers;
        this.mathTimeSetting = mathTimeSetting;
        this.countPrizePlace = countPrizePlace;
        this.tournamentTimeSetting = tournamentTimeSetting;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public String getTypeScheme() {
        return typeScheme;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public MatchTimeSettingView getMathTimeSetting() {
        return mathTimeSetting;
    }

    public int getCountPrizePlace() {
        return countPrizePlace;
    }

    public TournamentTimeSetting getTournamentTimeSetting() {
        return tournamentTimeSetting;
    }
}
