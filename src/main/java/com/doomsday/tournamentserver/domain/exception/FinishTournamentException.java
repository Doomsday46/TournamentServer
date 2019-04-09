package com.doomsday.tournamentserver.domain.exception;

public class FinishTournamentException extends NullPointerException {
    public FinishTournamentException(String message){
        super(message);
    }

    public FinishTournamentException() {
    }
}
