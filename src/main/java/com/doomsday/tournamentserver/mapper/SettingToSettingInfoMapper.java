package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.database.Entity.Setting;
import com.doomsday.tournamentserver.service.model.TournamentTimeSetting;
import com.doomsday.tournamentserver.service.model.TypeScheme;
import com.doomsday.tournamentserver.service.model.information.SettingInformation;
import org.springframework.stereotype.Service;

@Service
public class SettingToSettingInfoMapper implements Mapper<SettingInformation, Setting> {
    @Override
    public SettingInformation map(Setting object) {

        if (!object.getClass().equals(Setting.class)) throw new IllegalArgumentException("incorrect model for mapping");

        var setting = (Setting) object;

        var idTournament = setting.getTournament().getId();
        var idSetting = setting.getId();
        var typeScheme = TypeScheme.valueOf(setting.getTypeScheme());
        var countPlayer = setting.getCountPlayers();
        var countPrizePlace = setting.getCountPrizePlace();
        var startTournament = setting.getStartDate();
        var endTournament = setting.getEndDate();
        var durationMatch = setting.getDurationMatch();
        var startGameDay = setting.getStartGameDay();
        var endGameDay = setting.getEndGameDay();

        var tournamentTimeSetting = new TournamentTimeSetting(startTournament, endTournament, startGameDay, endGameDay);

        return new SettingInformation(idTournament, idSetting, typeScheme, countPlayer, durationMatch, countPrizePlace, tournamentTimeSetting);
    }


}
