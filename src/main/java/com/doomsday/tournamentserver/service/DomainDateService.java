package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component(value = "DomainDate")
public class DomainDateService implements DateService{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TimeSetting timeSettings;

    public DomainDateService()
    {
    }
    @Override
    public void setTimeSetting(LocalDateTime startDate, TimeSetting timeSettings){
        if (startDate == null) throw new NullPointerException();
        this.startDate = startDate;
        this.timeSettings = timeSettings;
    }
    @Override
    public LocalDateTime getNextDate()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isBefore(this.startDate))
        {
            currentDate = this.startDate;
        }
        currentDate = currentDate.plusHours(this.timeSettings.getDateHourOffset());
        if (currentDate.getHour() < timeSettings.getAllowedHourStart())
        {
            currentDate = currentDate.withHour(timeSettings.getAllowedHourStart());
            return currentDate;
        }
        if (currentDate.getHour() > timeSettings.getAllowedHourStart())
        {
            currentDate = currentDate.plusDays(1);
            currentDate = currentDate.withHour(timeSettings.getAllowedHourStart());
            return currentDate;
        }
        return currentDate;
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
        this.endDate = endDate;
    }
}
