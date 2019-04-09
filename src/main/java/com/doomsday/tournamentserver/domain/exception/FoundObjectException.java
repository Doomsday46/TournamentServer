package com.doomsday.tournamentserver.domain.exception;

public class FoundObjectException extends NullPointerException{
    public FoundObjectException(String message){
        super(message);
    }

    public FoundObjectException() {
    }
}
