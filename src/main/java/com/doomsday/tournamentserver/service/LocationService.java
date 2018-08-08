package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.domain.model.Location;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface LocationService {
     List<Location> getAllLocations();

     void addLocation(Location newLocation);

     void addAllLocation(List<Location> Locations);

     void removeLocation(Location existingLocation);

     void removeLocationByPlace(String locationPlace);

     Location getFreeLocation();

     List<Location> getAllFreeLocations();

     void reserveLocation(Location location);

     void reserveLocationByPlace(String locationPlace);

     void freeLocation(Location location);

     void freeLocationByPlace(String locationPlace);
}
