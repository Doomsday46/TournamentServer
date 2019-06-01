package com.doomsday.tournamentserver.service.model.information;

import java.util.List;

public class TournamentInformation {

    private long idTournament;
    private String nameTournament;
    private SettingInformation setting;
    private boolean isFinished;
    private boolean isStarted;
    private List<PlayerViewInformation> players;
    private List<LocationViewInformation> locations;
    private List<MatchInformation>  matches;
    private List<PrizePlaceInformation>  prizePlaces;

    public TournamentInformation(long idTournament, String nameTournament, SettingInformation setting, boolean isFinished, List<PlayerViewInformation> players, List<LocationViewInformation> locations, boolean started, boolean isStarted, List<MatchInformation> matches, List<PrizePlaceInformation> prizePlaces) {
        this.idTournament = idTournament;
        this.nameTournament = nameTournament;
        this.setting = setting;
        this.isFinished = isFinished;
        this.players = players;
        this.locations = locations;
        this.isStarted = isStarted;
        this.matches = matches;
        this.prizePlaces = prizePlaces;
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

    public boolean isFinished() {
        return isFinished;
    }

    public List<MatchInformation> getMatches() {
        return matches;
    }

    public List<PrizePlaceInformation> getPrizePlaces() {
        return prizePlaces;
    }

    public void setIdTournament(long idTournament) {
        this.idTournament = idTournament;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
