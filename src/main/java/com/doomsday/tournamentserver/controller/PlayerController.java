package com.doomsday.tournamentserver.controller;

import com.doomsday.tournamentserver.controller.response.Information;
import com.doomsday.tournamentserver.controller.response.Response;
import com.doomsday.tournamentserver.db.User;
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.PlayerToPlayerInfoMapper;
import com.doomsday.tournamentserver.service.PlayerService;
import com.doomsday.tournamentserver.service.TemplateHelper;
import com.doomsday.tournamentserver.service.UserService;
import com.doomsday.tournamentserver.service.model.information.PlayerViewInformation;
import com.doomsday.tournamentserver.service.model.view.PlayerView;
import com.doomsday.tournamentserver.validator.PlayerViewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Locale;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final UserService userService;
    private final TemplateHelper templateHelper;
    private final TextProgram textProgram;
    private final PlayerToPlayerInfoMapper playerToPlayerInfoMapper;
    private final PlayerViewValidator playerViewValidator;

    @Autowired
    public PlayerController(PlayerService playerService, UserService userService, TemplateHelper templateHelper, PlayerToPlayerInfoMapper playerToPlayerInfoMapper, PlayerViewValidator playerViewValidator) {
        this.playerService = playerService;
        this.userService = userService;
        this.templateHelper = templateHelper;
        this.playerToPlayerInfoMapper = playerToPlayerInfoMapper;
        this.playerViewValidator = playerViewValidator;
        this.textProgram = new TextProgram("textResponse", Locale.ENGLISH);
    }

    private Response getResponsePlayerView(int status, Information information, PlayerView body){
        return new Response<>(status, information, body);
    }

    private Response getResponsePlayerViewInformation(int status, Information information, PlayerViewInformation body){
        return new Response<>(status, information, body);
    }

    private Response getResponse(int status, Information information){
        return new Response<>(status, information, null);
    }

    @RequestMapping(value = {"/api/tournament/players/"}, method = RequestMethod.POST)
    public Response savePlayer(@RequestBody PlayerView playerView) {
        if (!playerViewValidator.setModel(playerView).isValid())
            return new Response<>(400, new Information(playerViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            boolean isAdd = playerService.savePlayer(user.getId(), playerView);
            if (!isAdd) return getResponse(400, new Information(getMessage("saveModel.error")));
            return getResponse(200, new Information(getMessage("successful")));
        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/players/{idPlayer}"}, method = RequestMethod.PUT)
    public Response updatePlayer(@RequestBody PlayerView playerView, @PathVariable long idPlayer) {

        if (!playerViewValidator.setModel(playerView).isValid())
            return new Response<>(400, new Information(playerViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());
        try {
            if (idPlayer <= 0 || playerService.getPlayer(user.getId(), playerView.getIdTournament(), idPlayer) == null)
                return getResponse(400, new Information(getMessage("updateModel.error.noFindPlayer")));

            boolean isUpdate = playerService.updatePlayerInformation(user.getId(), idPlayer, playerView);
            if (!isUpdate) return getResponse(400, new Information(getMessage("updateModel.error")));

            var playerViewInformation = playerToPlayerInfoMapper.map(playerService.getPlayer(user.getId(), playerView.getIdTournament(), idPlayer));

            return getResponsePlayerViewInformation(200, new Information(getMessage("successful")), playerViewInformation);

        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/players/{idTournament}/{idPlayer}"}, method = RequestMethod.GET)
    public Response getPlayer(@PathVariable long idPlayer, @PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idPlayer <= 0 || idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        var player = playerService.getPlayer(user.getId(), idTournament, idPlayer);

        if (player == null) return getResponse(400, new Information(getMessage("data.null")));

        try {
            var playerInformation = playerToPlayerInfoMapper.map(player);
            return getResponsePlayerViewInformation(200, new Information(getMessage("successful")), playerInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/players/{idTournament}"}, method = RequestMethod.GET)
    public Response getPlayers(@PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        var players = playerService.getPlayers(user.getId(), idTournament);

        if (players == null) return getResponse(400, new Information(getMessage("data.null")));

        var playersInformation = new ArrayList<PlayerViewInformation>();

        try {
            players.forEach(a -> { if (!playersInformation.contains(a)) playersInformation.add(playerToPlayerInfoMapper.map(a)); });
            return new Response<>(200, new Information(getMessage("successful")), playersInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/players/{idTournament}/{idPlayer}"}, method = RequestMethod.DELETE)
    public Response deletePlayer(@PathVariable long idTournament, @PathVariable long idPlayer) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idPlayer <= 0 || idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        try {
            var isDelete = playerService.remove(user.getId(), idTournament, idPlayer);

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
