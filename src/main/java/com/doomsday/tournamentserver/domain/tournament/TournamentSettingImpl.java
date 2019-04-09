package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeStrategy;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifier;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifierStrategy;

public class TournamentSettingImpl implements TournamentSetting {

    private TimeSetting timeSetting;
    private TournamentInfo tournamentInfo;
    private SchemeStrategy schemeStrategy;
    private WinnerIdentifierStrategy winnerIdentifierStrategy;

    public TournamentSettingImpl(WinnerIdentifierStrategy winnerIdentifierStrategy, SchemeStrategy schemeStrategy, TournamentInfo tournamentInfo, TimeSetting timeSetting) {
        if(tournamentInfo == null || schemeStrategy == null || timeSetting == null || winnerIdentifierStrategy == null) throw new NullPointerException();
        this.winnerIdentifierStrategy = winnerIdentifierStrategy;
        this.tournamentInfo = tournamentInfo;
        this.schemeStrategy = schemeStrategy;
        this.timeSetting = timeSetting;
    }

    private Scheme getSchemeByType(){
        return schemeStrategy.getScheme(tournamentInfo.getScheme(),tournamentInfo.getCountPlayers());
    }

    @Override
    public Scheme getScheme() {
        return getSchemeByType();
    }

    @Override
    public WinnerIdentifier getWinnerIdentifier() {
        return winnerIdentifierStrategy.getWinnnerIdentifier(tournamentInfo.getScheme());
    }

    @Override
    public Integer getPrizePlacesCount() {
        return tournamentInfo.getPrizePlacesCount();
    }

    @Override
    public TimeSetting getTimeSetting() {
        return timeSetting;
    }
}
