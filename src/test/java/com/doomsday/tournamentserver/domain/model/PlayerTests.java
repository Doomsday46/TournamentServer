package com.doomsday.tournamentserver.domain.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PlayerTests {

    @Test(expected = NullPointerException.class)
    public void test_initializingClassField() throws NullPointerException{
        Player player = new Player(null, null, null);
    }

    @Test
    public void test_getPatronymicName_resultEmptyString(){
        Player player = new Player("a","b",null, LocalDate.of(1972,2,1));
        assertEquals("",player.getPatronymicName());
    }
    @Test
    public void test_getPatronymicName_resultNoEmptyString() {
        Player player = new Player("a","b", "c", LocalDate.of(1972,2,1));
        assertEquals("c",player.getPatronymicName());
    }
    @Test
    public void test_getAge(){
        Player player = new Player("a","b", "c", LocalDate.of(1986,1,1));
        assertEquals(LocalDate.now().getYear() - LocalDate.of(1986,1,1).getYear(),player.getAge());
    }

}
