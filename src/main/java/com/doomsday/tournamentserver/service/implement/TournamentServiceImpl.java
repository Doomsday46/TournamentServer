package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Tournament;
import com.doomsday.tournamentserver.service.TournamentService;
import com.doomsday.tournamentserver.service.model.information.SchemeInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentSaveInformation;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TournamentServiceImpl implements TournamentService {
    @Override
    public TournamentSaveInformation saveTournament(long idUser, String name) {
        return null;
    }

    @Override
    public long saveTournament(long idUser, TournamentView tournamentView) {
        return 0;
    }

    @Override
    public boolean createTournament(long idUser, long idTournament) {
        return false;
    }

    @Override
    public boolean updateDataForTournament(long idUser, long idTournament, TournamentView tournamentView) {
        return false;
    }

    @Override
    public boolean updateDataForTournament(long idUser, String name) {
        return false;
    }

    @Override
    public boolean deleteTournament(long idUser, long idTournament) {
        return false;
    }

    @Override
    public Tournament getTournament(long idUser, long idTournament) {
        return null;
    }

    @Override
    public TournamentInformation getTournamentInformation(long idUser, long idTournament) {
        return null;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser) {
        return null;
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
