package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGeneratorImpl;
import com.doomsday.tournamentserver.domain.scheme.OlympicScheme;
import com.doomsday.tournamentserver.domain.scheme.RoundScheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeStrategy;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.service.*;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifierStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
public class UniversalTournamentTests {

    private Tournament tournament;
    private List<Location> listLocation;
    private List<Player> listPlayers;
    private TimeSetting timeSetting;
    private TournamentSetting tournamentSettingRound;
    private TournamentSetting tournamentSettingOlympic;
    private TournamentInfo tournamentInfoRound;
    private TournamentInfo tournamentInfoOlympic;
    private int countPlayersRound;
    private int countPlayersOlympic;
    private int countPrizePlaceOlympic;
    private int countPrizePlaceRound;
    private PlayerService domainPlayerService;
    private LocationService domainLocationService;
    private DateService domainDateService;
    private LocalDateTime beginDate;


    @Before
    public void initTest()
    {
        listLocation = new ArrayList<Location>();
        listPlayers = new ArrayList<Player>();

        countPlayersRound = 10;
        countPlayersOlympic = 10;
        countPrizePlaceOlympic = 3;
        countPrizePlaceRound = 3;

        for (int i = 0; i < countPlayersRound; i++) {
            var player = new Player("firstName"+i, "secondName" + i, LocalDate.of(1977,1,1).plusDays(i));
            player.setNumber(i + 1);
            listPlayers.add(player);
        }

        for (int i = 0; i < 100; i++) {
            listLocation.add(new Location("table"+i, "description" + i));
        }

        var hourBegin = 8;
        var hourEnd = 22;
        var offsetTimeMinutes = 30.0;

        timeSetting = new TimeSetting(hourBegin, hourEnd, offsetTimeMinutes);
        beginDate = LocalDateTime.of(2019, 7, 5,10, 0, 0);

        var winnerIdentifierStrategy = new WinnerIdentifierStrategy();
        var schemeStrategy = new SchemeStrategy();

        domainPlayerService = new DomainPlayerService();
        domainPlayerService.addNewPlayers(listPlayers);
        domainLocationService = new DomainLocationService();
        domainLocationService.addAllLocation(listLocation);
        domainDateService = new DomainDateService();
        domainDateService.setTimeSetting(beginDate, timeSetting);



        tournamentInfoRound = new TournamentInfoImpl(countPlayersRound, SchemeType.ROUND, "roundTournament", countPrizePlaceRound, beginDate);
        tournamentInfoOlympic = new TournamentInfoImpl(countPlayersOlympic, SchemeType.OLYMPIC, "olympicTournament", countPrizePlaceOlympic, beginDate);

        tournamentSettingRound = new TournamentSettingImpl(winnerIdentifierStrategy, schemeStrategy, tournamentInfoRound, timeSetting);
        tournamentSettingOlympic = new TournamentSettingImpl(winnerIdentifierStrategy, schemeStrategy, tournamentInfoOlympic, timeSetting);
    }

    @Test
    public void test_createTournamentOlympicScheme_ResultSuccess(){
        var scheduleGenerator = new ScheduleGeneratorImpl(domainPlayerService, domainLocationService, domainDateService, new OlympicScheme(countPlayersOlympic));
        var schedule = scheduleGenerator.generateSchedule();
        tournament = new UniversalTournament(tournamentSettingOlympic, tournamentInfoOlympic, schedule, scheduleGenerator, domainPlayerService, domainLocationService, domainDateService);
    }

    @Test
    public void test_createTournamentRoundScheme_ResultSuccess(){
        var scheduleGenerator = new ScheduleGeneratorImpl(domainPlayerService, domainLocationService, domainDateService, new RoundScheme(countPlayersRound));
        var schedule = scheduleGenerator.generateSchedule();
        tournament = new UniversalTournament(tournamentSettingRound, tournamentInfoRound, schedule, scheduleGenerator, domainPlayerService, domainLocationService, domainDateService);
    }

    @Test
    public void test_getAllMatchRoundScheme_ResultSuccess(){

        PlayerService playerService = new DomainPlayerService();
        LocationService locationService = new DomainLocationService();

        playerService.addNewPlayers(getPlayers(3));
        locationService.addAllLocation(getLocations(10));

        var winnerIdentifierStrategy = new WinnerIdentifierStrategy();
        var schemeStrategy = new SchemeStrategy();

        TournamentInfoImpl tournamentInfoRoundLocal = new TournamentInfoImpl(3, SchemeType.ROUND, "roundTournament", 1, beginDate);
        TournamentSettingImpl tournamentSettingRoundLocal = new TournamentSettingImpl(winnerIdentifierStrategy, schemeStrategy, tournamentInfoRoundLocal, timeSetting);

        var scheduleGenerator = new ScheduleGeneratorImpl(playerService, locationService, domainDateService, new RoundScheme(3));
        var schedule = scheduleGenerator.generateSchedule();

        tournament = new UniversalTournament(tournamentSettingRoundLocal, tournamentInfoRoundLocal, schedule, scheduleGenerator, playerService, locationService, domainDateService);
        Assert.assertEquals(3, tournament.getScheme().getAllPairs().size());
    }

    @Test
    public void test_getAllMatchOlympicScheme_ResultSuccess(){

        PlayerService playerService = new DomainPlayerService();
        LocationService locationService = new DomainLocationService();

        playerService.addNewPlayers(getPlayers(3));
        locationService.addAllLocation(getLocations(10));

        var winnerIdentifierStrategy = new WinnerIdentifierStrategy();
        var schemeStrategy = new SchemeStrategy();

        TournamentInfoImpl tournamentInfoRoundLocal = new TournamentInfoImpl(3, SchemeType.OLYMPIC, "OlympicTournament", 1, beginDate);
        TournamentSettingImpl tournamentSettingRoundLocal = new TournamentSettingImpl(winnerIdentifierStrategy, schemeStrategy, tournamentInfoRoundLocal, timeSetting);

        var scheduleGenerator = new ScheduleGeneratorImpl(playerService, locationService, domainDateService, new OlympicScheme(3));
        var schedule = scheduleGenerator.generateSchedule();

        tournament = new UniversalTournament(tournamentSettingRoundLocal, tournamentInfoRoundLocal, schedule, scheduleGenerator, playerService, locationService, domainDateService);
        Assert.assertEquals(1, tournament.getSchedule().getAllMatches().size());
    }

    @Test
    public void test_getAllMatchOlympicSchemeWhenSixPlayers_ResultSuccess(){

        PlayerService playerService = new DomainPlayerService();
        LocationService locationService = new DomainLocationService();

        playerService.addNewPlayers(getPlayers(6));
        locationService.addAllLocation(getLocations(10));

        var winnerIdentifierStrategy = new WinnerIdentifierStrategy();
        var schemeStrategy = new SchemeStrategy();

        TournamentInfoImpl tournamentInfoRoundLocal = new TournamentInfoImpl(6, SchemeType.OLYMPIC, "OlympicTournament", 1, beginDate);
        TournamentSettingImpl tournamentSettingRoundLocal = new TournamentSettingImpl(winnerIdentifierStrategy, schemeStrategy, tournamentInfoRoundLocal, timeSetting);

        var scheduleGenerator = new ScheduleGeneratorImpl(playerService, locationService, domainDateService, new OlympicScheme(6));
        var schedule = scheduleGenerator.generateSchedule();

        tournament = new UniversalTournament(tournamentSettingRoundLocal, tournamentInfoRoundLocal, schedule, scheduleGenerator, playerService, locationService, domainDateService);
        Assert.assertEquals(2, tournament.getSchedule().getAllMatches().size());
    }

    private List<Player> getPlayers(int n){
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            var player = new Player("firstName"+i, "secondName" + i, LocalDate.of(1977,1,1).plusDays(i));
            player.setNumber(i + 1);
            players.add(player);
        }
        return players;
    }

    private List<Location> getLocations(int n){
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location("table"+i, "description" + i));
        }
        return locations;
    }
}
