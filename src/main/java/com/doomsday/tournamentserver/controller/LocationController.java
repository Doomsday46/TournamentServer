package com.doomsday.tournamentserver.controller;

import com.doomsday.tournamentserver.controller.response.Information;
import com.doomsday.tournamentserver.controller.response.Response;
import com.doomsday.tournamentserver.db.User;
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.LocationToLocationInfoMapper;
import com.doomsday.tournamentserver.service.LocationService;
import com.doomsday.tournamentserver.service.TemplateHelper;
import com.doomsday.tournamentserver.service.UserService;
import com.doomsday.tournamentserver.service.model.information.LocationViewInformation;
import com.doomsday.tournamentserver.service.model.view.LocationView;
import com.doomsday.tournamentserver.validator.LocationViewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Locale;

public class LocationController {

    private final LocationService locationService;
    private final LocationViewValidator locationViewValidator;
    private final UserService userService;
    private final TemplateHelper templateHelper;
    private final TextProgram textProgram;
    private final LocationToLocationInfoMapper locationToLocationInfoMapper;

    @Autowired
    public LocationController(LocationService locationService, LocationViewValidator locationViewValidator, UserService userService, TemplateHelper templateHelper, LocationToLocationInfoMapper locationToLocationInfoMapper) {
        this.locationService = locationService;
        this.locationViewValidator = locationViewValidator;
        this.userService = userService;
        this.templateHelper = templateHelper;
        this.locationToLocationInfoMapper = locationToLocationInfoMapper;
        this.textProgram = new TextProgram("textResponse", Locale.ENGLISH);
    }

    private Response getResponseLocationView(int status, Information information, LocationView body){
        return new Response<>(status, information, body);
    }

    private Response getResponseLocationViewInformation(int status, Information information, LocationViewInformation body){
        return new Response<>(status, information, body);
    }

    private Response getResponse(int status, Information information){
        return new Response<>(status, information, null);
    }

    @RequestMapping(value = {"/api/tournament/locations/"}, method = RequestMethod.POST)
    public Response saveLocation(@RequestBody LocationView locationView) {
        if (!locationViewValidator.setModel(locationView).isValid())
            return new Response<>(400, new Information(locationViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            boolean isAdd = locationService.save(user.getId(), locationView);
            if (!isAdd) return getResponse(400, new Information(getMessage("saveModel.error")));
            return getResponse(200, new Information(getMessage("successful")));
        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/locations/{idLocation}"}, method = RequestMethod.PUT)
    public Response updateLocation(@RequestBody LocationView locationView, @PathVariable long idLocation) {

        if (!locationViewValidator.setModel(locationView).isValid())
            return new Response<>(400, new Information(locationViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            if (idLocation <= 0 || locationService.getLocation(user.getId(), idLocation) == null)
                return getResponse(400, new Information(getMessage("updateModel.error.noFindLocation")));

            boolean isUpdate = locationService.update(user.getId(), idLocation, locationView);
            if (!isUpdate) return getResponse(400, new Information(getMessage("updateModel.error")));

            var locationViewInformation = locationToLocationInfoMapper.map(locationService.getLocation(user.getId(), locationView.getIdTournament(), idLocation));

            return getResponseLocationViewInformation(200, new Information(getMessage("successful")), locationViewInformation);

        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/locations/{idTournament}/{idLocation}"}, method = RequestMethod.GET)
    public Response getLocation(@PathVariable long idLocation, @PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idLocation <= 0 || idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        var location = locationService.getLocation(user.getId(), idTournament, idLocation);

        if (location == null) return getResponse(400, new Information(getMessage("data.null")));

        try {
            var locationViewInformation = locationToLocationInfoMapper.map(location);
            return getResponseLocationViewInformation(200, new Information(getMessage("successful")), locationViewInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/locations/{idTournament}"}, method = RequestMethod.GET)
    public Response getLocations(@PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        var locations = locationService.getLocations(user.getId(), idTournament);

        if (locations == null) return getResponse(400, new Information(getMessage("data.null")));

        var locationsInformation = new ArrayList<LocationViewInformation>();

        try {
            locations.forEach(a -> { if (!locationsInformation.contains(a)) locationsInformation.add(locationToLocationInfoMapper.map(a)); });
            return new Response<>(200, new Information(getMessage("successful")), locationsInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/locations/{idTournament}/{idLocation}"}, method = RequestMethod.DELETE)
    public Response deleteLocation(@PathVariable long idTournament, @PathVariable long idLocation) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idLocation <= 0 || idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        try {
            var isDelete = locationService.remove(user.getId(), idTournament, idLocation);

            if (!isDelete) return getResponse(400, new Information(getMessage("deleteModel.error")));

            return getResponse(200, new Information(getMessage("successful")));
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    private String getMessage(String key) {
        return textProgram.getResourceBundle().getString(key);
    }
}
