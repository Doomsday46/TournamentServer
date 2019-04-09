package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Location;

import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;


public class DomainLocationService implements LocationService{

    private List<Location> locationsList;


    public DomainLocationService() {
        this.locationsList = new ArrayList<Location>();
    }
    @Override
    public List<Location> getAllLocations() {
        return locationsList;
    }
    @Override
    public void addLocation(Location newLocation) {
        if (newLocation == null) throw new NullPointerException("Null argument");
        if (this.locationsList.contains(newLocation)) {
            throw new DuplicateFormatFlagsException("Duplicate locations is not allowed");
        } else {
            this.locationsList.add(newLocation);
        }
    }
    @Override
    public void addAllLocation(List<Location> Locations) {
        if (Locations == null) throw new NullPointerException("Null argument");
        if (this.locationsList.containsAll(Locations)) {
            throw new DuplicateFormatFlagsException("Duplicate locations is not allowed");
        } else {
            this.locationsList.addAll(Locations);
        }
    }
    @Override
    public void removeLocation(Location existingLocation) {
        if (existingLocation == null) throw new NullPointerException("Null argument");
        if (this.locationsList.contains(existingLocation)) {
            this.locationsList.remove(existingLocation);
        } else {
            throw new IllegalArgumentException("Location doesn't exist in service");
        }
    }
    @Override
    public void removeLocationByPlace(String locationPlace) {
        if (locationPlace == null) throw new NullPointerException("Null argument");
        for (Location location : this.locationsList) {
            if (location.getPlace().equals(locationPlace)) {
                this.locationsList.remove(location);
                return;
            }
        }
        throw new IllegalArgumentException("Location with specified place doesn't exist in service");
    }
    @Override
    public Location getFreeLocation() {
        for (Location location : this.locationsList) {
            if (!(location.isBusy())) {
                return location;
            }
        }
        return null;
    }
    @Override
    public List<Location> getAllFreeLocations() {
        List<Location> freeLocationsList = new ArrayList<Location>();
        for (Location location : this.locationsList) {
            if (!(location.isBusy())) {
                freeLocationsList.add(location);
            }
        }
        return freeLocationsList;
    }
    @Override
    public void reserveLocation(Location location) {
        if (location == null) throw new NullPointerException("Null argument");
        if (this.locationsList.contains(location)) {
            if (!(location.isBusy())) {
                location.setBusy(true);
            } else {
                throw new IllegalArgumentException("Location is busy");
            }
        } else {
            throw new IllegalArgumentException("Location doesn't exist in dispather");
        }
    }
    @Override
    public void reserveLocationByPlace(String locationPlace) {
        if (locationPlace == null) throw new NullPointerException("Null argument");
        for (Location location : this.locationsList) {
            if (location.getPlace().equals(locationPlace)) {
                if (!(location.isBusy())) {
                    location.setBusy(true);
                    return;
                } else {
                    throw new IllegalArgumentException("Location is busy");
                }
            }

        }
        throw new IllegalArgumentException("Location doesn't exist in dispather");
    }
    @Override
    public void freeLocation(Location location) {
        if (location == null) throw new NullPointerException("Null argument");
        if (this.locationsList.contains(location)) {
            location.setBusy(false);

        } else {
            throw new IllegalArgumentException("Location doesn't exist in dispather");
        }
    }
    @Override
    public void freeLocationByPlace(String locationPlace) {
        if (locationPlace == null) throw new NullPointerException("Null argument");
        for (Location location : this.locationsList) {
            if (location.getPlace().equals(locationPlace)) {
                location.setBusy(false);
                return;
            }
        }
        throw new IllegalArgumentException("Location doesn't exist in dispather");
    }
}
