package com.doomsday.tournamentserver.service.model.information;

import java.util.Date;

public class PlayerViewInformation {

    private long idTournament;
    private String firstName;
    private String secondName;
    private Date birthDay;

    private int numberPlayerInTournament;

    public PlayerViewInformation(long idTournament, String firstName, String secondName, Date birthDay, int numberPlayerInTournament) {
        this.idTournament = idTournament;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDay = birthDay;
        this.numberPlayerInTournament = numberPlayerInTournament;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public int getNumberPlayerInTournament() {
        return numberPlayerInTournament;
    }
}
