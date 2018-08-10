package com.doomsday.tournamentserver.domain.model;

import com.doomsday.tournamentserver.exception.PlayedMatchException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class OneOnOneMatchTests {

    private Player testPlayer1, testPlayer2;
    private Match match;

    @Test(expected = IllegalArgumentException.class)
    public void testInitMatchWithCloneSidesResultNull() throws IllegalArgumentException {
        new OneOnOneMatch(testPlayer1, testPlayer1, new Location("1","2"), LocalDateTime.now());
    }

    @Test(expected = NullPointerException.class)
    public void testInitMatchWithNullParametersResultNull() throws Exception {
        new OneOnOneMatch(null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testInitMatchParametersResultNull() throws Exception {
        new OneOnOneMatch(null,testPlayer2, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testInitMatchParametersWithDateNullResultNull() throws Exception {
        new OneOnOneMatch(testPlayer1, testPlayer2, null, null);
    }

    @Before
    public void initialize()  {
        testPlayer1 = new Player("a", "b", LocalDate.of(1997,3,1));
        testPlayer2 = new Player("c", "b", LocalDate.of(1997,5,18));
        match = new OneOnOneMatch(testPlayer1, testPlayer2, new Location("1", ""), LocalDateTime.now());
    }

    @Test
    public void testGetWinnerResultFirstSidePlayers() {
        match.setPoints(11, 10);
        match.setMatchState(MatchState.PLAYED);
        assertEquals(testPlayer1, match.getWinner());
    }

    @Test
    public void testGetWinnerResultSecondSidePlayers()  {
        match.setPoints(10, 11);
        match.setMatchState(MatchState.PLAYED);
        assertEquals(testPlayer2, match.getWinner());
    }

    @Test
    public void testSetPoints()  {
        match.setPoints(10, 11);
        assertEquals(10, match.getPointsFirstSide());
        assertEquals(11, match.getPointsSecondSide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPointsParameterNegativeNumberResultException() throws IllegalArgumentException {
        match.setPoints(-5, 11);
        assertEquals(0, match.getPointsFirstSide());
        assertEquals(0, match.getPointsSecondSide());
    }

    @Test(expected = PlayedMatchException.class)
    public void testSetPointsWhenPlayedMatchResultExceptionAllMatchPlayed() throws PlayedMatchException {
        match.setPoints(10, 11);
        match.setMatchState(MatchState.PLAYED);
        match.setPoints(8, 5);
        assertEquals(10, match.getPointsFirstSide());
        assertEquals(11, match.getPointsSecondSide());
    }

    @Test
    public void testIsPlayedResultTrue()  {
        match.setPoints(10, 11);
        match.setMatchState(MatchState.PLAYED);
        assertTrue(match.isPlayed());
    }

    @Test
    public void testIsPlayedResultFalse()  {
        match.setPoints(10, 11);
        assertFalse(match.isPlayed());
    }

    @Test
    public void testEquales(){
        try {
            Match match = new OneOnOneMatch(testPlayer1,testPlayer2,mock(Location.class),LocalDateTime.of(1975,1,1,1,1));
            Match match2 = new OneOnOneMatch(testPlayer2,testPlayer1,mock(Location.class),LocalDateTime.of(1975,1,1,1,1));
            assertTrue(match.equals(match2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}