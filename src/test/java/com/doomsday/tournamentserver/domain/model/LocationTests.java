package com.doomsday.tournamentserver.domain.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class LocationTests {

    private Location location;

    @Before
    public void initTestObject(){
        location = new Location("1","1");
    }

    @Test(expected = NullPointerException.class)
    public void test_initLocation_whenPlaceIsNull_resultException() throws NullPointerException {
        Location table = new Location (null, "");
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_initLocation_whenParametersIsEmpty_resultException() throws IllegalArgumentException
    {
        Location table = new Location ("", "");
    }
    @Test(expected = NullPointerException.class)
    public void test_initLocation_whenDescriptionIsNull_resultException() throws NullPointerException
    {
        Location table = new Location ("1", null);
    }
    @Test
    public void test_initLocation_whenDescriptionIsEmpty(){
        new Location("1","");
    }
    @Test(expected = NullPointerException.class)
    public void test_setBusy_whenParameterIsNull_resultException() throws NullPointerException{
        location.setBusy(null);
    }
    @Test
    public void test_setBusy() {
        location.setBusy(true);
    }
    @Test
    public void test_isBusy_whenLocationIsBusy() {
        location.setBusy(true);
        assertTrue(location.isBusy());
    }
    @Test
    public void test_isBusy_whenLocationNotIsBusy() {
        assertFalse(location.isBusy());
    }
    @Test
    public void test_getPlace() {
        assertEquals("1",location.getPlace());
    }
    @Test
    public void test_getDescription() {
        assertEquals("1",location.getDescription());
    }
}
