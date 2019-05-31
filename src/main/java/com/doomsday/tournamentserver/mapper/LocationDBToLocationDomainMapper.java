package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.domain.model.Location;
import org.springframework.stereotype.Service;

@Service
public class LocationDBToLocationDomainMapper implements Mapper<Location, com.doomsday.tournamentserver.db.Location> {
    @Override
    public Location map(com.doomsday.tournamentserver.db.Location object) {
        var location = new Location(object.getName(), object.getDescription());
        location.setBusy(object.isState());
        return location;
    }
}
