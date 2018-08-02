package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.scheme.SchemeType;

import java.time.LocalDateTime;

public class TournamentInfoImpl implements TournamentInfo {

    private SchemeType schemeType;
    private String nameTournament;
    private Integer countPrizePlace;
    private LocalDateTime startDate;
    private Integer countPlayers;

    public TournamentInfoImpl(Integer countPlayers, SchemeType schemeType, String nameTournament, Integer countPrizePlace, LocalDateTime startDate) {
        if(schemeType == null || nameTournament == null || countPrizePlace == null || startDate == null || countPlayers == null) throw new NullPointerException();
        if(nameTournament.isEmpty() || countPrizePlace < 1 || startDate.isAfter(LocalDateTime.now()) || countPlayers < 2) throw new IllegalArgumentException();
        this.countPlayers = countPlayers;
        this.schemeType = schemeType;
        this.nameTournament = nameTournament;
        this.countPrizePlace = countPrizePlace;
        this.startDate = startDate;
    }

    public TournamentInfoImpl() {

    }

    @Override
    public SchemeType getScheme() {
        return schemeType;
    }

    @Override
    public String getTournamentName() {
        return nameTournament;
    }

    @Override
    public Integer getPrizePlacesCount() {
        return countPrizePlace;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public Integer getCountPlayers() {
        return countPlayers;
    }


}
