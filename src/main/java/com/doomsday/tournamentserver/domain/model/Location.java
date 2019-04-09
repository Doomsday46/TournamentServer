package com.doomsday.tournamentserver.domain.model;

public class Location {
    private String place;
    private String description;

    private Boolean isBusy;

    public Location(String place, String description){
        if (place == null || description == null)
        {
            throw new NullPointerException();
        }
        if (place.isEmpty())
        {
            throw  new IllegalArgumentException();
        }
        this.place = place;
        this.description = description;
        this.isBusy = false;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isBusy() {
        return isBusy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return getPlace().equals(location.getPlace());
    }

    public void setBusy(Boolean busy){
        if (busy == null)
            throw new NullPointerException();
        isBusy = busy;
    }
}
