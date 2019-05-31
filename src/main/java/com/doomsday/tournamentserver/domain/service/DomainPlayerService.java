package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Player;

import java.util.*;


public class DomainPlayerService implements PlayerService{

    private Map<Player, Integer> playersNumbersMap;

    public DomainPlayerService()
    {
        this.playersNumbersMap = new HashMap<>();
    }

    @Override
    public void addNewPlayer(Player player)
    {
        if (player == null) throw new NullPointerException();
        if (playersNumbersMap.containsKey(player)) throw new IllegalArgumentException("Player already exist in dispatcher");

        setNumberToPlayer(player);


    }

    @Override
    public void recoveryPlayer(Player player, Integer number) {
        playersNumbersMap.put(player, number);
    }

    private void setNumberToPlayer(Player player){
        playersNumbersMap.put(player, player.getNumber());
    }

    @Override
    public void addNewPlayers(List<Player> playersList)
    {
        if (playersList == null) throw new NullPointerException();
        for (Player player: playersList)
        {
            this.addNewPlayer(player);
        }

    }

    @Override
    public void recoveryPlayers(Map<Player, Integer> playersNumbersMap) {
        playersNumbersMap.putAll(playersNumbersMap);
    }

    @Override
    public List<Integer> getAllPlayersNumbers()
    {
        List playersNumbersList = new ArrayList<Integer>(this.playersNumbersMap.values());
        playersNumbersList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return playersNumbersList;
    }
    @Override
    public Integer getPlayerNumber(Player player)
    {
        if (player == null) throw new NullPointerException();
        if (!(playersNumbersMap.containsKey(player))) throw new IllegalArgumentException("Can't find specified player");
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
        if (!(playersNumbersMap.containsValue(playerNumber))) throw new IllegalArgumentException("Can't find specified number");
        for (Player player: this.playersNumbersMap.keySet())
        {
            if (this.playersNumbersMap.get(player).equals(playerNumber))
                return player;
        }
        return null;
    }

    @Override
    public Map<Player, Integer> getPlayerAndNumbers() {
        return playersNumbersMap;
    }
}
