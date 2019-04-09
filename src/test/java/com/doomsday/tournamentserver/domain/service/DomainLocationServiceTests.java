package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.DuplicateFormatFlagsException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DomainLocationServiceTests {
    private LocationService testSubject;


    @Before
    public void testInit()
    {
        testSubject = new DomainLocationService();
    }


    @Test()
    public void test_addLocation_whenAddOneLocation()
    {
        Location testLockMock = mock(Location.class);
        try {
            testSubject.addLocation(testLockMock);
            assertEquals(1, testSubject.getAllLocations().size());
        }
        catch (Exception e) {}
    }
    @Test(expected = DuplicateFormatFlagsException.class)
    public void test_addLocation_whenDuplicateLocation_resultException() throws DuplicateFormatFlagsException
    {
        Location testLockMock = mock(Location.class);
        testSubject.addLocation(testLockMock);
        testSubject.addLocation(testLockMock);
    }
    @Test(expected = DuplicateFormatFlagsException.class)
    public void test_addLocation_whenDuplicateValuesLocation_resultException() throws DuplicateFormatFlagsException
    {
        Location testLoc1 = new Location("1", "");
        Location testLoc2 = new Location("1", "");
        testSubject.addLocation(testLoc1);
        testSubject.addLocation(testLoc2);
    }
    @Test
    public void test_removeLocation()
    {
        Location testMock = mock(Location.class);
        testSubject.addLocation(testMock);
        testSubject.addLocation(mock(Location.class));
        testSubject.removeLocation(testMock);
        assertEquals(1, testSubject.getAllLocations().size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_removeLocation_whenDoesntExistLocation_resultException() throws IllegalArgumentException
    {
        testSubject.addLocation(mock(Location.class));
        Location mock = mock(Location.class);
        testSubject.removeLocation(mock);
    }
    @Test()
    public void test_removeLocationByPlace()
    {
        String testString = "1";
        Location testLoc = new Location(testString, "");
        Location testLocMock1 = mock(Location.class);
        Location testLocMock2 = mock(Location.class);
        when(testLocMock1.getPlace()).thenReturn("2");
        when(testLocMock2.getPlace()).thenReturn("3");
        testSubject.addLocation(testLoc);
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.removeLocationByPlace(testString);
        assertEquals(2, testSubject.getAllLocations().size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_removeLocationByPlace_whenDoesntExistLocation_resultException() throws IllegalArgumentException
    {
        Location testLocMock1 = mock(Location.class);
        Location testLocMock2 = mock(Location.class);
        when(testLocMock1.getPlace()).thenReturn("2");
        when(testLocMock2.getPlace()).thenReturn("3");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.removeLocationByPlace("1");
    }
    @Test()
    public void test_getFreeLocation()
    {
        Location testLocMock1 = mock(Location.class);
        Location testLocMock2 = mock(Location.class);
        when(testLocMock1.isBusy()).thenReturn(false);
        when(testLocMock2.isBusy()).thenReturn(true);
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        assertFalse(testSubject.getFreeLocation().isBusy());

    }
    @Test()
    public void test_getFreeLocation_whenNoFreeLocation_resultNull()
    {
        Location testLocMock1 = mock(Location.class);
        Location testLocMock2 = mock(Location.class);
        when(testLocMock1.isBusy()).thenReturn(true);
        when(testLocMock2.isBusy()).thenReturn(true);
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        assertEquals(null, testSubject.getFreeLocation());
    }
    @Test()
    public void test_getAllFreeLocations()
    {
        Location testLocMock1 = mock(Location.class);
        Location testLocMock2 = mock(Location.class);
        when(testLocMock1.isBusy()).thenReturn(false);
        when(testLocMock2.isBusy()).thenReturn(false);
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        assertEquals(2, testSubject.getAllFreeLocations().size());
    }
    @Test()
    public void test_reserveLocation()
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.reserveLocation(testLocMock1);
        testSubject.reserveLocation(testLocMock2);
        assertEquals(0, testSubject.getAllFreeLocations().size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_reserveLocation_whenInvalidParameter() throws IllegalArgumentException
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.reserveLocation(testLocMock1);
        testSubject.reserveLocation(testLocMock1);
    }
    @Test()
    public void test_reserveLocationByPlace()
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.reserveLocationByPlace("1");
        testSubject.reserveLocationByPlace("2");
        assertEquals(0, testSubject.getAllFreeLocations().size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_reserveLocationByPlace_whenReserveBusyLocation_resultException() throws IllegalArgumentException
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.reserveLocationByPlace("1");
        testSubject.reserveLocationByPlace("1");
        assertEquals(0, testSubject.getAllFreeLocations().size());
    }
    @Test()
    public void test_freeLocation()
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.reserveLocationByPlace("1");
        testSubject.reserveLocationByPlace("2");
        testSubject.freeLocation(testLocMock1);
        assertEquals(1, testSubject.getAllFreeLocations().size());
    }
    @Test(expected = Exception.class)
    public void test_freeLocation_whenLocationNotBusy_resultException()
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.reserveLocationByPlace("1");
        testSubject.reserveLocationByPlace("2");
        testSubject.freeLocation(testLocMock2);
    }
    @Test()
    public void test_freeLocationByPlace()
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.addLocation(testLocMock2);
        testSubject.reserveLocationByPlace("1");
        testSubject.reserveLocationByPlace("2");
        testSubject.freeLocationByPlace("1");
        assertEquals(1, testSubject.getAllFreeLocations().size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_freeLocationByPlace_whenInvalidParameter_resultException() throws IllegalArgumentException
    {
        Location testLocMock1 = new Location("1", "");
        Location testLocMock2 = new Location("2", "");
        testSubject.addLocation(testLocMock1);
        testSubject.reserveLocationByPlace("1");
        testSubject.freeLocationByPlace("2");
    }
}