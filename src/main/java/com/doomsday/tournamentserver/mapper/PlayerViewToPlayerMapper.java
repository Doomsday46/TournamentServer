package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.database.Entity.Player;
import com.doomsday.tournamentserver.service.model.view.PlayerView;
import org.springframework.stereotype.Service;

@Service
public class PlayerViewToPlayerMapper implements Mapper<Player, PlayerView> {

    @Override
    public Player map(PlayerView object) {
        var player = new Player();

        player.setFirstName(object.getFirstName());
        player.setSurname(object.getSecondName());
        player.setAge(object.getBirthDay());

        return player;
    }
}
