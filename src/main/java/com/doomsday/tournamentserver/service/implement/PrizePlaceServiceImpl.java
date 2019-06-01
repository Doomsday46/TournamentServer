package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Entity.PrizePlace;
import com.doomsday.tournamentserver.db.repository.PlayerRepository;
import com.doomsday.tournamentserver.db.repository.PrizePlaceRepository;
import com.doomsday.tournamentserver.mapper.PrizePlaceViewToPrizePlaceMapper;
import com.doomsday.tournamentserver.service.PrizePlaceService;
import com.doomsday.tournamentserver.service.model.view.PrizePlaceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PrizePlaceServiceImpl implements PrizePlaceService {

    private PrizePlaceRepository prizePlaceRepository;
    private PrizePlaceViewToPrizePlaceMapper prizePlaceViewToPrizePlaceMapper;
    private PlayerRepository playerRepository;

    @Autowired
    public PrizePlaceServiceImpl(PrizePlaceRepository prizePlaceRepository, PrizePlaceViewToPrizePlaceMapper prizePlaceViewToPrizePlaceMapper, PlayerRepository playerRepository) {
        this.prizePlaceRepository = prizePlaceRepository;
        this.prizePlaceViewToPrizePlaceMapper = prizePlaceViewToPrizePlaceMapper;
        this.playerRepository = playerRepository;
    }


    @Override
    public boolean update(long idUser, PrizePlaceView prizePlace) {
        if (idUser <= 0 || prizePlace == null) throw  new NullPointerException("Incorrect parameters");

        try {
            var _prizePlace = prizePlaceRepository.findByTournament_IdAndUser_IdAndNumber(prizePlace.getIdTournament(), idUser, prizePlace.getPrizePlaceNumber());
            var player = playerRepository.getOne(prizePlace.getIdPlayer());
            _prizePlace.setPlayer(player);
            prizePlaceRepository.saveAndFlush(_prizePlace);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public PrizePlace getPrizePlace(long idUser, long id) {
        return prizePlaceRepository.findByUser_IdAndId(idUser, id);
    }

    @Override
    public PrizePlace getPrizePlace(long idUser, long idTournament, int number) {
        return prizePlaceRepository.findByTournament_IdAndUser_IdAndNumber(idTournament, idUser,  number);
    }

    @Override
    public List<PrizePlace> getPrizePlaces(long idUser, long idTournament) {
        return prizePlaceRepository.findAllByUser_IdAndTournament_Id(idUser, idTournament);
    }

    @Override
    public List<PrizePlace> getPrizePlaces(long idUser, long idTournament, boolean state) {
        return prizePlaceRepository.findAllByUser_IdAndTournament_IdAndTournament_Finished(idUser, idTournament, state);
    }
}
