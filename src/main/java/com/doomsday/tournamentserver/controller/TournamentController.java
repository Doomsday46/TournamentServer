package com.doomsday.tournamentserver.controller;

import com.doomsday.tournamentserver.controller.response.Information;
import com.doomsday.tournamentserver.controller.response.Response;
import com.doomsday.tournamentserver.db.Entity.User;
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.TournamentToTournamentInfoMapper;
import com.doomsday.tournamentserver.service.TemplateHelper;
import com.doomsday.tournamentserver.service.TournamentService;
import com.doomsday.tournamentserver.service.UserService;
import com.doomsday.tournamentserver.service.implement.TournamentManagerImplService;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentSaveInformation;
import com.doomsday.tournamentserver.service.model.view.MatchView;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import com.doomsday.tournamentserver.validator.TournamentViewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final TournamentManagerImplService tournamentManagerImplService;

    @Autowired
    public TournamentController(TournamentService tournamentService, TournamentViewValidator tournamentViewValidator, UserService userService, TemplateHelper templateHelper, TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper, TournamentManagerImplService tournamentManagerImplService) {
        this.tournamentService = tournamentService;
        this.tournamentViewValidator = tournamentViewValidator;
        this.userService = userService;
        this.templateHelper = templateHelper;
        this.tournamentToTournamentInfoMapper = tournamentToTournamentInfoMapper;
        this.tournamentManagerImplService = tournamentManagerImplService;

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
    public ResponseEntity<Response> saveTournamentShortData(@PathVariable String name) {
        if (name.isEmpty())
            return new ResponseEntity<>(new Response<>(400, new Information(getMessage("incorrectParameter"))),HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            var tournament = tournamentService.saveTournament(user.getId(), name);

            if (tournament.getIdTournament() <= 0 || !tournament.getNameTournament().equals(name))
                return new ResponseEntity<>(getResponse(400, new Information(getMessage("saveModel.error"))), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(getResponseTournamentSaveInformation(200, new Information(tournament.getInformation()), tournament), HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/create/{idTournament}"}, method = RequestMethod.POST)
    public ResponseEntity<Response> createTournament(@PathVariable Long idTournament) {
        if (idTournament <= 0)
            return new ResponseEntity<>(new Response<>(400, new Information(getMessage("incorrectParameter"))),HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            var isCreate = tournamentManagerImplService.createTournament(user.getId(), idTournament);

            if (!isCreate) return new ResponseEntity<>(getResponse(400, new Information("failed")), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(getResponse(200, new Information("successfull")), HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/match/finish"}, method = RequestMethod.POST)
    public ResponseEntity<Response> createTournament(@RequestBody MatchView matchView) {
        if (matchView.getIdMatch() <= 0 || matchView.getIdTournament() <= 0)
            return new ResponseEntity<>(new Response<>(400, new Information(getMessage("incorrectParameter"))),HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            var isFinish = tournamentManagerImplService.finishMatch(user.getId(), matchView);

            if (!isFinish) return new ResponseEntity<>(getResponse(400, new Information("failed")), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(getResponse(200, new Information("successfull")), HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/"}, method = RequestMethod.POST)
    public ResponseEntity<Response> saveTournament(@RequestBody TournamentView tournamentView) {
        if (!tournamentViewValidator.setModel(tournamentView).isValid())
            return new ResponseEntity<>(getResponse(400, new Information(tournamentViewValidator.message())), HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            var idTournament = tournamentService.saveTournament(user.getId(), tournamentView);

            if (idTournament <= 0)
                return new ResponseEntity<>(getResponse(400, new Information(getMessage("saveModel.error"))), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(getResponse(200, new Information(getMessage("tournament.save.idTournament"))), HttpStatus.OK);

        } catch(Exception ex) {
            return  new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/data/{idTournament}"}, method = RequestMethod.PUT)
    public ResponseEntity<Response> updateTournamentData(@RequestBody TournamentView tournamentView, @PathVariable long idTournament) {

        if (!tournamentViewValidator.setModel(tournamentView).isValid())
            return new ResponseEntity<>(new Response<>(400, new Information(tournamentViewValidator.message())), HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            if (idTournament <= 0 || tournamentService.getTournament(user.getId(), idTournament) == null)
                return new ResponseEntity<>(getResponse(400, new Information(getMessage("updateModel.error.noFindTournament"))), HttpStatus.BAD_REQUEST);

            boolean isUpdate = tournamentService.updateDataForTournament(user.getId(), idTournament, tournamentView);

            if (!isUpdate) return new ResponseEntity<>(getResponse(400, new Information(getMessage("updateModel.error"))), HttpStatus.BAD_REQUEST);

            var tournamentInformation = tournamentService.getTournamentInformation(user.getId(), idTournament);

            return  new ResponseEntity<>(getResponseTournamentInformation(200, new Information(getMessage("successful")), tournamentInformation), HttpStatus.OK);

        } catch(Exception ex) {
            return  new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/info/{idTournament}"}, method = RequestMethod.GET)
    public ResponseEntity<Response> getTournamentInformation(@PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idTournament <= 0)
            return new ResponseEntity<>(getResponse(400, new Information(getMessage("argument.error.id"))),HttpStatus.BAD_REQUEST);

        try {
            var tournamentInformation = tournamentService.getTournamentInformation(user.getId(), idTournament);

            if (tournamentInformation == null)
                return new ResponseEntity<>(getResponse(400, new Information(getMessage("data.null"))), HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(getResponseTournamentInformation(200, new Information(getMessage("successful")), tournamentInformation),HttpStatus.OK);
        }   catch(Exception ex)  {
            return  new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/info/"}, method = RequestMethod.GET)
    public ResponseEntity<Response> getTournamentsInformation() {
        User user = userService.findByUsername(templateHelper.getUsername());
        try {
            var tournamentsInformation = tournamentService.getTournamentInformation(user.getId());

            if (tournamentsInformation.size() == 0)
                return new ResponseEntity<>(getResponse(200, new Information(getMessage("data.list.empty"))),HttpStatus.OK);

            return new ResponseEntity<>(new Response<>(200, new Information(getMessage("successful")), tournamentsInformation), HttpStatus.OK);
        }   catch(Exception ex)  {
            return  new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/{idTournament}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Response> deleteTournament(@PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idTournament <= 0)
            return new ResponseEntity<>(getResponse(400, new Information(getMessage("argument.error.id"))), HttpStatus.BAD_REQUEST);

        try {
            var isDelete = tournamentService.deleteTournament(user.getId(), idTournament);

            if (!isDelete)
                return new ResponseEntity<>(getResponse(400, new Information(getMessage("deleteModel.error"))), HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(getResponse(200, new Information(getMessage("successful"))), HttpStatus.OK);
        }   catch(Exception ex)  {
            return  new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    private String getMessage(String key) {
        return textProgram.getResourceBundle().getString(key);
    }
}
