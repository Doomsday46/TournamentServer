package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public interface DateService {
    void setTimeSetting(LocalDateTime startDate, TimeSetting timeSettings);
    LocalDateTime getNextDate();
    LocalDateTime getStartDate();
    Integer getAllowedHourStart();
    Integer getAllowedHourEnd();
    LocalDateTime getEndDate();
    void setEndDate(LocalDateTime endDate);
}
