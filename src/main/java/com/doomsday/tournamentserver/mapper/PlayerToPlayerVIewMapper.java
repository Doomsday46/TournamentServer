package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Player;
import com.doomsday.tournamentserver.service.model.view.PlayerView;
import org.springframework.stereotype.Service;

@Service
public class PlayerToPlayerVIewMapper implements Mapper<PlayerView, Player> {
    @Override
    public PlayerView map(Player object) {
        return new PlayerView(object.getTournament().id, object.getFirstName(), object.getSurname(), object.getAge());
    }
}
