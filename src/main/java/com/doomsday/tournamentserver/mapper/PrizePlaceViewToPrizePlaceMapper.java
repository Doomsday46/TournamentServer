package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.database.Entity.PrizePlace;
import com.doomsday.tournamentserver.service.model.view.PrizePlaceView;
import org.springframework.stereotype.Service;

@Service
public class PrizePlaceViewToPrizePlaceMapper implements Mapper<PrizePlace, PrizePlaceView> {
    @Override
    public PrizePlace map(PrizePlaceView object) {
        var prizePlace = new PrizePlace();

        prizePlace.setNumber(object.getPrizePlaceNumber());

        return prizePlace;
    }
}
