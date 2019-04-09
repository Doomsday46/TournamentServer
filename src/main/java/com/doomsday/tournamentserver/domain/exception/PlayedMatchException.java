package com.doomsday.tournamentserver.domain.exception;

public class PlayedMatchException extends IllegalArgumentException {
    public PlayedMatchException(String message){
        super(message);
    }

    public PlayedMatchException() {
    }
}
