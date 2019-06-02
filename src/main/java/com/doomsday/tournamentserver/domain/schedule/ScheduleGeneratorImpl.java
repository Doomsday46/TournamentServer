package com.doomsday.tournamentserver.domain.schedule;

import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.OneOnOneMatch;
import com.doomsday.tournamentserver.domain.pair.Pair;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.service.DateService;
import com.doomsday.tournamentserver.domain.service.LocationService;
import com.doomsday.tournamentserver.domain.service.PlayerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleGeneratorImpl implements ScheduleGenerator{

    private PlayerService playerService;
    private LocationService locationService;
    private DateService dateService;
    private Scheme tournamentScheme;


    public ScheduleGeneratorImpl(PlayerService playerService, LocationService locationService, DateService dateService, Scheme tournamentScheme) {
        this.playerService = playerService;
        this.locationService = locationService;
        this.dateService = dateService;
        this.tournamentScheme = tournamentScheme;
    }

    @Override
    public Schedule generateSchedule(){
        Schedule newSchedule = new ScheduleImpl();
        newSchedule.addMatches(this.createMatches(newSchedule.getAllMatches()));
        return newSchedule;
    }

    @Override
    public Schedule updateSchedule(List<Match> matchesList, Schedule existingSchedule){
        if (matchesList == null || existingSchedule == null) throw new NullPointerException();
        for (Match match: matchesList)
        {
            if (match == null) throw new NullPointerException();
        }
        List<Integer> winnersList = new ArrayList<>();
        for (Match match: matchesList)
        {
            winnersList.add(playerService.getPlayerNumber(match.getWinner()));
        }
        return getSchedule(existingSchedule, winnersList);
    }

    private Schedule getSchedule(Schedule existingSchedule, List<Integer> winnersList) {
        this.tournamentScheme.updateScheme(winnersList);
        List<Match> newMatches = this.createMatches(existingSchedule.getAllMatches());
        if (newMatches.size() == 0) return existingSchedule;
        existingSchedule.addMatches(newMatches);
        return existingSchedule;
    }

    @Override
    public Schedule updateSchedule(Match match, Schedule existingSchedule){
        if (match== null || existingSchedule == null) throw new NullPointerException();
        List<Integer> winnersList = new ArrayList<>();
        winnersList.add(playerService.getPlayerNumber(match.getWinner()));
        return getSchedule(existingSchedule, winnersList);
    }

    @Override
    public Schedule recoverySchedule(List<Match> matchList) {
        List<Integer> winnersList = new ArrayList<>();

        for (var match: matchList) {
            if (match.isPlayed())
                winnersList.add(match.getWinner().getNumber());
        }

        if (winnersList.size() != 0) this.tournamentScheme.updateScheme(winnersList);

        Schedule recoverySchedule = new ScheduleImpl();
        recoverySchedule.addMatches(matchList);

        return recoverySchedule;
    }

    private List<Match> createMatches(List<Match> allMatches)
    {
        List<Match> matchesList = new ArrayList<>();
        List<Location> freeLocations = this.locationService.getAllFreeLocations();
        if (freeLocations.size() == 0)
            return matchesList;
        for (Location location: freeLocations)
        {
            Pair<Integer,Integer> playerPair = tournamentScheme.getNextNotPlayedPair();
            if (playerPair == null) break;
            locationService.reserveLocation(location);

            LocalDateTime localDateTime;
            if (allMatches.size() == 0) localDateTime = dateService.getNextDate();
            else {

                var  dateTime = allMatches.stream().map(Match::getDate).max(LocalDateTime::compareTo).get();;
                localDateTime = dateService.getNextDate(dateTime);
            }

            Match newMatch = new OneOnOneMatch(playerService.getPlayerByNumber(playerPair.getFirstObject()),
                    playerService.getPlayerByNumber(playerPair.getSecondObject()), location, localDateTime);
            matchesList.add(newMatch);
        }
        return matchesList;
    }

    public Scheme getScheme() {
        return tournamentScheme;
    }
}
