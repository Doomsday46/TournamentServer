package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.database.Entity.Player;
import com.doomsday.tournamentserver.service.model.information.PlayerViewInformation;
import org.springframework.stereotype.Service;

@Service
public class PlayerToPlayerInfoMapper implements Mapper<PlayerViewInformation, Player> {

    @Override
    public PlayerViewInformation map(Player object){
        if (!object.getClass().equals(Player.class)) throw new IllegalArgumentException("incorrect model for mapping");

        var player = (Player) object;

        return new PlayerViewInformation(player.getId(), player.getId(), player.getFirstName(), player.getSurname(), player.getAge(), player.getNumber());
    }
}