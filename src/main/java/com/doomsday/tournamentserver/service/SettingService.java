package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.db.Entity.Setting;
import com.doomsday.tournamentserver.service.model.view.SettingView;

public interface SettingService {
    boolean save(long idUser, SettingView setting);
    boolean update(long idUser, long idSetting, SettingView setting);
    boolean remove(long idUser, SettingView setting);
    boolean remove(long idUser, long id);
    Setting getSetting(long idUser, long id);
    Setting getSetting(long idUser, long idTournament, long idSetting);
}
