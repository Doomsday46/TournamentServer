package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DomainDateServiceTests {
    private DateService testSubject;

    @Test(expected = NullPointerException.class)
    public void test_setTimeSetting_whenNullParameters_resultException() throws NullPointerException
    {
        new DomainDateService().setTimeSetting(null,null);
    }
    @Test(expected = NullPointerException.class)
    public void test_setTimeSetting_whenFirstNullParameter_resultException() throws NullPointerException
    {
        new DomainDateService().setTimeSetting(null,mock(TimeSetting.class));
    }
    @Test(expected = NullPointerException.class)
    public void test_setTimeSetting_whenSecondNullParameter_resultException() throws NullPointerException
    {
        new DomainDateService().setTimeSetting(LocalDateTime.now(),null);
    }
    @Test()
    public void test_getNextDate()
    {
        testSubject = new DomainDateService();
        testSubject.setTimeSetting(LocalDateTime.now(), new TimeSetting(10, 18, 12));
        System.out.print(LocalDateTime.now());
        System.out.print('\n');
        System.out.print(testSubject.getNextDate());
        assertEquals(true, testSubject.getNextDate().isAfter(LocalDateTime.now()));
    }
    @Test()
    public void test_getStartDate()
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        testSubject.setTimeSetting(startDate, new TimeSetting(10, 18, 12));
        assertEquals(startDate, testSubject.getStartDate());
    }
    @Test()
    public void test_getAllowedHourStart()
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        Integer allowedHourStart = 10;
        Integer allowedHourEnd = 18;
        Integer allowedHourOffset = 12;
        testSubject.setTimeSetting(startDate, new TimeSetting(allowedHourStart, allowedHourEnd, allowedHourOffset));
        assertEquals(allowedHourStart, testSubject.getAllowedHourStart());
    }
    @Test()
    public void test_getAllowedHourEnd()
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        Integer allowedHourStart = 10;
        Integer allowedHourEnd = 18;
        Integer allowedHourOffset = 12;
        testSubject.setTimeSetting(startDate, new TimeSetting(allowedHourStart, allowedHourEnd, allowedHourOffset));
        assertEquals(allowedHourEnd, testSubject.getAllowedHourEnd());
    }
    @Test()
    public void test_setEndDate()
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        Integer allowedHourStart = 10;
        Integer allowedHourEnd = 18;
        Integer allowedHourOffset = 12;
        testSubject.setTimeSetting(startDate, new TimeSetting(allowedHourStart, allowedHourEnd, allowedHourOffset));
        LocalDateTime endDate = LocalDateTime.of(2020,5,10,12,0);
        testSubject.setEndDate(endDate);
    }
    @Test(expected = NullPointerException.class)
    public void test_setEndDate_whenNullParameter_resultException() throws NullPointerException
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        Integer allowedHourStart = 10;
        Integer allowedHourEnd = 18;
        Integer allowedHourOffset = 12;
        testSubject.setTimeSetting(startDate, new TimeSetting(allowedHourStart, allowedHourEnd, allowedHourOffset));
        LocalDateTime endDate = LocalDateTime.of(2020,5,10,12,0);
        testSubject.setEndDate(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_setEndDate_whenInvalidParameter_resultException() throws IllegalArgumentException
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        Integer allowedHourStart = 10;
        Integer allowedHourEnd = 18;
        Integer allowedHourOffset = 12;
        testSubject.setTimeSetting(startDate, new TimeSetting(allowedHourStart, allowedHourEnd, allowedHourOffset));
        LocalDateTime endDate = LocalDateTime.of(2002,5,10,12,0);
        testSubject.setEndDate(endDate);
    }
    @Test()
    public void test_getEndDate()
    {
        testSubject = new DomainDateService();
        LocalDateTime startDate = LocalDateTime.now();
        Integer allowedHourStart = 10;
        Integer allowedHourEnd = 18;
        Integer allowedHourOffset = 12;
        LocalDateTime endDate = LocalDateTime.of(2020,5,10,12,0);
        testSubject.setTimeSetting(startDate, new TimeSetting(allowedHourStart, allowedHourEnd, allowedHourOffset));
        testSubject.setEndDate(endDate);
        assertEquals(endDate, testSubject.getEndDate());
    }

}
