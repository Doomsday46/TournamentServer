package com.doomsday.tournamentserver.exception;

public class FoundObjectException extends NullPointerException{
    public FoundObjectException(String message){
        super(message);
    }

    public FoundObjectException() {
    }
}
