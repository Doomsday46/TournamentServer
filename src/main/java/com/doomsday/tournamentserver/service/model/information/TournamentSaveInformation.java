package com.doomsday.tournamentserver.service.model.information;

public class TournamentSaveInformation {

    private long idTournament;
    private String nameTournament;
    private String information;

    public TournamentSaveInformation(long idTournament, String nameTournament, String information) {
        this.idTournament = idTournament;
        this.nameTournament = nameTournament;
        this.information = information;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public String getInformation() {
        return information;
    }
}
