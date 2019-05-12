package com.doomsday.tournamentserver.service.model;

import com.test.tournamentDB.service.model.information.PlayerViewInformation;

public class SchemePairPlayers {

    private PlayerViewInformation firstPlayer;
    private PlayerViewInformation nextPlayer;
    private int currentRound;

    public SchemePairPlayers(PlayerViewInformation firstPlayer, PlayerViewInformation nextPlayer, int currentRound) {
        this.firstPlayer = firstPlayer;
        this.nextPlayer = nextPlayer;
        this.currentRound = currentRound;
    }

    public PlayerViewInformation getFirstPlayer() {
        return firstPlayer;
    }

    public PlayerViewInformation getNextPlayer() {
        return nextPlayer;
    }

    public int getCurrentRound() {
        return currentRound;
    }
}
