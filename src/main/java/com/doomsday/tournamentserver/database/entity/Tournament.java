package com.doomsday.tournamentserver.database.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tournament")
public class Tournament {
    private Long idTournament;
    private String name;

    private User user;
    private TournamentInfo tournamentInfo;
    private Setting setting;
    private Schedule schedule;

    private Set<Player> players;
    private Set<Location> locations;
    private Set<PrizePlace> prizePlaces;


    public Tournament() {

    }

    public Tournament(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Long idTournament) {
        this.idTournament = idTournament;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @OneToMany(mappedBy = "tournament")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Set<PrizePlace> getPrizePlaces() {
        return prizePlaces;
    }

    public void setPrizePlaces(Set<PrizePlace> prizePlaces) {
        this.prizePlaces = prizePlaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_info_id", referencedColumnName = "id")
    public TournamentInfo getTournamentInfo() {
        return tournamentInfo;
    }

    public void setTournamentInfo(TournamentInfo tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "setting_id", referencedColumnName = "id")
    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    @OneToMany(mappedBy = "tournament")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    @OneToMany(mappedBy = "tournament")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return Objects.equals(idTournament, that.idTournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTournament);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "idTournament=" + idTournament +
                ", name='" + name + '\''+'}';
    }
}