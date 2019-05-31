package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.service.model.information.PlayerViewInformation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PlayerViewInformationToPlayerDomainMapper implements Mapper<Player, PlayerViewInformation> {

    @Override
    public Player map(PlayerViewInformation object) {

        var player = new Player(object.getFirstName(),object.getSecondName(), convertToLocalDateViaInstant(object.getBirthDay()));

        return player;
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
