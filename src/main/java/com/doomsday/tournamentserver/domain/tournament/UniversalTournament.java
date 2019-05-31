package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.exception.FinishTournamentException;
import com.doomsday.tournamentserver.domain.exception.StartTournamentException;
import com.doomsday.tournamentserver.domain.model.*;
import com.doomsday.tournamentserver.domain.schedule.Schedule;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGenerator;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.service.DateService;
import com.doomsday.tournamentserver.domain.service.LocationService;
import com.doomsday.tournamentserver.domain.service.PlayerService;

import java.time.LocalDateTime;
import java.util.List;

public class UniversalTournament implements Tournament {

    private TournamentSetting tournamentSetting;
    private TournamentInfo tournamentInfo;
    private Schedule schedule;
    private ScheduleGenerator scheduleGenerator;
    private List<PrizePlace> prizePlaces;

    private Boolean isStart;

    private PlayerService playerService;
    private LocationService locationService;
    private DateService dateService;

    public UniversalTournament(TournamentSetting tournamentSetting, TournamentInfo tournamentInfo, Schedule schedule,
                               ScheduleGenerator scheduleGenerator, PlayerService playerService, LocationService locationService, DateService dateService) {
        if(scheduleGenerator == null || schedule == null || tournamentSetting == null || tournamentInfo == null) throw new NullPointerException();
        if(playerService == null || locationService == null || dateService == null) throw new NullPointerException();
        this.tournamentSetting = tournamentSetting;
        this.tournamentInfo = tournamentInfo;
        this.schedule = schedule;
        this.scheduleGenerator = scheduleGenerator;
        this.playerService = playerService;
        this.locationService = locationService;
        this.dateService = dateService;
        this.isStart = false;
    }

    @Override
    public String getName() {
        return tournamentInfo.getTournamentName();
    }

    @Override
    public List<Player> getPlayers() {
        return playerService.getAllPlayers();
    }

    @Override
    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @Override
    public SchemeType getSchemeType() {
        return tournamentInfo.getScheme();
    }

    @Override
    public PlayerService getPlayerService() {
        return playerService;
    }

    @Override
    public LocationService getLocationService() {
        return locationService;
    }

    @Override
    public DateService getDateService() {
        return dateService;
    }

    @Override
    public TournamentInfo getTournamentInfo() {
        return tournamentInfo;
    }

    @Override
    public TournamentSetting getTournamentSetting() {
        return tournamentSetting;
    }

    @Override
    public void start() {
        if (!this.isStart) {
            this.isStart = true;
            return;
        }
        throw new StartTournamentException("Tournament is started");
    }

    @Override
    public void finish() {
        if (this.isStart) {
            if (schedule.getMatchesByState(MatchState.PLAYED).size() == 0)
                throw new FinishTournamentException("Matches didn't played");
            if (this.schedule.getAllMatches().size() != this.scheduleGenerator.getScheme().getMaxPairCount())
                return;
            List<Player> winners = tournamentSetting.getWinnerIdentifier().identifyWinners(schedule.getAllMatches());
            for (int i = 0; i < this.tournamentSetting.getPrizePlacesCount(); i++) {
                this.prizePlaces.add(new PrizePlaceImpl(winners.get(i), i + 1));
            }
            dateService.setEndDate(LocalDateTime.now());
        } else
            throw new FinishTournamentException("Tournament is not started");
    }

    @Override
    public Match getNextMatch() {
        if (!(isStart)) throw new StartTournamentException("Tournament is not started");
        List<Match> matchesByState = schedule.getMatchesByState(MatchState.NOTPLAYED);
        if (matchesByState.size() == 0) return null;
        Match nextMatch = matchesByState.get(0);
        for (int i = 0; i < matchesByState.size() - 1; i++) {
            if (matchesByState.get(i).getDate().isBefore(matchesByState.get(i + 1).getDate())) {
                nextMatch = matchesByState.get(i);
            }
        }
        return nextMatch;
    }

    @Override
    public void finishMatch(Match match, Score score) {
        if (match == null || score == null) throw new NullPointerException();
        if (!(isStart)) throw new StartTournamentException("Tournament is not started");
        match.setPoints(score.getPointsFirstSide(), score.getPointsSecondSide());
        match.setMatchState(MatchState.PLAYED);
        this.locationService.freeLocation(match.getLocation());
        this.schedule = this.scheduleGenerator.updateSchedule(match, this.schedule);
    }

    @Override
    public void finishMatches(List<Match> matches, List<Score> points) {
        if (!(isStart)) throw new StartTournamentException("Tournament is not started");
        if (matches == null || points == null) throw new NullPointerException();
        for (int i = 0; i < matches.size(); i++) {
            finishMatch(matches.get(i), points.get(i));
        }
    }

    @Override
    public boolean isStart() {
        return isStart;
    }

    @Override
    public Player getThePrizePlace(int number) {
        for (PrizePlace prizePlace: prizePlaces) {
            if(prizePlace.getPrizePlace() == number) return  prizePlace.getPlayer();
        }
        return null;
    }

    @Override
    public Scheme getScheme() {
        return tournamentSetting.getScheme();
    }

}
