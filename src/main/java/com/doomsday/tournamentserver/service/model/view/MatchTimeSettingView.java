package com.doomsday.tournamentserver.service.model.view;

public class MatchTimeSettingView {

    private int durationMatchHour;
    private int durationMatchMinute;
    private int durationMatchSeconds;

    public MatchTimeSettingView() {
    }

    public MatchTimeSettingView(int durationMatchHour, int durationMatchMinute, int durationMatchSeconds) {
        this.durationMatchHour = durationMatchHour;
        this.durationMatchMinute = durationMatchMinute;
        this.durationMatchSeconds = durationMatchSeconds;
    }

    public int getDurationMatchHour() {
        return durationMatchHour;
    }

    public int getDurationMatchMinute() {
        return durationMatchMinute;
    }

    public int getDurationMatchSeconds() {
        return durationMatchSeconds;
    }
}
