package com.doomsday.tournamentserver.domain.schedule;

import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.scheme.Scheme;

import java.util.List;

public interface ScheduleGenerator {
    Schedule generateSchedule();

    Schedule updateSchedule(List<Match> matchesList, Schedule existingSchedule);
    Schedule updateSchedule(Match match, Schedule existingSchedule);
    Scheme getScheme();
}
