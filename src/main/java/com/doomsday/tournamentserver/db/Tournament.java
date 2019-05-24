package com.doomsday.tournamentserver.db;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column
    public String name;

    @Column
    public boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "setting_id", referencedColumnName = "id")
    public Setting setting;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<PrizePlace> prizePlace = new ArrayList<PrizePlace>();
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Match> matches = new ArrayList<Match>();
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Location> locations = new ArrayList<Location>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Player> players = new ArrayList<Player>();

    public Tournament() {
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public List<PrizePlace> getPrizePlace() {
        return prizePlace;
    }

    public void setPrizePlace(List<PrizePlace> prizePlace) {
        this.prizePlace = prizePlace;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
