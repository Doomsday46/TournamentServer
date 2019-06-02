package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.database.Entity.Tournament;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentViewToTournamentMapper implements Mapper<Tournament, TournamentView> {

    private SettingViewToSettingMapper settingViewToSettingMapper;

    @Autowired
    public TournamentViewToTournamentMapper(SettingViewToSettingMapper settingViewToSettingMapper) {
        this.settingViewToSettingMapper = settingViewToSettingMapper;
    }

    @Override
    public Tournament map(TournamentView object) {

        var tournament = new Tournament();

        tournament.setName(object.getNameTournament());
        tournament.setFinished(object.isFinished());
        tournament.setStarted(object.isStarted());

        if (object.getSettingView() == null) {
            return tournament;
        } else {
            tournament.setSetting(settingViewToSettingMapper.map(object.getSettingView()));
        }

        return tournament;
    }
}
