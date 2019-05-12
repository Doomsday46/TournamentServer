package com.doomsday.tournamentserver.service.model.view;

import java.util.List;

public class TournamentView {

    private String nameTournament;
    private SettingView settingView;
    private List<Integer> playersId;
    private List<Integer> locationsId;

    public TournamentView(String nameTournament, SettingView settingView, List<Integer> playersId, List<Integer> locationsId) {
        this.nameTournament = nameTournament;
        this.settingView = settingView;
        this.playersId = playersId;
        this.locationsId = locationsId;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public SettingView getSettingView() {
        return settingView;
    }

    public List<Integer> getPlayersId() {
        return playersId;
    }

    public List<Integer> getLocationsId() {
        return locationsId;
    }
}
