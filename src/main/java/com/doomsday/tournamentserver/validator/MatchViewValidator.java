package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.service.model.view.MatchView;
import org.springframework.stereotype.Service;

@Service
public class MatchViewValidator implements Validator<MatchView> {

    private MatchView matchView;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";
    private boolean isValid;

    public MatchViewValidator() {
        this.textProgram = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(MatchView model) {
        this.matchView = model;
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
        isValid = isValidIdMatch(matchView.getIdMatch()) && isValidScoreFirstPlayer(matchView.getScoreFirstPlayer())
                    && isValidScoreSecondPlayer(matchView.getScoreSecondPlayer()) && isValidIsFinished(matchView.isFinished())
                    && isValidIdTournament(matchView.getIdTournament());
    }

    private String getMessage(boolean isValid) {
        if (isValid == true) {
            return textProgram.getResourceBundle().getString("validator.match.valid");
        } else {
            return textProgram.getResourceBundle().getString("validator.match.incorrect")
                    + getIncorrectNameField();
        }
    }

    private String getIncorrectNameField(){
        var idMatchField = (!isValidIdMatch(matchView.getIdMatch())) ? "idMatch " : "";
        var scoreFirstPlayerField = (!isValidScoreFirstPlayer(matchView.getScoreFirstPlayer())) ? "scoreFirstPlayer " : "";
        var scoreSecondPlayerField = (!isValidScoreSecondPlayer(matchView.getScoreSecondPlayer())) ? "scoreSecondPlayer " : "";
        var isFinishedField = (!isValidIsFinished(matchView.isFinished())) ? "isFinished " : "";
        var idTournamentField = (!isValidIdTournament(matchView.getIdTournament())) ? "idTournament " : "";

        return listNameField(idMatchField, scoreFirstPlayerField, scoreSecondPlayerField, isFinishedField, idTournamentField);
    }

    private String listNameField(String idMatchField, String scoreFirstPlayerField, String scoreSecondPlayerField, String isFinishedField, String idTournamentField) {
        return idMatchField + getSeparator(idMatchField) + scoreFirstPlayerField + getSeparator(scoreFirstPlayerField)
                + scoreSecondPlayerField + getSeparator(scoreSecondPlayerField) + isFinishedField + getSeparator(isFinishedField)
                + idTournamentField;
    }

    private boolean isValidIdTournament(long idTournament) {
        return idTournament > 0;
    }

    private boolean isValidIsFinished(boolean finished) {
        return true;
    }

    private boolean isValidScoreSecondPlayer(int scoreSecondPlayer) {
        return scoreSecondPlayer >= 0;
    }

    private boolean isValidScoreFirstPlayer(int scoreFirstPlayer) {
        return  scoreFirstPlayer >= 0;
    }

    private boolean isValidIdMatch(long idMatch) {
        return idMatch > 0;
    }

    private String getSeparator(String str){
        return ((!str.equals("")) ? "," : "");
    }
}
