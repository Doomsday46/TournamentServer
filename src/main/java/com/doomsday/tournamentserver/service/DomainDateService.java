package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.setting.TimeSetting;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DomainDateService {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TimeSetting timeSettings;

    public DomainDateService()
    {
    }

    public void setTimeSetting(LocalDateTime startDate, TimeSetting timeSettings){
        if (startDate == null) throw new NullPointerException();
        this.startDate = startDate;
        this.timeSettings = timeSettings;
    }

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Integer getAllowedHourStart() {
        return timeSettings.getAllowedHourStart();
    }

    public Integer getAllowedHourEnd() {
        return timeSettings.getAllowedHourEnd();
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
