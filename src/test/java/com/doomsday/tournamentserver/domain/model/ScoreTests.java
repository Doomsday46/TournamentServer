package com.doomsday.tournamentserver.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTests {

    private Score testScoreObjectStandard;
    private Score testScoreObjectCustomer;

    @Before
    public void init(){
        testScoreObjectStandard = new Score();
        testScoreObjectCustomer = new Score(10,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_init_whenFirstSideHaveNegativeScore_resultException() throws IllegalArgumentException{
        new Score(-10,20);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_init_whenSecondSideHaveNegativeScore_resultException() throws IllegalArgumentException{
        new Score(10,-20);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_init_whenNegativeScores_resultException() throws IllegalArgumentException{
        new Score(-10,-20);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_setPoints_whenFirstSideHaveNegativeScore_resultException() throws IllegalArgumentException{
        testScoreObjectStandard.setPoints(-10,20);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_setPoints_whenSecondSideHaveNegativeScore_resultException() throws IllegalArgumentException{
        testScoreObjectStandard.setPoints(10,-20);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_setPoints_whenNegativeScores_resultException() throws IllegalArgumentException{
        testScoreObjectStandard.setPoints(-10,-20);
    }
    @Test
    public void test_setPoints(){
        testScoreObjectStandard.setPoints(10,20);
    }
    @Test
    public void test_getPointsFirstSide_whenObjectStandard(){
        assertEquals(0, testScoreObjectStandard.getPointsFirstSide());
    }
    @Test
    public void test_getPointsSecondSide_whenObjectStandard(){
        assertEquals(0, testScoreObjectStandard.getPointsSecondSide());
    }
    @Test
    public void test_getPointsFirstSide_whenObjectCustomer(){
        assertEquals(10, testScoreObjectCustomer.getPointsFirstSide());
    }
    @Test
    public void test_getPointsSecondSide_whenObjectCustomer(){
        assertEquals(20, testScoreObjectCustomer.getPointsSecondSide());
    }

}
