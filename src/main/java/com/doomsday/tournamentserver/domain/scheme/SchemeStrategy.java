package com.doomsday.tournamentserver.domain.scheme;

public class SchemeStrategy {

    public SchemeStrategy() {
    }

    public Scheme getScheme(SchemeType schemeType, Integer countPlayers){
        if(schemeType == null || countPlayers == null) throw new NullPointerException();
        if(countPlayers < 2) throw new IllegalArgumentException();
        var SchemeTypeWork = schemeType;
        switch(SchemeTypeWork){
            case OLYMPIC: return new OlympicScheme(countPlayers);
            case ROUND: return new RoundScheme(countPlayers);
        }
        return null;
    }
}
