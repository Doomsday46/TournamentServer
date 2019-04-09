package com.doomsday.tournamentserver.domain.schedule;

import com.doomsday.tournamentserver.domain.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ScheduleTests {
    private Schedule testSubject;
    private Player testPlayer;
    private Location testLocation;
    private LocalDate testDate;
    private List<Match> testList;

    @Before
    public void initTest()
    {

        testList = new ArrayList<>();

        testList.add(new OneOnOneMatch(mock(Player.class), mock(Player.class), mock(Location.class), LocalDateTime.of(1970, 1,2,1,1)));
        testList.add(new OneOnOneMatch(mock(Player.class), mock(Player.class), mock(Location.class), LocalDateTime.of(1970, 1,3,1,1)));
        testList.add(new OneOnOneMatch(mock(Player.class), mock(Player.class), mock(Location.class), LocalDateTime.of(1970, 1,4,1,1)));
        testList.add(new OneOnOneMatch(mock(Player.class), mock(Player.class), mock(Location.class), LocalDateTime.of(1970, 1,5,1,1)));
        this.testSubject = new ScheduleImpl(testList);
        this.testPlayer = mock(Player.class);
        this.testDate =  LocalDate.of(1970, 1,1);
        this.testLocation = mock(Location.class);
    }

    @Test()
    public void test_getAllMatches()
    {
        assertEquals(4, testSubject.getAllMatches().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_initSchedule_whenDuplicateMatches_resultException() throws IllegalArgumentException
    {
        Match testMatch = mock(OneOnOneMatch.class);
        List<Match> testList2 = new ArrayList<>();
        testList2.add(testMatch);
        testList2.add(testMatch);
        testSubject = new ScheduleImpl(testList2);
    }
    @Test()
    public void test_addMatch()
    {
        testSubject.addMatch(mock(Match.class));
        assertEquals(5, testSubject.getAllMatches().size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_addMatch_whenDuplicateMatches_resultException() throws IllegalArgumentException
    {
        Match testMatch = mock(OneOnOneMatch.class);
        testSubject.addMatch(testMatch);
        testSubject.addMatch(testMatch);
    }
    @Test()
    public void test_getMatchesByState()
    {
        Match testMatch = new OneOnOneMatch(mock(Player.class), mock(Player.class), mock(Location.class), LocalDateTime.of(1970, 1,6,1,1));
        testSubject.addMatch(testMatch);
        testMatch.setMatchState(MatchState.PLAYED);
        assertEquals(1, testSubject.getMatchesByState(MatchState.PLAYED).size());
    }

    @Test(expected = NullPointerException.class)
    public void test_addMatches_withNullParameter_resultNullPointerException()throws NullPointerException{
        testSubject.addMatches(null);
    }

    @Test(expected = Exception.class)
    public void test_addMatches_parameterEmptyList_resultException()throws IllegalArgumentException{
        testSubject.addMatches(new ArrayList<>());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_AddMatches_whenDuplicateListMatches_resultException() throws IllegalArgumentException{
        testSubject.addMatches(testList);
    }

}
