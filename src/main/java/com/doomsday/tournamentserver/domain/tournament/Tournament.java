package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.domain.model.Score;
import com.doomsday.tournamentserver.domain.schedule.Schedule;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;

import java.util.List;

public interface Tournament {
     String getName();
     List<Player> getPlayers();
     Schedule getSchedule();
     List<Location> getLocations();
     SchemeType getSchemeType();
     void start() ;
     void finish();
     Match getNextMatch() ;
     void finishMatch(Match match, Score score) ;
     void finishMatches(List<Match> matches,List<Score> points) ;
     boolean isStart();
     Player getThePrizePlace(int count);
     Scheme getScheme();
     Tournament clone();
}
