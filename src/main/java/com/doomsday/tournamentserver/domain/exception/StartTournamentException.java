package com.doomsday.tournamentserver.domain.exception;

public class StartTournamentException extends NullPointerException {

    public StartTournamentException(String message){
        super(message);
    }

    public StartTournamentException() {
    }
}