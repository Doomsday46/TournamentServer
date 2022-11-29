package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.service.model.information.LocationViewInformation;
import org.springframework.stereotype.Service;

@Service
public class LocationViewInformationToLocationDomainMapper implements Mapper<Location, LocationViewInformation> {

    @Override
    public Location map(LocationViewInformation object) {
        var location = new Location(object.getName(), object.getDescription());
        System.out.println("LocationViewInformationToLocationDomainMapper call");
        location.setBusy(object.isBase());
        return location;
    }
}
