package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.service.model.view.PlayerView;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlayerViewValidator implements Validator<PlayerView> {

    private PlayerView playerView;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";
    private boolean isValid;

    private enum AGE {
        OLD,
        YOUNG,
        NORMAL
    }

    public PlayerViewValidator() {
        this.textProgram = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(PlayerView model) {
        this.playerView = model;
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
        isValid = isValidFirstName(playerView.getFirstName()) && isValidSecondName(playerView.getSecondName()) && checkAge(playerView.getBirthDay()).equals(AGE.NORMAL);
    }

    private String getMessage(boolean isValid) {
        if (isValid == true) {
            return textProgram.getResourceBundle().getString("validator.player.valid");
        } else {
            return textProgram.getResourceBundle().getString("validator.player.incorrect")
                    + getIncorrectNameField();
        }
    }

    private String getIncorrectNameField(){
        var firstNameField = (!isValidFirstName(playerView.getFirstName())) ? "firstName " : "";
        var secondNameField = (!isValidSecondName(playerView.getSecondName())) ? "secondName " : "";
        var idTournamentField = (!isValidIdTournament(playerView.getIdTournament())) ? "idTournament " : "";
        var ageField = "";
        var ageType = checkAge(playerView.getBirthDay());

        if (ageType.equals(AGE.OLD)) ageField = "birthDay ({" + textProgram.getResourceBundle().getString("validator.player.incorrect.age.old")  + "})";
        else if (ageType.equals(AGE.YOUNG)) ageField = "birthDay ({" + textProgram.getResourceBundle().getString("validator.player.incorrect.age.young")  + "})";

        return listNameField(idTournamentField, firstNameField, secondNameField, ageField);
    }

    private String listNameField(String idTournament, String firstName, String secondName, String birthDay){
        return idTournament + getSeparator(idTournament) + firstName + getSeparator(firstName) + secondName + getSeparator(secondName) + birthDay;
    }

    private String getSeparator(String str){
        return ((!str.equals("")) ? "," : "");
    }

    private boolean isValidFirstName(String name){
        return !name.isEmpty();
    }

    private boolean isValidSecondName(String name){
        return !name.isEmpty();
    }

    private boolean isValidIdTournament(long id){
        return id <= 0;
    }

    private AGE checkAge(Date birthDay){
        return AGE.NORMAL;
    }
}
