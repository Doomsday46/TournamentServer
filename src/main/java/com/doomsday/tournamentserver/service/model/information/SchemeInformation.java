package com.doomsday.tournamentserver.service.model.information;

import com.doomsday.tournamentserver.service.model.SchemePairPlayers;
import com.doomsday.tournamentserver.service.model.TypeScheme;

import java.util.List;

public class SchemeInformation {

    private long idTournament;
    private TypeScheme typeScheme;
    private int countRounds;
    private List<SchemePairPlayers> schemePairPlayers;

    public SchemeInformation(long idTournament, TypeScheme typeScheme, int countRounds, List<SchemePairPlayers> schemePairPlayers) {
        this.idTournament = idTournament;
        this.typeScheme = typeScheme;
        this.countRounds = countRounds;
        this.schemePairPlayers = schemePairPlayers;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public TypeScheme getTypeScheme() {
        return typeScheme;
    }

    public int getCountRounds() {
        return countRounds;
    }

    public List<SchemePairPlayers> getSchemePairPlayers() {
        return schemePairPlayers;
    }
}
