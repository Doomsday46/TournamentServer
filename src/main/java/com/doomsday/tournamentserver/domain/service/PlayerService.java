package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Player;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    void addNewPlayer(Player player);
    void recoveryPlayer(Player player, Integer number);
    void addNewPlayers(List<Player> playersList);
    void recoveryPlayers(Map<Player, Integer> playersNumbersMap);
    List<Player> getAllPlayers();
    List<Integer> getAllPlayersNumbers();
    Integer getPlayerNumber(Player player);
    Player getPlayerByNumber(Integer playerNumber);
    Map<Player, Integer> getPlayerAndNumbers();

}
