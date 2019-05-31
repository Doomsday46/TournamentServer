package com.doomsday.tournamentserver.domain.builder;

import com.doomsday.tournamentserver.domain.schedule.Schedule;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGenerator;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.service.DomainDateService;
import com.doomsday.tournamentserver.domain.service.DomainLocationService;
import com.doomsday.tournamentserver.domain.service.DomainPlayerService;
import com.doomsday.tournamentserver.domain.tournament.Tournament;
import com.doomsday.tournamentserver.domain.tournament.TournamentInfo;
import com.doomsday.tournamentserver.domain.tournament.TournamentSetting;
import com.doomsday.tournamentserver.domain.tournament.UniversalTournament;
import com.doomsday.tournamentserver.service.LocationService;
import com.doomsday.tournamentserver.service.PlayerService;
import com.doomsday.tournamentserver.service.TournamentService;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class UniversalTournamentBuilder implements TournamentBuilder{

    private DomainPlayerService playerService;
    private DomainLocationService locationService;
    private DomainDateService dateService;

    private ScheduleGenerator scheduleGenerator;
    private Schedule schedule;
    private Scheme scheme;
    private TournamentSetting tournamentSetting;
    private TournamentInfo tournamentInfo;


    private TournamentInformation tournamentInformation;

    public UniversalTournamentBuilder(TournamentInformation tournamentInformation){
        this.tournamentInformation = tournamentInformation;
    }

    @Override
    public TournamentBuilder setPlayers() {
        var players = tournamentInformation.getPlayers();

        return this;
    }

    @Override
    public TournamentBuilder setLocations() {
        return this;
    }

    @Override
    public TournamentBuilder setSetting() {
        return this;
    }

    @Override
    public TournamentBuilder generateSchedule() {
        return this;
    }

    @Override
    public TournamentBuilder generateScheme() {
        return this;
    }

    @Override
    public Tournament build() {
        return new UniversalTournament(tournamentSetting, tournamentInfo, schedule,
                                       scheduleGenerator, playerService, locationService, dateService);
    }
}
