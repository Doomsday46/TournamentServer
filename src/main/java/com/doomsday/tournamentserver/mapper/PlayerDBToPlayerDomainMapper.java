package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.domain.model.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
@Service
public class PlayerDBToPlayerDomainMapper implements Mapper<Player, com.doomsday.tournamentserver.db.Player> {
    @Override
    public Player map(com.doomsday.tournamentserver.db.Player object) {
        var player = new Player(object.getFirstName(),object.getSurname(), convertToLocalDateViaInstant(object.getAge()));

        return player;
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}