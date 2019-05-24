package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Setting;
import com.doomsday.tournamentserver.db.repository.SettingRepository;
import com.doomsday.tournamentserver.db.repository.TournamentRepository;
import com.doomsday.tournamentserver.db.repository.UserRepository;
import com.doomsday.tournamentserver.mapper.SettingViewToSettingMapper;
import com.doomsday.tournamentserver.service.SettingService;
import com.doomsday.tournamentserver.service.model.view.SettingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    private UserRepository userRepository;
    private TournamentRepository tournamentRepository;
    private SettingRepository settingRepository;
    private SettingViewToSettingMapper settingViewToSettingMapper;

    @Autowired
    public SettingServiceImpl(UserRepository userRepository, TournamentRepository tournamentRepository, SettingRepository settingRepository, SettingViewToSettingMapper settingViewToSettingMapper) {
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.settingRepository = settingRepository;
        this.settingViewToSettingMapper = settingViewToSettingMapper;
    }


    @Override
    public boolean save(long idUser, SettingView setting) {
        if (idUser <= 0 || setting == null) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var _setting = settingViewToSettingMapper.map(setting);
            if (tournamentRepository.existsById(setting.getIdTournament())) {
                var tournament = tournamentRepository.getOne(setting.getIdTournament());
                _setting.setTournament(tournament);
                tournament.setSetting(_setting);

                settingRepository.saveAndFlush(_setting);
                tournamentRepository.saveAndFlush(tournament);

            } else {
                return  false;
            }
        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    @Override
    public boolean update(long idUser, long idSetting, SettingView setting) {
        if (idUser <= 0 || idSetting <= 0 || setting == null) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var _setting = settingViewToSettingMapper.map(setting);
            if (settingRepository.existsById(idSetting)) {
                var settingDB = settingRepository.findByUser_IdAndTournament_IdAndId(idUser, setting.getIdTournament(), idSetting);

                settingDB.setStartDate(_setting.getStartDate());
                settingDB.setStartGameDay(_setting.getStartGameDay());
                settingDB.setTypeScheme(_setting.getTypeScheme());
                settingDB.setEndGameDay(_setting.getEndGameDay());
                settingDB.setEndDate(_setting.getEndDate());
                settingDB.setDurationMatch(_setting.getDurationMatch());
                settingDB.setCountPrizePlace(_setting.getCountPrizePlace());
                settingDB.setCountPlayers(_setting.getCountPlayers());

                settingRepository.saveAndFlush(settingDB);

            } else {
                return  false;
            }
        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    @Override
    public boolean remove(long idUser, SettingView setting) {
        if (idUser <= 0 || setting == null) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var settingDB = settingRepository.findByUser_IdAndTournament_Id(idUser, setting.getIdTournament());

            if (settingDB == null) return false;

            settingRepository.delete(settingDB);

        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    @Override
    public boolean remove(long idUser, long id) {

        if (idUser <= 0 || id <= 0) throw new IllegalArgumentException("Incorrect parameters");

        try {

             if (settingRepository.existsById(id)) {
                 settingRepository.deleteById(id);
                 return true;
             } else {
                 return false;
             }
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Setting getSetting(long idUser, long id) {
        return settingRepository.findByUser_IdAndId(idUser, id);
    }

    @Override
    public Setting getSetting(long idUser, long idTournament, long idSetting) {
        return settingRepository.findByUser_IdAndIdAndTournament_Id(idUser, idSetting, idTournament);
    }
}
