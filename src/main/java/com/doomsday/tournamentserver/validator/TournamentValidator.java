package com.doomsday.tournamentserver.validator;

import com.doomsday.tournamentserver.db.Entity.Tournament;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.localization.TextProgram;
import org.springframework.stereotype.Service;

@Service
public class TournamentValidator implements Validator<Tournament> {

    private Tournament tournament;
    private TextProgram textProgram;
    private final String NAME_RESOURSE_BUNDEL = "textValidator";
    private boolean isValid;

    public TournamentValidator() {
        this.textProgram = new TextProgram(NAME_RESOURSE_BUNDEL);
    }

    @Override
    public Validator setModel(Tournament model) {
        this.tournament = model;
        setValid();
        return this;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public String message() {
        return textProgram.getResourceBundle().getString("validator.tournament.model.incorrect");
    }

    private void setValid(){
        var correctSetting  = (tournament.getSetting() != null) && isValidSetting();
        var correctTournament = !tournament.getFinished() && !tournament.getName().isEmpty() && !tournament.isStarted();
        isValid = correctSetting && correctTournament && isValidPlayers() && isValidLocations();
    }

    private boolean isValidPlayers(){
        return tournament.getPlayers().size() > 1;
    }

    private boolean isValidLocations(){
        return tournament.getLocations().size() > 0;
    }

    private boolean isValidSetting(){
        var setting = tournament.getSetting();
        var correctCountPlayer = tournament.getPlayers().size() == setting.getCountPlayers();
        var corretPrizePlace = setting.getCountPrizePlace() > 0 && setting.getCountPrizePlace() <= setting.getCountPlayers();
        var schemeType = SchemeType.valueOf(setting.getTypeScheme().toUpperCase());
        var correctTypeScheme =  schemeType == SchemeType.OLYMPIC || schemeType == SchemeType.ROUND;
        return correctCountPlayer && corretPrizePlace && correctTypeScheme;
    }
}
