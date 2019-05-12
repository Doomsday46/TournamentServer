package com.doomsday.tournamentserver.validator;

public interface Validator<T> {
    Validator setModel(T model);
    boolean isValid();
    String message();
}
