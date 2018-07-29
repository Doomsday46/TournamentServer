package com.doomsday.tournamentserver.exception;

public class PlayMatchException extends IllegalArgumentException {
    public PlayMatchException(String message){
        super(message);
    }

    public PlayMatchException() {
    }
}
