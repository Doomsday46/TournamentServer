package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.database.Entity.Setting;
import com.doomsday.tournamentserver.database.Entity.Tournament;
import com.doomsday.tournamentserver.database.repository.*;
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.*;
import com.doomsday.tournamentserver.service.TournamentService;
import com.doomsday.tournamentserver.service.model.information.SchemeInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentSaveInformation;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class TournamentServiceImpl implements TournamentService {

    private TournamentRepository tournamentRepository;
    private SettingRepository settingRepository;
    private TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper;
    private TournamentViewToTournamentMapper tournamentViewToTournamentMapper;
    private UserRepository userRepository;



    private TextProgram textProgram;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, SettingRepository settingRepository, TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper, TournamentViewToTournamentMapper tournamentViewToTournamentMapper, UserRepository userRepository) {

        this.tournamentRepository = tournamentRepository;
        this.settingRepository = settingRepository;
        this.tournamentToTournamentInfoMapper = tournamentToTournamentInfoMapper;
        this.tournamentViewToTournamentMapper = tournamentViewToTournamentMapper;
        this.userRepository = userRepository;


        this.textProgram = new TextProgram("textResponse", Locale.ENGLISH);
    }


    @Override
    public TournamentSaveInformation saveTournament(long idUser, String name) {
        if (idUser <= 0 || name.isEmpty()) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var user = userRepository.getOne(idUser);

            if (tournamentRepository.existsByName(name))
                return new TournamentSaveInformation(-1, name, textProgram.getResourceBundle().getString("tournament.save.information.duplicate"));

            var tournament = new Tournament();

            tournament.setName(name);
            tournament.setFinished(false);
            tournament.setUser(user);

            var savedTournament = tournamentRepository.saveAndFlush(tournament);

            return new TournamentSaveInformation(savedTournament.getId(), name, textProgram.getResourceBundle().getString("tournament.save.information.succesfull"));
        } catch (Exception ex) {
            return new TournamentSaveInformation(-1, name, textProgram.getResourceBundle().getString("tournament.save.information.error"));
        }
    }

    @Override
    public long saveTournament(long idUser, TournamentView tournamentView) {
        if (idUser <= 0 || tournamentView == null) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var user = userRepository.getOne(idUser);
            var tournament = tournamentViewToTournamentMapper.map(tournamentView);

            Setting setting;

            if (tournament.getSetting() == null) {
                tournamentRepository.saveAndFlush(tournament);
            } else {
                setting = tournament.getSetting();
                setting.setTournament(tournament);
                setting.setUser(user);
                tournament.setUser(user);
                var indexTournament = tournamentRepository.saveAndFlush(tournament);
                settingRepository.saveAndFlush(setting);
                return indexTournament.getId();
            }

        } catch (Exception ex) {
            return -1;
        }
        return -1;
    }

    @Override
    public boolean updateDataForTournament(long idUser, long idTournament, TournamentView tournamentView) {
        if (idUser <= 0 || idTournament <= 0 || tournamentView == null) throw new IllegalArgumentException("Incorrect parameters");


        try {
            var tournament = tournamentViewToTournamentMapper.map(tournamentView);
            var tournamentDB = tournamentRepository.findByIdAndUser_Id(idTournament, idUser);

            if (!tournamentDB.getFinished()) return false;

            tournamentDB.setName(tournament.getName());
            tournamentDB.setFinished(tournamentView.isFinished());

            if (tournament.getSetting() != null) {
                var settingView = tournamentView.getSettingView();

                tournamentDB.getSetting().setCountPlayers(settingView.getCountPlayers());
                tournamentDB.getSetting().setCountPrizePlace(settingView.getCountPrizePlace());
                tournamentDB.getSetting().setDurationMatch(LocalTime.of(settingView.getMathTimeSetting().getDurationMatchHour(),
                        settingView.getMathTimeSetting().getDurationMatchMinute(),
                        settingView.getMathTimeSetting().getDurationMatchSeconds()));
                tournamentDB.getSetting().setEndDate(settingView.getTournamentTimeSetting().getEndTournament());
                tournamentDB.getSetting().setEndGameDay(settingView.getTournamentTimeSetting().getEndGameDayTime());
                tournamentDB.getSetting().setTypeScheme(settingView.getTypeScheme());
                tournamentDB.getSetting().setStartGameDay(settingView.getTournamentTimeSetting().getBeginGameDayTime());
                tournamentDB.getSetting().setStartDate(settingView.getTournamentTimeSetting().getBeginTournament());
            }

            tournamentRepository.saveAndFlush(tournamentDB);

        } catch (Exception ex) {
            return false;
        }

        return true;
    }


    @Override
    public boolean deleteTournament(long idUser, long idTournament) {
        try {
            if (tournamentRepository.existsById(idTournament)) return false;

            tournamentRepository.deleteById(idTournament);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Tournament getTournament(long idUser, long idTournament) {
        return tournamentRepository.findByIdAndUser_Id(idTournament, idUser);
    }

    @Override
    public TournamentInformation getTournamentInformation(long idUser, long idTournament) {
        return tournamentToTournamentInfoMapper.map(tournamentRepository.findByIdAndUser_Id(idTournament, idUser));
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser) {

        if (idUser <= 0) throw new IllegalArgumentException("Incorrect parameters");

        var tournaments = tournamentRepository.findAllByUser_Id(idUser);

        var tournamentInformations = new ArrayList<TournamentInformation>();

        tournaments.forEach(a -> tournamentInformations.add(tournamentToTournamentInfoMapper.map(a)));

        return tournamentInformations;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament) {
        return null;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament, Date beginInterval, Date endInterval) {
        return null;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament, Date beginInterval) {
        return null;
    }

    @Override
    public SchemeInformation getTournamentSchemeDetails(long idUser, long idTournament) {
        return null;
    }


}
