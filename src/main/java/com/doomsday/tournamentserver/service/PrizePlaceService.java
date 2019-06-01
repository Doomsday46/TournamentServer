package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.db.Entity.PrizePlace;
import com.doomsday.tournamentserver.service.model.view.PrizePlaceView;

import java.util.List;

public interface PrizePlaceService {

    boolean update(long idUser, PrizePlaceView prizePlace);
    PrizePlace getPrizePlace(long idUser, long id);
    PrizePlace getPrizePlace(long idUser, long idTournament, int number);
    List<PrizePlace> getPrizePlaces(long idUser, long idTournament);
    List<PrizePlace> getPrizePlaces(long idUser, long idTournament, boolean state);
}
