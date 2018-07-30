package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.exception.FoundObjectException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DomainPlayerService implements PlayerService{
    private Map<Player, Integer> playersNumbersMap;

    public DomainPlayerService()
    {
        this.playersNumbersMap = new HashMap<>();
    }
    @Override
    public void addPlayer(Player player)
    {
        if (player == null) throw new NullPointerException();
        if (playersNumbersMap.containsKey(player)) throw new FoundObjectException("Player already exist in dispatcher");
        for (int i =0; i  < playersNumbersMap.entrySet().size()+1; i++)
        {
            if (!(playersNumbersMap.values().contains(i+1)))
            {playersNumbersMap.put(player, i+1);
                break;}
        }
    }
    @Override
    public void addPlayers(List<Player> playersList)
    {
        if (playersList == null) throw new NullPointerException();
        for (Player player: playersList)
        {
            this.addPlayer(player);
        }

    }
    @Override
    public List<Integer> getAllPlayersNumbers()
    {
        return new ArrayList<Integer>(this.playersNumbersMap.values());
    }
    @Override
    public Integer getPlayerNumber(Player player)
    {
        if (player == null) throw new NullPointerException();
        if (!(playersNumbersMap.containsKey(player))) throw new FoundObjectException("Can't find specified player");
        return playersNumbersMap.get(player);
    }
    @Override
    public List<Player> getAllPlayers()
    {
        return new ArrayList<>(this.playersNumbersMap.keySet());
    }

    @Override
    public Player getPlayerByNumber(Integer playerNumber)
    {
        if (playerNumber == null ) throw new NullPointerException();
        if (!(playersNumbersMap.containsValue(playerNumber))) throw new FoundObjectException("Can't find specified number");
        for (Player player: this.playersNumbersMap.keySet())
        {
            if (this.playersNumbersMap.get(player).equals(playerNumber))
                return player;
        }
        return null;
    }
}
