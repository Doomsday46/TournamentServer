package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Player;

import java.util.List;

public interface PlayerService {

    void addPlayer(Player player);
    void addPlayers(List<Player> playersList);
    List<Player> getAllPlayers();
    List<Integer> getAllPlayersNumbers();
    Integer getPlayerNumber(Player player);
    Player getPlayerByNumber(Integer playerNumber);

}
