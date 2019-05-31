package com.doomsday.tournamentserver.service.model.view;

public class MatchView {

    private long idTournament;
    private long idMatch;
    private int scoreFirstPlayer;
    private int scoreSecondPlayer;
    private boolean isFinished;

    public MatchView() {
    }

    public MatchView(long idTournament, long idMatch, int scoreFirstPlayer, int scoreSecondPlayer, boolean isFinished) {
        this.idTournament = idTournament;
        this.idMatch = idMatch;
        this.scoreFirstPlayer = scoreFirstPlayer;
        this.scoreSecondPlayer = scoreSecondPlayer;
        this.isFinished = isFinished;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public long getIdMatch() {
        return idMatch;
    }

    public int getScoreFirstPlayer() {
        return scoreFirstPlayer;
    }

    public int getScoreSecondPlayer() {
        return scoreSecondPlayer;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
