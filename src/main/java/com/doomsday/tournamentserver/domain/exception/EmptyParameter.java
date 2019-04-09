package com.doomsday.tournamentserver.domain.exception;

public class EmptyParameter extends IllegalArgumentException {

    public EmptyParameter(String message){
        super(message);
    }

    public EmptyParameter() {
    }
}
