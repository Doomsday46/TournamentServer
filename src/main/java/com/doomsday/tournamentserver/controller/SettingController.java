package com.doomsday.tournamentserver.controller;

import com.doomsday.tournamentserver.controller.response.Information;
import com.doomsday.tournamentserver.controller.response.Response;
import com.doomsday.tournamentserver.db.User;
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.SettingToSettingInfoMapper;
import com.doomsday.tournamentserver.service.SettingService;
import com.doomsday.tournamentserver.service.TemplateHelper;
import com.doomsday.tournamentserver.service.UserService;
import com.doomsday.tournamentserver.service.model.information.SettingInformation;
import com.doomsday.tournamentserver.service.model.view.SettingView;
import com.doomsday.tournamentserver.validator.SettingViewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
public class SettingController {

    private final SettingService settingService;
    private final SettingViewValidator settingViewValidator;
    private final UserService userService;
    private final TemplateHelper templateHelper;
    private final TextProgram textProgram;
    private final SettingToSettingInfoMapper settingToSettingInfoMapper;

    @Autowired
    public SettingController(SettingService settingService, SettingViewValidator settingViewValidator, UserService userService, TemplateHelper templateHelper, SettingToSettingInfoMapper settingToSettingInfoMapper) {
        this.settingService = settingService;
        this.settingViewValidator = settingViewValidator;
        this.userService = userService;
        this.templateHelper = templateHelper;
        this.settingToSettingInfoMapper = settingToSettingInfoMapper;

        this.textProgram = new TextProgram("textResponse", Locale.ENGLISH);
    }

    private Response getResponseSettingView(int status, Information information, SettingView body){
        return new Response<>(status, information, body);
    }

    private Response getResponseSettingInformation(int status, Information information, SettingInformation body){
        return new Response<>(status, information, body);
    }

    private Response getResponse(int status, Information information){
        return new Response<>(status, information, null);
    }

    @RequestMapping(value = {"/api/tournament/setting/"}, method = RequestMethod.POST)
    public Response saveSettingForTournament(@RequestBody SettingView settingView) {
        if (!settingViewValidator.setModel(settingView).isValid())
            return new Response<>(400, new Information(settingViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            boolean isAdd = settingService.save(user.getId(), settingView);
            if (!isAdd) return getResponse(400, new Information(getMessage("saveModel.error")));
            return getResponse(200, new Information(getMessage("successful")));

        } catch(Exception ex) {

            return getResponse(400, new Information(ex.getMessage()));

        }
    }

    @RequestMapping(value = {"/api/tournament/setting/{idSetting}"}, method = RequestMethod.PUT)
    public Response updateSetting(@RequestBody SettingView settingView, @PathVariable long idSetting) {

        if (!settingViewValidator.setModel(settingView).isValid())
            return new Response<>(400, new Information(settingViewValidator.message()));

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            if (idSetting <= 0 || settingService.getSetting(user.getId(), idSetting) == null)
                return getResponse(400, new Information(getMessage("updateModel.error.noFindSetting")));

            boolean isUpdate = settingService.update(user.getId(), idSetting, settingView);
            if (!isUpdate) return getResponse(400, new Information(getMessage("updateModel.error")));

            var settingInformation = settingToSettingInfoMapper.map(settingService.getSetting(user.getId(), settingView.getIdTournament(), idSetting));

            return getResponseSettingInformation(200, new Information(getMessage("successful")), settingInformation);

        } catch(Exception ex) {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    @RequestMapping(value = {"/api/tournament/setting/{idTournament}/{idSetting}"}, method = RequestMethod.GET)
    public Response getSetting(@PathVariable long idSetting, @PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idSetting <= 0 || idTournament <= 0) return getResponse(400, new Information(getMessage("argument.error.id")));

        var setting = settingService.getSetting(user.getId(), idTournament, idSetting);

        if (setting == null) return getResponse(400, new Information(getMessage("data.null")));

        try {
            var settingInformation = settingToSettingInfoMapper.map(setting);
            return getResponseSettingInformation(200, new Information(getMessage("successful")), settingInformation);
        }   catch(Exception ex)  {
            return getResponse(400, new Information(ex.getMessage()));
        }
    }

    private String getMessage(String key) {
        return textProgram.getResourceBundle().getString(key);
    }
}
