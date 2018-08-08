package com.doomsday.tournamentserver.domain.scheme;

import com.doomsday.tournamentserver.domain.pair.Pair;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class RoundSchemeTests {
    private RoundScheme testSubject;
    private Integer playerCount;

    @Before
    public void testInit()
    {
        playerCount = 5;
        testSubject = new RoundScheme(playerCount);
    }

    @Test()
    public void schemeBuildTest()
    {
        assertEquals((playerCount*(playerCount-1)/2), testSubject.getAllPairs().size());
    }
    @Test()
    public void schemeGetTourTest()
    {
        assertEquals(playerCount-1, testSubject.getAllPairsInTour(0).size());
    }
    @Test()
    public void schemeGetUnplayedTest()
    {
        Pair<Integer, Integer> pair1 = testSubject.getNextNotPlayedPair();
        assertFalse(testSubject.getNextNotPlayedPair().equals(pair1));
    }
    @Test()
    public void schemeGerLoopTest()
    {
        while (true)
        {
            Pair<Integer, Integer> pair = testSubject.getNextNotPlayedPair();
            if (pair == null) { break;}

        }
    }
}