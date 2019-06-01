package com.doomsday.tournamentserver.db.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String surname;
    private int number;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prizePlace_id", referencedColumnName = "id")
    private PrizePlace prizePlace;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Tournament tournament;


    @ManyToMany(mappedBy = "players")
    private Set<Game> games = new HashSet();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public User user;

    public Player() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public PrizePlace getPrizePlace() {
        return prizePlace;
    }

    public void setPrizePlace(PrizePlace prizePlace) {
        this.prizePlace = prizePlace;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void addGames(Game game) {
        this.games.add(game);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
