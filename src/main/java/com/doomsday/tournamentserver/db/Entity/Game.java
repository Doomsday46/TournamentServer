package com.doomsday.tournamentserver.db.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int scoreFirstSide;
    private int scoreSecondSide;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date date;
    private boolean state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Tournament tournament;

    @ManyToMany
    @JoinTable(
            name = "player_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> players = new HashSet<Player>();

    @Column
    private int numberFirstSide;
    @Column
    private int numberSecondSide;

    @Column
    private long winnerId;

    public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScoreFirstSide() {
        return scoreFirstSide;
    }

    public void setScoreFirstSide(int scoreFirstSide) {
        this.scoreFirstSide = scoreFirstSide;
    }

    public int getScoreSecondSide() {
        return scoreSecondSide;
    }

    public void setScoreSecondSide(int scoreSecondSide) {
        this.scoreSecondSide = scoreSecondSide;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int getNumberFirstSide() {
        return numberFirstSide;
    }

    public void setNumberFirstSide(int numberFirstSide) {
        this.numberFirstSide = numberFirstSide;
    }

    public int getNumberSecondSide() {
        return numberSecondSide;
    }

    public void setNumberSecondSide(int numberSecondSide) {
        this.numberSecondSide = numberSecondSide;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }
}
