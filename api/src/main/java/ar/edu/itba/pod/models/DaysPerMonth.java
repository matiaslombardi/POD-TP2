package ar.edu.itba.pod.models;

public enum DaysPerMonth {
    JANUARY(31),
    FEBRUARY(28),
    MARCH(31),
    APRIL(30),
    MAY(31),
    JUNE(30),
    JULY(31),
    AUGUST(31),
    SEPTEMBER(30),
    OCTOBER(31),
    NOVEMBER(30),
    DECEMBER(31);

    private int days;

    DaysPerMonth(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
