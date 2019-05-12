package com.doomsday.tournamentserver.exception;

public class UncorrectModelException extends IllegalArgumentException {

    public UncorrectModelException(String message){
        super(message);
    }

    public UncorrectModelException() {
    }
}
