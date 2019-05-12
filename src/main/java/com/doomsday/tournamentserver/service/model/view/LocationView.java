package com.doomsday.tournamentserver.service.model.view;

import java.util.Objects;

public class LocationView {

    private long idTournament;
    private String nameLocation;
    private String descriptionLocation;
    private boolean isBase;

    public LocationView(long idTournament, String nameLocation, String descriptionLocation, boolean isBase) {
        this.idTournament = idTournament;
        this.nameLocation = nameLocation;
        this.descriptionLocation = descriptionLocation;
        this.isBase = isBase;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public String getDescriptionLocation() {
        return descriptionLocation;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationView that = (LocationView) o;
        return idTournament == that.idTournament &&
                isBase == that.isBase &&
                Objects.equals(nameLocation, that.nameLocation) &&
                Objects.equals(descriptionLocation, that.descriptionLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTournament, nameLocation, descriptionLocation, isBase);
    }
}
