package com.doomsday.tournamentserver.exception;

public class EmptyParameter extends IllegalArgumentException {

    public EmptyParameter(String message){
        super(message);
    }

    public EmptyParameter() {
    }
}
