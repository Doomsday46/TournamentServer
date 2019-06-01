package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Entity.Location;
import com.doomsday.tournamentserver.service.model.view.LocationView;
import org.springframework.stereotype.Service;

@Service
public class LocationViewToLocationMapper implements Mapper<Location, LocationView> {

    @Override
    public Location map(LocationView object) {
        Location location = new Location();
        location.setName(object.getNameLocation());
        location.setDescription(object.getDescriptionLocation());
        location.setState(false);
        return location;
    }
}
