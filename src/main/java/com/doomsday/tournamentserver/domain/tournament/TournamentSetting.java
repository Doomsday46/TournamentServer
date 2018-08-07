package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifier;


public interface TournamentSetting {

     Scheme getScheme();
     WinnerIdentifier getWinnerIdentifier();
     Integer getPrizePlacesCount();
     TimeSetting getTimeSetting();

}