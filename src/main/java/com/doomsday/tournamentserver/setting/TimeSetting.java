package com.doomsday.tournamentserver.setting;

public class TimeSetting {
    private Integer allowedHourStart;
    private Integer allowedHourEnd;
    private Integer dateHourOffset;


    public TimeSetting()  {
        this.allowedHourStart = 10;
        this.allowedHourEnd = 20;
        this.dateHourOffset = 12;
    }
    public TimeSetting(Integer allowedHourStart, Integer allowedHourEnd, Integer dateHourOffset){
        if (allowedHourStart == null || allowedHourEnd == null || dateHourOffset == null) throw new NullPointerException();
        if (dateHourOffset < 0) throw new IllegalArgumentException("Time offset cannot be below zero");
        if (allowedHourEnd <= allowedHourStart) throw new IllegalArgumentException("End of allowed time cannot be lower or equal to start time");
        if ((allowedHourEnd > 23) || (allowedHourEnd < 0) || (allowedHourStart > 23) || (allowedHourStart < 0))
            throw new IllegalArgumentException("Bad hour values");
        this.allowedHourStart = allowedHourStart;
        this.allowedHourEnd = allowedHourEnd;
        this.dateHourOffset = dateHourOffset;
    }


    public Integer getAllowedHourStart() {
        return allowedHourStart;
    }

    public Integer getAllowedHourEnd() {
        return allowedHourEnd;
    }

    public Integer getDateHourOffset() {
        return dateHourOffset;
    }
}
