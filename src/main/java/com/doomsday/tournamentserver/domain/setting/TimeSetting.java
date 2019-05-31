package com.doomsday.tournamentserver.domain.setting;

public class TimeSetting {
    private Integer allowedHourStart;
    private Integer allowedHourEnd;
    private Double dateMinutesOffset;


    public TimeSetting()  {
        this.allowedHourStart = 10;
        this.allowedHourEnd = 20;
        this.dateMinutesOffset = 60.0;
    }
    public TimeSetting(Integer allowedHourStart, Integer allowedHourEnd, Double dateMinutesOffset){
        if (allowedHourStart == null || allowedHourEnd == null || dateMinutesOffset == null) throw new NullPointerException();
        if (dateMinutesOffset < 0) throw new IllegalArgumentException("Time offset cannot be below zero");
        if (allowedHourEnd <= allowedHourStart) throw new IllegalArgumentException("End of allowed time cannot be lower or equal to start time");
        if ((allowedHourEnd > 23) || (allowedHourEnd < 0) || (allowedHourStart > 23) || (allowedHourStart < 0))
            throw new IllegalArgumentException("Bad hour values");
        this.allowedHourStart = allowedHourStart;
        this.allowedHourEnd = allowedHourEnd;
        this.dateMinutesOffset = dateMinutesOffset;
    }


    public Integer getAllowedHourStart() {
        return allowedHourStart;
    }

    public Integer getAllowedHourEnd() {
        return allowedHourEnd;
    }

    public Double getDateMinutesOffset() {
        return dateMinutesOffset;
    }
}
