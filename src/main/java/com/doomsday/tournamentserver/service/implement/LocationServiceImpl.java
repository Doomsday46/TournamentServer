package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Location;
import com.doomsday.tournamentserver.service.LocationService;
import com.doomsday.tournamentserver.service.model.view.LocationView;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {
    @Override
    public boolean save(long idUser, LocationView location) {
        return false;
    }

    @Override
    public boolean update(long idUser, long idLocation, LocationView locationView) {
        return false;
    }

    @Override
    public boolean remove(long idUser, LocationView location) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long id) {
        return false;
    }

    @Override
    public Location getLocation(long idUser, long idTournament, long idLocation) {
        return null;
    }

    @Override
    public Location getLocation(long idUser, long idLocation) {
        return null;
    }

    @Override
    public List<Location> getLocations(long idUser, long idTournament) {
        return null;
    }

    @Override
    public List<Location> getLocations(long idUser, long idTournament, boolean state) {
        return null;
    }

    @Override
    public boolean remove(long idUser, long idTournament, long idLocation) {
        return false;
    }
}
