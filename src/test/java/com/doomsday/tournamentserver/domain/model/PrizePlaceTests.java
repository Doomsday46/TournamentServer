package com.doomsday.tournamentserver.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class PrizePlaceTests {

    private PrizePlace prizePlace;
    private Player player;

    @Before
    public void init(){
        player = mock(Player.class);
        prizePlace = new PrizePlaceImpl(player,1);
    }

    @Test
    public void test_init(){
        new PrizePlaceImpl(player,2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_init_whenPrizePlaceNegativeNumber_resultException()throws IllegalArgumentException{
        new PrizePlaceImpl(player,-2);
    }

    @Test(expected = NullPointerException.class)
    public void test_init_whenPlayerIsNull_resultException()throws NullPointerException{
        new PrizePlaceImpl(null,2);
    }

    @Test
    public void test_getPrizePlace(){
        assertEquals(1,prizePlace.getPrizePlace());
    }
    @Test
    public void test_getPlayer(){
        assertEquals(player,prizePlace.getPlayer());
    }
}
