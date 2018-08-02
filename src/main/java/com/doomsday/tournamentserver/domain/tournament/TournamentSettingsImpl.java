package com.doomsday.tournamentserver.domain.tournament;

import com.doomsday.tournamentserver.domain.scheme.OlympicScheme;
import com.doomsday.tournamentserver.domain.scheme.RoundScheme;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.domain.winneridentifier.OlympicWinnerIdentifier;
import com.doomsday.tournamentserver.domain.winneridentifier.RoundWinnerIdentifier;
import com.doomsday.tournamentserver.domain.winneridentifier.WinnerIdentifier;
import com.doomsday.tournamentserver.exception.EmptyParameter;
import com.doomsday.tournamentserver.service.DateService;

import java.time.LocalDateTime;

public class TournamentSettingsImpl implements TournamentSetting {
    private SchemeType schemeType;
    private LocalDateTime startDate;
    private TimeSetting timeSettings;
    private String tournamentName;


    private Integer prizePlacesCount;

    public TournamentSettingsImpl(String tournamentName, SchemeType schemeType, LocalDateTime startDate, TimeSetting timeSettings)
    {
        if (schemeType == null || startDate == null || timeSettings == null) throw new NullPointerException();
        if (tournamentName.isEmpty()) throw new EmptyParameter("Empty tournament name");
        if (startDate.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Can't set the past date and time");
        this.schemeType = schemeType;
        this.startDate = startDate;
        this.timeSettings = timeSettings;
        this.tournamentName = tournamentName;
        if (schemeType == SchemeType.ROUND) {
            this.prizePlacesCount = 3;
        }
        if (schemeType == SchemeType.OLYMPIC) {
            this.prizePlacesCount = 1;
        }
    }

    public void setPrizePlacesCount(Integer prizePlacesCount) {
        this.prizePlacesCount = prizePlacesCount;
    }
    @Override
    public DateService getDateDispatcher()  {
        return null;
    }

    @Override
    public Scheme getScheme(Integer playersCount){

        if (schemeType == SchemeType.ROUND) {
            return new RoundScheme(playersCount);
        }
        if (schemeType == SchemeType.OLYMPIC) {
            return new OlympicScheme(playersCount);
        }
        return null;
    }

    @Override
    public WinnerIdentifier getWinnerIdentifier() {
        if (schemeType == SchemeType.ROUND) {
            return new RoundWinnerIdentifier();
        }
        if (schemeType == SchemeType.OLYMPIC) {
            return new OlympicWinnerIdentifier();
        }
        return null;
    }

    public SchemeType getSchemeType() {
        return schemeType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public TimeSetting getTimeSettings() {
        return timeSettings;
    }

    @Override
    public String getTournamentName() {
        return this.tournamentName;
    }

    @Override
    public Integer getPrizePlacesCount() {
        return prizePlacesCount;
    }
}
