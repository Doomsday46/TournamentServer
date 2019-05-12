package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.PrizePlace;
import com.doomsday.tournamentserver.service.PrizePlaceService;
import com.doomsday.tournamentserver.service.model.view.PrizePlaceView;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PrizePlaceServiceImpl implements PrizePlaceService {
    @Override
    public boolean update(long idUser, PrizePlaceView prizePlace) {
        return false;
    }

    @Override
    public PrizePlace getPrizePlace(long idUser, long id) {
        return null;
    }

    @Override
    public PrizePlace getPrizePlace(long idUser, long idTournament, long number) {
        return null;
    }

    @Override
    public List<PrizePlace> getPrizePlaces(long idUser, long idTournament) {
        return null;
    }

    @Override
    public List<PrizePlace> getPrizePlaces(long idUser, long idTournament, boolean state) {
        return null;
    }
}
