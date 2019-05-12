package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.service.model.TournamentTimeSetting;
import com.doomsday.tournamentserver.service.model.TypeScheme;
import com.doomsday.tournamentserver.service.model.view.MatchTimeSettingView;
import com.doomsday.tournamentserver.service.model.view.SettingView;
import org.springframework.stereotype.Service;

@Service
public class SettingViewValidator implements Validator<SettingView> {

    private SettingView settingView;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";
    private boolean isValid;

    public SettingViewValidator() {
        this.textProgram = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(SettingView model) {
        this.settingView = model;
        if (settingView.getTournamentTimeSetting() == null || settingView.getMathTimeSetting() == null) {
            isValid = false;
            return this;
        }
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

    private String getMessage(boolean isValid) {
        if (settingView.getTournamentTimeSetting() == null || settingView.getMathTimeSetting() == null) {
            return textProgram.getResourceBundle().getString("validator.setting.incorrectTimeField");
        }
        if (isValid == true) {
            return textProgram.getResourceBundle().getString("validator.setting.valid");
        } else {
            return textProgram.getResourceBundle().getString("validator.setting.incorrect")
                    + getIncorrectNameField();
        }
    }

    private void setValidStatus(){
        isValid = isValidCountPlayers(settingView.getCountPlayers()) && isValidCountPrizePlace(settingView.getCountPrizePlace(), settingView.getCountPlayers())
                                        && isValidIdTournament(settingView.getIdTournament()) && isValidMatchTimeSetting(settingView.getMathTimeSetting())
                                        && isValidTypeScheme(settingView.getTypeScheme()) && isValidTournamentTimeSetting(settingView.getTournamentTimeSetting());
    }

    private String getIncorrectNameField(){
        var countPlayersField = (!isValidCountPlayers(settingView.getCountPlayers())) ? "countPlayers " : "";
        var countPrizePlaceField = (!isValidCountPrizePlace(settingView.getCountPrizePlace(), settingView.getCountPlayers())) ? "countPrizePlace " : "";
        var idTournamentField = (!isValidIdTournament(settingView.getIdTournament())) ? "idTournament " : "";
        var typeSchemeField = (!isValidTypeScheme(settingView.getTypeScheme())) ? "typeScheme " : "";
        var matchTimeSettingField = (!isValidMatchTimeSetting(settingView.getMathTimeSetting())) ? "matchTimeSetting " : "";
        var tournamentTimeSettingField = (!isValidTournamentTimeSetting(settingView.getTournamentTimeSetting())) ? "tournamentTimeSetting " : "";

        return listNameField(countPlayersField, countPrizePlaceField, idTournamentField, typeSchemeField, matchTimeSettingField, tournamentTimeSettingField);
    }

    private String listNameField(String countPlayersField, String countPrizePlaceField, String idTournamentField, String typeSchemeField, String matchTimeSettingField, String tournamentTimeSettingField) {
        return countPlayersField + getSeparator(countPlayersField)
                + countPrizePlaceField + getSeparator(countPrizePlaceField)
                + idTournamentField + getSeparator(idTournamentField)
                + typeSchemeField + getSeparator(typeSchemeField)
                + matchTimeSettingField + getSeparator(matchTimeSettingField)
                + tournamentTimeSettingField;
    }

    private boolean isValidTournamentTimeSetting(TournamentTimeSetting tournamentTimeSetting) {
        if (tournamentTimeSetting.getEndTournament().compareTo(tournamentTimeSetting.getBeginTournament()) < 0) return false;
        if (tournamentTimeSetting.getEndGameDayTime().compareTo(tournamentTimeSetting.getBeginGameDayTime()) < 0) return false;

        var durationBeginGameDayMin = tournamentTimeSetting.getBeginGameDayTime().toSecondOfDay() / 60.0;
        var durationEndGameDayMin = tournamentTimeSetting.getEndGameDayTime().toSecondOfDay() / 60.0;

        var durationGameDayMinutesActually = durationEndGameDayMin - durationBeginGameDayMin;

        return !(durationGameDayMinutesActually < 0);
    }

    private boolean isValidMatchTimeSetting(MatchTimeSettingView matchTimeSetting) {
        if (matchTimeSetting.getDurationMatchHour() < 0 && matchTimeSetting.getDurationMatchMinute() < 0 && matchTimeSetting.getDurationMatchSeconds() < 0) return  false;
        if (matchTimeSetting.getDurationMatchHour() > 23 && matchTimeSetting.getDurationMatchMinute() > 59 && matchTimeSetting.getDurationMatchSeconds() > 59) return false;

        var minutes = matchTimeSetting.getDurationMatchHour() * 60 + matchTimeSetting.getDurationMatchMinute() + matchTimeSetting.getDurationMatchSeconds() / 60.0;


        return !(minutes < 1);
    }

    private boolean isValidTypeScheme(String typeScheme) {
        for (TypeScheme c : TypeScheme.values()) {
            if (c.name().equals(typeScheme)) {
                return !TypeScheme.UNDEFINED.toString().equals(typeScheme);
            }
        }
        return false;
    }

    private boolean isValidIdTournament(long idTournament) {
        return idTournament > 0;
    }

    private boolean isValidCountPrizePlace(int countPrizePlace, int countPlayers) {
        return countPrizePlace > 0 && countPrizePlace < countPlayers;
    }

    private boolean isValidCountPlayers(int countPlayers) {
        return countPlayers > 1;
    }

    private String getSeparator(String str){
        return ((!str.equals("")) ? "," : "");
    }
}
