package com.doomsday.tournamentserver.database.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "setting")
public class Setting {

    private long id;

    private String schemeType;
    private int countPlayers;
    private int countPrizePlace;
    private Date startTournamentDate;

    private User user;

    public Setting() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public void setCountPlayers(int countPlayers) {
        this.countPlayers = countPlayers;
    }

    public int getCountPrizePlace() {
        return countPrizePlace;
    }

    public void setCountPrizePlace(int countPrizePlace) {
        this.countPrizePlace = countPrizePlace;
    }

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    public Date getStartTournamentDate() {
        return startTournamentDate;
    }

    public void setStartTournamentDate(Date startTournamentDate) {
        this.startTournamentDate = startTournamentDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
