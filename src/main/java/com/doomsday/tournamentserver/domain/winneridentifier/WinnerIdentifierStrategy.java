package com.doomsday.tournamentserver.domain.winneridentifier;

import com.doomsday.tournamentserver.domain.scheme.SchemeType;

public class WinnerIdentifierStrategy {

    public WinnerIdentifierStrategy() {
    }

    public WinnerIdentifier getWinnnerIdentifier(SchemeType schemeType){
        if(schemeType == null ) throw new NullPointerException();
        switch(schemeType){
            case OLYMPIC: return new OlympicWinnerIdentifier();
            case ROUND: return new RoundWinnerIdentifier();
        }
        return null;
    }
}
