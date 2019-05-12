package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentViewValidator implements Validator<TournamentView> {

    private TournamentView tournamentView;
    private SettingViewValidator settingViewValidator;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";
    private boolean isValid;

    public TournamentViewValidator(SettingViewValidator settingViewValidator) {
        this.settingViewValidator = settingViewValidator;
        this.textProgram = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(TournamentView model) {
        this.tournamentView = model;
        if (tournamentView.getSettingView() == null) {
            isValid = false;
            return this;
        }
        settingViewValidator.setModel(tournamentView.getSettingView());
        setValidStatus();
        return this;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public String message() {
        return getMessage(isValid);
    }

    private void setValidStatus(){
        isValid = isValidLocationsId(tournamentView.getLocationsId()) && isValidNameTournament(tournamentView.getNameTournament())
                && isValidScorePlayersId(tournamentView.getPlayersId()) && settingViewValidator.isValid();
    }

    private String getMessage(boolean isValid) {
        if (tournamentView.getSettingView() == null) {
            return textProgram.getResourceBundle().getString("validator.tournament.incorrectSettingView");
        }
        if (isValid == true) {
            return textProgram.getResourceBundle().getString("validator.tournament.valid");
        } else {
            return textProgram.getResourceBundle().getString("validator.tournament.incorrect")
                    + getIncorrectNameField();
        }
    }

    private String getIncorrectNameField(){
        var locationsIdField = (!isValidLocationsId(tournamentView.getLocationsId())) ? "locationsId " : "";
        var nameTournamentField = (!isValidNameTournament(tournamentView.getNameTournament())) ? "nameTournament " : "";
        var playersIdField = (!isValidScorePlayersId(tournamentView.getPlayersId())) ? "playersId " : "";
        var settingViewField = (!settingViewValidator.isValid())? "settingView: ( "
                + settingViewValidator.message() + ") ": "";

        return listNameField(locationsIdField, nameTournamentField, playersIdField, settingViewField);
    }

    private boolean isValidNameTournament(String nameTournament) {
        return !nameTournament.isEmpty();
    }

    private boolean isValidScorePlayersId(List<Integer> playersId) {
        for (var playerId: playersId) {
            if (playerId <= 0) return false;
        }
        return playersId.size() > 1;
    }

    private boolean isValidLocationsId(List<Integer> locationsId) {
        for (var locationId: locationsId) {
            if (locationId <= 0) return false;
        }
        return locationsId.size() > 0;
    }

    private String listNameField(String settingViewField, String locationsIdField, String nameTournamentField, String playersIdField) {
        return settingViewField + getSeparator(settingViewField) + locationsIdField + getSeparator(locationsIdField) + nameTournamentField + getSeparator(nameTournamentField) + playersIdField;
    }

    private String getSeparator(String str){
        return ((!str.equals("")) ? "," : "");
    }
}
