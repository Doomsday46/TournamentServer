package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Location;
import com.doomsday.tournamentserver.service.model.information.LocationViewInformation;
import org.springframework.stereotype.Service;

@Service
public class LocationToLocationInfoMapper implements Mapper<LocationViewInformation, Location>  {

    @Override
    public LocationViewInformation map(Location object) {
        if (!object.getClass().equals(Location.class)) throw new IllegalArgumentException("incorrect model for mapping");

        var location = (Location) object;

        return new LocationViewInformation(location.getTournament().getId(), location.getId(), location.getName(), location.getDescription(), location.isState());
    }
}
