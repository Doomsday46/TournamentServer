package com.doomsday.tournamentserver.service.model.view;

import java.util.Date;

public class PlayerView {

    private long idTournament;
    private String firstName;
    private String secondName;
    private Date birthDay;

    public PlayerView(long idTournament, String firstName, String secondName, Date birthDay) {
        this.idTournament = idTournament;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDay = birthDay;
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
}
