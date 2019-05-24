package com.doomsday.tournamentserver.db;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "setting")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int countPlayers;

    private int countPrizePlace;

    private String typeScheme;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date endDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime durationMatch;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startGameDay;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endGameDay;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public User user;

    public Setting() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTypeScheme() {
        return typeScheme;
    }

    public void setTypeScheme(String typeScheme) {
        this.typeScheme = typeScheme;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LocalTime getDurationMatch() {
        return durationMatch;
    }

    public void setDurationMatch(LocalTime durationMatch) {
        this.durationMatch = durationMatch;
    }

    public LocalTime getStartGameDay() {
        return startGameDay;
    }

    public void setStartGameDay(LocalTime startGameDay) {
        this.startGameDay = startGameDay;
    }

    public LocalTime getEndGameDay() {
        return endGameDay;
    }

    public void setEndGameDay(LocalTime endGameDay) {
        this.endGameDay = endGameDay;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
