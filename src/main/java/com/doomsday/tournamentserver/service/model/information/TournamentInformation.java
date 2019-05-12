package com.doomsday.tournamentserver.service.model.information;

import java.util.List;

public class TournamentInformation {

    private long idTournament;
    private String nameTournament;
    private SettingInformation setting;
    private List<PlayerViewInformation> players;
    private List<LocationViewInformation> locations;

    public TournamentInformation(long idTournament, String nameTournament, SettingInformation setting, List<PlayerViewInformation> players, List<LocationViewInformation> locations) {
        this.idTournament = idTournament;
        this.nameTournament = nameTournament;
        this.setting = setting;
        this.players = players;
        this.locations = locations;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public SettingInformation getSetting() {
        return setting;
    }

    public List<PlayerViewInformation> getPlayers() {
        return players;
    }

    public List<LocationViewInformation> getLocations() {
        return locations;
    }
}
