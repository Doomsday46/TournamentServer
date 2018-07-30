package com.doomsday.tournamentserver.domain.schedule;

import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.MatchState;
import com.doomsday.tournamentserver.domain.model.Player;

import java.time.LocalDateTime;
import java.util.List;

public interface Schedule {
     List<Match> getAllMatches();
     void addMatch(Match match);
     void addMatches(List<Match> matches);
     List<Match> getMatchesByState(MatchState state);
     List<Match> getMatchesByPlayer(Player player);
     List<Match> getMatchesByDate(LocalDateTime date);
     List<Match> getMatchesByLocation(Location location);

}
