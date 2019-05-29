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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Response> saveSettingForTournament(@RequestBody SettingView settingView) {
        if (!settingViewValidator.setModel(settingView).isValid())
            return new ResponseEntity<>(new Response<>(400, new Information(settingViewValidator.message())), HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {

            boolean isAdd = settingService.save(user.getId(), settingView);
            if (!isAdd) return new ResponseEntity<>(getResponse(400, new Information(getMessage("saveModel.error"))), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(getResponse(200, new Information(getMessage("successful"))), HttpStatus.OK);

        } catch(Exception ex) {

            return new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())), HttpStatus.BAD_REQUEST);

        }
    }

    @RequestMapping(value = {"/api/tournament/setting/{idSetting}"}, method = RequestMethod.PUT)
    public ResponseEntity<Response> updateSetting(@RequestBody SettingView settingView, @PathVariable long idSetting) {

        if (!settingViewValidator.setModel(settingView).isValid())
            return new ResponseEntity<>(new Response<>(400, new Information(settingViewValidator.message())),HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(templateHelper.getUsername());

        try {
            if (idSetting <= 0 || settingService.getSetting(user.getId(), idSetting) == null)
            return  new ResponseEntity<>(getResponse(400, new Information(getMessage("updateModel.error.noFindSetting"))), HttpStatus.BAD_REQUEST);

            boolean isUpdate = settingService.update(user.getId(), idSetting, settingView);
            if (!isUpdate) return  new ResponseEntity<>(getResponse(400, new Information(getMessage("updateModel.error"))), HttpStatus.BAD_REQUEST);

            var settingInformation = settingToSettingInfoMapper.map(settingService.getSetting(user.getId(), settingView.getIdTournament(), idSetting));

            return  new ResponseEntity<>(getResponseSettingInformation(200, new Information(getMessage("successful")), settingInformation), HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/tournament/setting/{idTournament}/{idSetting}"}, method = RequestMethod.GET)
    public ResponseEntity<Response> getSetting(@PathVariable long idSetting, @PathVariable long idTournament) {
        User user = userService.findByUsername(templateHelper.getUsername());

        if (idSetting <= 0 || idTournament <= 0) return new ResponseEntity<>(getResponse(400, new Information(getMessage("argument.error.id"))), HttpStatus.BAD_REQUEST);

        var setting = settingService.getSetting(user.getId(), idTournament, idSetting);

        if (setting == null) return new ResponseEntity<>(getResponse(400, new Information(getMessage("data.null"))), HttpStatus.BAD_REQUEST);

        try {
            var settingInformation = settingToSettingInfoMapper.map(setting);
            return new ResponseEntity<>(getResponseSettingInformation(200, new Information(getMessage("successful")), settingInformation), HttpStatus.OK);
        }   catch(Exception ex)  {
            return new ResponseEntity<>(getResponse(400, new Information(ex.getMessage())),HttpStatus.BAD_REQUEST);
        }
    }

    private String getMessage(String key) {
        return textProgram.getResourceBundle().getString(key);
    }
}
