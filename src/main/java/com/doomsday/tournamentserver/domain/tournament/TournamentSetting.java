package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifier;
import com.doomsday.tournamentserver.service.DateService;

import java.time.LocalDateTime;

public interface TournamentSetting {

     DateService getDateDispatcher();
     Scheme getScheme(Integer playersCount);
     WinnerIdentifier getWinnerIdentifier();
     Integer getPrizePlacesCount();
     void setPrizePlacesCount(Integer prizePlacesCount);

}