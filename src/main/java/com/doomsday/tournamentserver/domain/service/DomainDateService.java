package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.setting.TimeSetting;

import java.time.LocalDateTime;

public class DomainDateService implements DateService{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TimeSetting timeSettings;

    public DomainDateService()
    {
    }

    @Override
    public void setTimeSetting(LocalDateTime startDate, TimeSetting timeSettings){
        if (startDate == null || timeSettings == null) throw new NullPointerException();
        this.startDate = startDate;
        this.timeSettings = timeSettings;
    }
    @Override
    public LocalDateTime getNextDate()
    {
        LocalDateTime _currentDate = LocalDateTime.now();
        if (_currentDate.isBefore(this.startDate))
        {
            _currentDate = this.startDate;
        }
        var currentDate = getDate(_currentDate);
        return currentDate;
    }

    private LocalDateTime getDate(LocalDateTime currentDate) {
        currentDate = currentDate.plusMinutes(Math.round(this.timeSettings.getDateMinutesOffset()));
        if (currentDate.getHour() < timeSettings.getAllowedHourStart())
        {
            currentDate = currentDate.withHour(timeSettings.getAllowedHourStart());
            return currentDate;
        }
        if (currentDate.getHour() > timeSettings.getAllowedHourEnd())
        {
            currentDate = currentDate.plusDays(1);
            currentDate = currentDate.withHour(timeSettings.getAllowedHourStart());
            return currentDate;
        }
        return  currentDate;
    }

    @Override
    public LocalDateTime getNextDate(LocalDateTime localDateTime) {
        return getDate(localDateTime);
    }

    @Override
    public TimeSetting getTimeSettings() {
        return timeSettings;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public Integer getAllowedHourStart() {
        return timeSettings.getAllowedHourStart();
    }

    @Override
    public Integer getAllowedHourEnd() {
        return timeSettings.getAllowedHourEnd();
    }

    @Override
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(LocalDateTime endDate) {
       if(endDate == null || startDate == null) throw new NullPointerException();
       if(endDate.isBefore(startDate)) throw new IllegalArgumentException();
       this.endDate = endDate;
    }
}
