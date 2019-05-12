package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.service.model.view.PrizePlaceView;
import org.springframework.stereotype.Service;

@Service
public class PrizePlaceViewValidator implements Validator<PrizePlaceView> {

    private PrizePlaceView prizePlaceView;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";
    private boolean isValid;

    public PrizePlaceViewValidator() {
        this.textProgram = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(PrizePlaceView model) {
        this.prizePlaceView = model;
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
        isValid = isValidIdPlayer(prizePlaceView.getIdPlayer()) && isValidIdTournament(prizePlaceView.getPrizePlaceNumber()) && isValidIdTournament(prizePlaceView.getIdTournament());
    }

    private String getMessage(boolean isValid) {
        if (isValid == true) {
            return textProgram.getResourceBundle().getString("validator.prizePlace.valid");
        } else {
            return textProgram.getResourceBundle().getString("validator.prizePlace.incorrect")
                    + getIncorrectNameField();
        }
    }

    private String getIncorrectNameField(){
        var idPlayerField = (!isValidIdPlayer(prizePlaceView.getIdPlayer())) ? "idPlayer " : "";
        var prizePlaceNumberField = (!isValidSPrizePlaceNumber(prizePlaceView.getPrizePlaceNumber())) ? "prizePlaceNumberField " : "";
        var idTournamentField = (!isValidIdTournament(prizePlaceView.getIdTournament())) ? "idTournament " : "";

        return listNameField(idPlayerField, prizePlaceNumberField, idTournamentField);
    }

    private boolean isValidSPrizePlaceNumber(int prizePlaceNumber) {
        return prizePlaceNumber > 0;
    }

    private boolean isValidIdTournament(long idTournament) {
        return idTournament > 0;
    }

    private boolean isValidIdPlayer(long idPlayer) {
        return idPlayer > 0;
    }

    private String listNameField(String idPlayerField, String prizePlaceNumberField, String idTournamentField){
        return idPlayerField + getSeparator(idPlayerField) + prizePlaceNumberField + getSeparator(prizePlaceNumberField) + idTournamentField;
    }

    private String getSeparator(String str){
        return ((!str.equals("")) ? "," : "");
    }
}
