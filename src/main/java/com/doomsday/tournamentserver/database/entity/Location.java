package com.doomsday.tournamentserver.database.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "location")
public class Location {
    private Long idLocation;
    private String name;
    private String description;
    private Boolean state;
    private User user;

    private Tournament tournament;

    public Location() {

    }

    public Location(String name, String description, Boolean state, Tournament tournament) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.tournament = tournament;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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
        Location location = (Location) o;
        return Objects.equals(idLocation, location.idLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocation);
    }
}
