package com.doomsday.tournamentserver.controller;

import com.doomsday.tournamentserver.controller.response.Information;
import com.doomsday.tournamentserver.controller.response.Response;
import com.doomsday.tournamentserver.db.User;
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.TournamentToTournamentInfoMapper;
import com.doomsday.tournamentserver.service.TemplateHelper;
import com.doomsday.tournamentserver.service.TournamentService;
import com.doomsday.tournamentserver.service.UserService;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentSaveInformation;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import com.doomsday.tournamentserver.validator.TournamentViewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentViewValidator tournamentViewValidator;
    private final UserService userService;
    private final TemplateHelper templateHelper;
    private final TextProgram textProgram;
    private final TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper;

    @Autowired
    public TournamentController(TournamentService tournamentService, TournamentViewValidator tournamentViewValidator, UserService userService, TemplateHelper templateHelper, TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper) {
        this.tournamentService = tournamentService;
        this.tournamentViewValidator = tournamentViewValidator;
        this.userService = userService;
        this.templateHelper = templateHelper;
        this.tournamentToTournamentInfoMapper = tournamentToTournamentInfoMapper;

        this.textProgram = new TextProgram("textResponse", Locale.ENGLISH);
    }

    private Response getResponseTournamentView(int status, Information information, TournamentView body){
        return new Response<>(status, information, body);
    }

    private Response getResponseTournamentSaveInformation(int status, Information information, TournamentSaveInformation body){
        return new Response<>(status, information, body);
    }

    private Response getResponseTournamentInformation(int status, Information information, TournamentInformation body){
        return new Response<>(status, information, body);
    }

    private Response getResponse(int status, Information information){
        return new Response<>(status, information, null);
    }

    @RequestMapping(value = {"/api/tournament/{name}"}, method = RequestMethod.POST)
    public Response saveTournamentShortData(@PathVariable String name) {
        if (!name.isEmpty())
            return new Response<>(400, new Information(getMessage("incorrectParameter")));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            var tournament = tournamentService.saveTournament(user.getId(), name);

            if (tournament.getIdTournament() <= 0 || !tournament.getNameTournament().equals(name))
                return getResponse(400, new Information(getMessage("saveModel.error")));
            return getResponseTournamentSaveInformation(200, new Information(tournament.getInformation()), tournament);

        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/"}, method = RequestMethod.POST)
    public Response saveTournament(@RequestBody TournamentView tournamentView) {
        if (!tournamentViewValidator.setModel(tournamentView).isValid())
            return getResponse(400, new Information(tournamentViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            var idTournament = tournamentService.saveTournament(user.getId(), tournamentView);

            if (idTournament <= 0)
                return getResponse(400, new Information(getMessage("saveModel.error")));
            return getResponse(200, new Information(getMessage("tournament.save.idTournament")));

        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/data/{idTournament}"}, method = RequestMethod.PUT)
    public Response updateTournamentData(@RequestBody TournamentView tournamentView, @PathVariable long idTournament) {

        if (!tournamentViewValidator.setModel(tournamentView).isValid())
            return new Response<>(400, new Information(tournamentViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            if (idTournament <= 0 || tournamentService.getTournament(user.getId(), idTournament) == null)
                return getResponse(400, new Information(getMessage("updateModel.error.noFindTournament")));

            boolean isUpdate = tournamentService.updateDataForTournament(user.getId(), idTournament, tournamentView);

            if (!isUpdate) return getResponse(400, new Information(getMessage("updateModel.error")));

            var tournamentInformation = tournamentService.getTournamentInformation(user.getId(), idTournament);

            return getResponseTournamentInformation(200, new Information(getMessage("successful")), tournamentInformation);

        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/info/{idTournament}"}, method = RequestMethod.GET)
    public Response getTournamentInformation(@PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        try {
            var tournamentInformation = tournamentService.getTournamentInformation(user.getId(), idTournament);

            if (tournamentInformation == null) return getResponse(400, new Information(getMessage("data.null")));

            return getResponseTournamentInformation(200, new Information(getMessage("successful")), tournamentInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/info/"}, method = RequestMethod.GET)
    public Response getTournamentsInformation() {
        User user = userService.findByUsername(templateHelper.getUsername());
        try {
            var tournamentsInformation = tournamentService.getTournamentInformation(user.getId());

            if (tournamentsInformation.size() == 0) return getResponse(200, new Information(getMessage("data.list.empty")));

            return new Response<>(200, new Information(getMessage("successful")), tournamentsInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/{idTournament}"}, method = RequestMethod.DELETE)
    public Response deleteTournament(@PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        try {
            var isDelete = tournamentService.deleteTournament(user.getId(), idTournament);

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
