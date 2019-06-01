package com.doomsday.tournamentserver.domain.builder;

import com.doomsday.tournamentserver.domain.schedule.Schedule;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGenerator;
import com.doomsday.tournamentserver.domain.scheme.SchemeStrategy;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.service.*;
import com.doomsday.tournamentserver.domain.tournament.*;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifierStrategy;

import java.time.LocalDateTime;
import java.util.Date;

public class UniversalTournamentBuilder implements TournamentBuilder{

    private PlayerService playerService;
    private LocationService locationService;
    private DateService dateService;

    private ScheduleGenerator scheduleGenerator;
    private Schedule schedule;
    private TournamentSetting tournamentSetting;
    private TournamentInfo tournamentInfo;

    private com.doomsday.tournamentserver.db.Entity.Tournament tournamentDB;



    public UniversalTournamentBuilder(com.doomsday.tournamentserver.db.Entity.Tournament tournamentDB){
        this.tournamentDB = tournamentDB;
    }

    @Override
    public TournamentBuilder setPlayerService(PlayerService playerService) {
        this.playerService = playerService;

        return this;
    }

    @Override
    public TournamentBuilder setLocationService(LocationService locationService) {
        this.locationService = locationService;

        return this;
    }

    @Override
    public TournamentBuilder setDateService(DateService dateService) {
        this.dateService = dateService;
        return this;
    }

    @Override
    public TournamentBuilder initSetting() {

        var winnerIndetifierStrategy = new WinnerIdentifierStrategy();
        var schemeStrategy = new SchemeStrategy();

        var setting = tournamentDB.getSetting();

        var timeSetting  = dateService.getTimeSettings();

        var schemeType = SchemeType.valueOf(setting.getTypeScheme().toString());
        Date startTournamentInfo = setting.getStartDate();
        var beginTournament = LocalDateTime.of(startTournamentInfo.getYear(),
                                                startTournamentInfo.getMonth(),
                                                startTournamentInfo.getDay(),
                                                startTournamentInfo.getHours(),
                                                startTournamentInfo.getMinutes(),
                                                startTournamentInfo.getSeconds());


        tournamentInfo = new TournamentInfoImpl(setting.getCountPlayers(), schemeType, tournamentDB.getName(),
                                                setting.getCountPrizePlace(), beginTournament);

        tournamentSetting = new TournamentSettingImpl(winnerIndetifierStrategy, schemeStrategy, tournamentInfo, timeSetting);

        return this;
    }

    @Override
    public TournamentBuilder setGenerateSchedule(ScheduleGenerator scheduleGenerator) {
        this.scheduleGenerator = scheduleGenerator;
        return this;
    }

    @Override
    public TournamentBuilder setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    @Override
    public Tournament build() {
        return new UniversalTournament(tournamentSetting, tournamentInfo, schedule,
                                       scheduleGenerator, playerService, locationService, dateService);
    }

}
