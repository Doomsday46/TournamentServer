package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.service.model.view.LocationView;
import org.springframework.stereotype.Service;

@Service
public class LocationViewValidator implements Validator<LocationView> {

    private LocationView locationView;
    private boolean isValid;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";

    public LocationViewValidator() {
        this.textProgram  = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(LocationView model) {
        this.locationView = model;
        setIsValid();
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

    private void setIsValid(){
        isValid = isValidBaseStatus(locationView.isBase()) && isValidDescription(locationView.getDescriptionLocation()) && isValidIdTournament(locationView.getIdTournament()) && isValidName(locationView.getNameLocation());
    }

    private String getMessage(boolean isValid) {
        if (isValid == true) {
            return textProgram.getResourceBundle().getString("validator.location.valid");
        } else {
            return textProgram.getResourceBundle().getString("validator.location.incorrect")
                    + getIncorrectNameField();
        }
    }

    private String getIncorrectNameField(){
        var firstNameField = (!isValidName(locationView.getNameLocation())) ? "name " : "";
        var secondNameField = (!isValidDescription(locationView.getDescriptionLocation())) ? "description " : "";
        var idTournamentField = (!isValidIdTournament(locationView.getIdTournament())) ? "idTournament " : "";
        var isBase = (!isValidBaseStatus(locationView.isBase())) ? "isBase " : "";

        return listNameField(idTournamentField, firstNameField, secondNameField, isBase);
    }

    private boolean isValidBaseStatus(boolean base) {
        return true;
    }

    private boolean isValidIdTournament(long idTournament) {
        return idTournament > 0;
    }

    private boolean isValidDescription(String descriptionLocation) {
        return true;
    }

    private boolean isValidName(String nameLocation) {
        return !nameLocation.isEmpty();
    }

    private String listNameField(String name, String descrition, String idTournament, String isBase){
        return name + getSeparator(name) + descrition + getSeparator(descrition) + idTournament + getSeparator(idTournament) + isBase;
    }

    private String getSeparator(String str){
        return ((!str.equals("")) ? "," : "");
    }
}
