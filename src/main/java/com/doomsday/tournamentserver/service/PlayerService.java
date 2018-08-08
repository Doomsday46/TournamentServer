package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.domain.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PlayerService {

    void addPlayer(Player player);
    void addPlayers(List<Player> playersList);
    List<Player> getAllPlayers();
    List<Integer> getAllPlayersNumbers();
    Integer getPlayerNumber(Player player);
    Player getPlayerByNumber(Integer playerNumber);

}
