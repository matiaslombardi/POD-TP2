package ar.edu.itba.pod.models;

public enum DaysPerMonth {
    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    private final int days;
    private final int index;

    DaysPerMonth(int index, int days) {
        this.index = index;
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public int getIndex() {
        return index;
    }
}
