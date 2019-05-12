package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Setting;
import com.doomsday.tournamentserver.service.SettingService;
import com.doomsday.tournamentserver.service.model.view.SettingView;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {
    @Override
    public boolean save(long idUser, SettingView setting) {
        return false;
    }

    @Override
    public boolean update(long idUser, long idSetting, SettingView setting) {
        return false;
    }

    @Override
    public boolean remove(long idUser, SettingView setting) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long id) {
        return false;
    }

    @Override
    public Setting getSetting(long idUser, long id) {
        return null;
    }

    @Override
    public Setting getSetting(long idUser, long idTournament, long idSetting) {
        return null;
    }
}
