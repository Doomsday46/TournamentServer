package com.doomsday.tournamentserver.service.model.view;

public class PrizePlaceView {

    private long idTournament;
    private long idPlayer;
    private int prizePlaceNumber;

    public PrizePlaceView(long idTournament, long idPlayer, int prizePlaceNumber) {
        this.idTournament = idTournament;
        this.idPlayer = idPlayer;
        this.prizePlaceNumber = prizePlaceNumber;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public long getIdPlayer() {
        return idPlayer;
    }

    public int getPrizePlaceNumber() {
        return prizePlaceNumber;
    }
}
