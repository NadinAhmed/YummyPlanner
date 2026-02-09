package com.nadin.yummy_planner.presentation.planner.model;

public class Day {
    private String dayOfWeek;
    private String dayOfMonth;
    private long date;

    public Day(String dayOfWeek, String dayOfMonth, long date) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public long getDate() {
        return date;
    }
}
