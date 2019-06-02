package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.setting.TimeSetting;

import java.time.LocalDateTime;

public interface DateService {
    void setTimeSetting(LocalDateTime startDate, TimeSetting timeSettings);
    LocalDateTime getNextDate();
    LocalDateTime getNextDate(LocalDateTime localDateTime);
    LocalDateTime getStartDate();
    Integer getAllowedHourStart();
    Integer getAllowedHourEnd();
    LocalDateTime getEndDate();
    TimeSetting getTimeSettings();
    void setEndDate(LocalDateTime endDate);
}
