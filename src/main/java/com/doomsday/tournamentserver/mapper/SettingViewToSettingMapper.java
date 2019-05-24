package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Setting;
import com.doomsday.tournamentserver.service.model.view.SettingView;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;
@Service
public class SettingViewToSettingMapper implements Mapper<Setting, SettingView> {

    @Override
    public Setting map(SettingView object) {
        var setting = new Setting();

        setting.setCountPlayers(object.getCountPlayers());
        setting.setCountPrizePlace(object.getCountPrizePlace());
        setting.setDurationMatch(LocalTime.of(object.getMathTimeSetting().getDurationMatchHour(),
                                              object.getMathTimeSetting().getDurationMatchMinute(),
                                              object.getMathTimeSetting().getDurationMatchSeconds()));
        setting.setEndDate(object.getTournamentTimeSetting().getEndTournament());
        setting.setEndGameDay(object.getTournamentTimeSetting().getEndGameDayTime());
        setting.setTypeScheme(object.getTypeScheme());
        setting.setStartGameDay(object.getTournamentTimeSetting().getBeginGameDayTime());
        setting.setStartDate(object.getTournamentTimeSetting().getBeginTournament());

        return setting;
    }
}
