package com.doomsday.tournamentserver.service.model.information;

import java.util.Date;
import java.util.List;

public class MatchInformation {

    private long idTournament;
    private long idMatch;
    private List<PlayerViewInformation> players;
    private boolean isFinished;
    private Date date;
    private LocationViewInformation locationViewInformation;
    private int numberFirstSide;
    private int numberSecondSide;
    private PlayerViewInformation winner;

    public MatchInformation(long idTournament, long idMatch, List<PlayerViewInformation> players, boolean isFinished, Date date, LocationViewInformation locationViewInformation, int numberFirstSide, int numberSecondSide, PlayerViewInformation winner) {
        this.idTournament = idTournament;
        this.idMatch = idMatch;
        this.players = players;
        this.isFinished = isFinished;
        this.date = date;
        this.locationViewInformation = locationViewInformation;
        this.numberFirstSide = numberFirstSide;
        this.numberSecondSide = numberSecondSide;
        this.winner = winner;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public long getIdMatch() {
        return idMatch;
    }

    public List<PlayerViewInformation> getPlayers() {
        return players;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Date getDate() {
        return date;
    }

    public LocationViewInformation getLocationViewInformation() {
        return locationViewInformation;
    }

    public int getNumberFirstSide() {
        return numberFirstSide;
    }

    public int getNumberSecondSide() {
        return numberSecondSide;
    }

    public PlayerViewInformation getWinner() {
        return winner;
    }
}
