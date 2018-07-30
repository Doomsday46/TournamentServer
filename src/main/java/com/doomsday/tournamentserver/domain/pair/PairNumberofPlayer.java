package com.doomsday.tournamentserver.domain.pair;

public class PairNumberofPlayer {

    private Integer numberFirstPlayer;
    private Integer numberSecondPlayer;

    public PairNumberofPlayer(Integer numberFirstPlayer, Integer numberSecondPlayer) {
        if(numberFirstPlayer <= 0 || numberSecondPlayer <= 0) throw new IllegalArgumentException();
        this.numberFirstPlayer = numberFirstPlayer;
        this.numberSecondPlayer = numberSecondPlayer;
    }

    public Integer getNumberFirstPlayer() {
        return numberFirstPlayer;
    }

    public Integer getNumberSecondPlayer() {
        return numberSecondPlayer;
    }
}
