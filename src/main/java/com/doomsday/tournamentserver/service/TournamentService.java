package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.db.Tournament;
import com.doomsday.tournamentserver.service.model.information.SchemeInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentSaveInformation;
import com.doomsday.tournamentserver.service.model.view.TournamentView;

import java.util.Date;
import java.util.List;

public interface TournamentService {
    TournamentSaveInformation saveTournament(long idUser, String name);
    long saveTournament(long idUser, TournamentView tournamentView);
    boolean createTournament(long idUser, long idTournament);
    boolean updateDataForTournament(long idUser, long idTournament, TournamentView tournamentView);
    boolean deleteTournament(long idUser, long idTournament);
    Tournament getTournament(long idUser, long idTournament);
    TournamentInformation getTournamentInformation(long idUser, long idTournament);
    List<TournamentInformation> getTournamentInformation(long idUser);
    List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament);
    List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament, Date beginInterval, Date endInterval);
    List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament, Date beginInterval);
    SchemeInformation getTournamentSchemeDetails(long idUser, long idTournament);
}
