package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.database.Entity.Location;
import com.doomsday.tournamentserver.service.model.view.LocationView;

import java.util.List;

public interface LocationService {
    boolean save(long idUser, LocationView location);
    boolean update(long idUser, long idLocation, LocationView locationView);
    boolean remove(long idUser, LocationView location);
    boolean remove(long idUser, long id);
    Location getLocation(long idUser, long idTournament, long idLocation);
    Location getLocation(long idUser, long idLocation);
    List<Location> getLocations(long idUser, long idTournament);
    List<Location> getLocations(long idUser, long idTournament, boolean state);

    boolean remove(long idUser, long idTournament, long idLocation);
}
