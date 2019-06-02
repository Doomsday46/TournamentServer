package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.database.Entity.PrizePlace;
import com.doomsday.tournamentserver.service.model.information.PlayerViewInformation;
import com.doomsday.tournamentserver.service.model.information.PrizePlaceInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrizePlaceToPrizePlaceInfoMapper implements Mapper<PrizePlaceInformation, PrizePlace> {

    private PlayerToPlayerInfoMapper playerToPlayerInfoMapper;

    @Autowired
    public PrizePlaceToPrizePlaceInfoMapper(PlayerToPlayerInfoMapper playerToPlayerInfoMapper) {
        this.playerToPlayerInfoMapper = playerToPlayerInfoMapper;
    }

    @Override
    public PrizePlaceInformation map(PrizePlace object) {

        PlayerViewInformation playerViewInformation = null;

        if (object.getPlayer() != null) {
            playerViewInformation = playerToPlayerInfoMapper.map(object.getPlayer());
        }

        return new PrizePlaceInformation(object.getId(), object.getTournament().getId(), object.getNumber(), playerViewInformation);
    }
}
