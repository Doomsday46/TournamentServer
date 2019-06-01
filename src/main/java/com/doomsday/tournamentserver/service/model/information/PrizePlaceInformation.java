package com.doomsday.tournamentserver.service.model.information;

public class PrizePlaceInformation {

    private long id;
    private long idTournament;
    private int number;
    private PlayerViewInformation playerViewInformation;

    public PrizePlaceInformation(long id, long idTournament, int number, PlayerViewInformation playerViewInformation) {
        this.id = id;
        this.idTournament = idTournament;
        this.number = number;
        this.playerViewInformation = playerViewInformation;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public PlayerViewInformation getPlayerViewInformation() {
        return playerViewInformation;
    }
}
