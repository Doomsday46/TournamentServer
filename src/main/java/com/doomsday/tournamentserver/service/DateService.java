package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.setting.TimeSetting;

import java.time.LocalDateTime;

public interface DateService {
    void setTimeSetting(LocalDateTime startDate, TimeSetting timeSettings);
    LocalDateTime getNextDate();
    LocalDateTime getStartDate();
    Integer getAllowedHourStart();
    Integer getAllowedHourEnd();
    LocalDateTime getEndDate();
    void setEndDate(LocalDateTime endDate);
}
