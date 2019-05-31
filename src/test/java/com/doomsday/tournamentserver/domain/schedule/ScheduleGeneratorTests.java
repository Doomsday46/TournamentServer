package com.doomsday.tournamentserver.domain.schedule;

import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.MatchState;
import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.domain.scheme.OlympicScheme;
import com.doomsday.tournamentserver.domain.scheme.RoundScheme;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.domain.service.DateService;
import com.doomsday.tournamentserver.domain.service.LocationService;
import com.doomsday.tournamentserver.domain.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduleGeneratorTests {
    private ScheduleGenerator testSubject;
    @Autowired
    @Qualifier("DomainPlayer")
    private PlayerService playerService;
    @Autowired
    @Qualifier("DomainLocation")
    private LocationService locationService;
    @Autowired
    @Qualifier("DomainDate")
    private DateService dateService;
    private Scheme schemeType;
    private Location loc1;
    private Location loc2;
    private Location loc3;
    @Before
    public void initTest()
    {
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        playerService.addNewPlayer(mock(Player.class));
        loc1 = new Location("1", "");
        loc2 = new Location("2", "");
        loc3 = new Location("3", "");
        locationService.addLocation(loc1);
        locationService.addLocation(loc2);
        locationService.addLocation(loc3);
        dateService.setTimeSetting(LocalDateTime.now(), new TimeSetting(10, 18, 12*60.0));


    }

    //round scheme tests

    @Test()
    public void genRoundGenerateTest()
    {
        schemeType = new RoundScheme(playerService.getAllPlayers().size());
        testSubject = new ScheduleGeneratorImpl(playerService, locationService, dateService,schemeType);
        Schedule schedule =  testSubject.generateSchedule();
        assertEquals(3, schedule.getAllMatches().size());
    }
    @Test()
    public void genRoundUpdateTest()
    {
        schemeType = new RoundScheme(playerService.getAllPlayers().size());
        testSubject = new ScheduleGeneratorImpl(playerService, locationService, dateService, schemeType);
        Schedule schedule =  testSubject.generateSchedule();
        Match match1 = schedule.getMatchesByState(MatchState.NOTPLAYED).get(0);
        Match match2 = schedule.getMatchesByState(MatchState.NOTPLAYED).get(1);
        match1.setPoints(1,0);
        match2.setPoints(1,0);
        match1.setMatchState(MatchState.PLAYED);
        match2.setMatchState(MatchState.PLAYED);
        locationService.freeLocation(loc1);
        schedule = testSubject.updateSchedule(match1, schedule);
        locationService.freeLocation(loc2);
        schedule = testSubject.updateSchedule(match2, schedule);
        assertEquals(5, schedule.getAllMatches().size());
    }
    @Test()
    public void genRoundUpdateLoopTest()
    {
        schemeType = new RoundScheme(playerService.getAllPlayers().size());
        testSubject = new ScheduleGeneratorImpl(playerService, locationService, dateService, schemeType);
        Schedule schedule =  testSubject.generateSchedule();
        while (schedule.getAllMatches().size() < 10) {
            Match match1 = schedule.getMatchesByState(MatchState.NOTPLAYED).get(0);
            match1.setPoints(1, 0);
            match1.setMatchState(MatchState.PLAYED);
            locationService.freeLocation(loc1);
            schedule = testSubject.updateSchedule(match1, schedule);
        }
        assertEquals(10, schedule.getAllMatches().size());
    }

    //olympic scheme tests
    @Test()
    public void genOlympGenerateTest()
    {
        schemeType = new OlympicScheme(playerService.getAllPlayers().size());
        testSubject = new ScheduleGeneratorImpl(playerService, locationService, dateService, schemeType);
        Schedule schedule =  testSubject.generateSchedule();
        assertEquals(3, schedule.getAllMatches().size());
    }
}
