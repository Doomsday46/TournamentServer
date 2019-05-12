package com.doomsday.tournamentserver.service.model.information;

public class LocationViewInformation {

    private long idTournament;
    private long idLocation;

    private String name;
    private String description;
    private boolean isBase;

    public LocationViewInformation(long idTournament, long idLocation, String name, String description, boolean isBase) {
        this.idTournament = idTournament;
        this.idLocation = idLocation;
        this.name = name;
        this.description = description;
        this.isBase = isBase;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public long getIdLocation() {
        return idLocation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBase() {
        return isBase;
    }
}
