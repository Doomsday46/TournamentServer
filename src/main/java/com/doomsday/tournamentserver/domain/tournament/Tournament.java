package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.model.*;
import com.doomsday.tournamentserver.domain.schedule.Schedule;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.service.DateService;
import com.doomsday.tournamentserver.domain.service.LocationService;
import com.doomsday.tournamentserver.domain.service.PlayerService;

import java.util.List;

public interface Tournament {
     String getName();
     List<Player> getPlayers();
     Schedule getSchedule();
     List<Location> getLocations();
     SchemeType getSchemeType();
     PlayerService getPlayerService();
     LocationService getLocationService();
     DateService getDateService();
     TournamentInfo getTournamentInfo();
     TournamentSetting getTournamentSetting();
     void start() ;
     void finish();
     Match getNextMatch() ;
     void finishMatch(Match match, Score score) ;
     void finishMatches(List<Match> matches,List<Score> points) ;
     boolean isStart();
     Player getThePrizePlace(int number);
     List<PrizePlace> getPrizePlace();
     Scheme getScheme();
}
