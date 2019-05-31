package com.doomsday.tournamentserver.domain.builder;

import com.doomsday.tournamentserver.domain.schedule.Schedule;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGenerator;
import com.doomsday.tournamentserver.domain.service.DateService;
import com.doomsday.tournamentserver.domain.service.LocationService;
import com.doomsday.tournamentserver.domain.service.PlayerService;
import com.doomsday.tournamentserver.domain.tournament.Tournament;

public interface TournamentBuilder {

    TournamentBuilder setPlayerService(PlayerService playerService);
    TournamentBuilder setLocationService(LocationService locationService);
    TournamentBuilder setDateService(DateService dateService);
    TournamentBuilder initSetting();
    TournamentBuilder setGenerateSchedule(ScheduleGenerator scheduleGenerator);
    TournamentBuilder setSchedule(Schedule schedule);
    Tournament build();

}
