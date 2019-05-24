package com.doomsday.tournamentserver.mapper;


public interface Mapper<T, R> {
    T map(R object);
}
