package io.github.wsz82;

class TimeCheck {
    private long checkTimeMillis;
    private long doubleCheckTimeMillis;

    TimeCheck(long checkTimePeriodMillis) {
        setCheckTimeMillis(checkTimePeriodMillis);
    }

    void setCheckTimeMillis(long checkTimePeriodMillis) {
        long time = System.currentTimeMillis();
        this.checkTimeMillis = time + checkTimePeriodMillis;
        this.doubleCheckTimeMillis = time + checkTimePeriodMillis * 2L;
    }

    boolean isTimeBeforeCheck() {
        return System.currentTimeMillis() < checkTimeMillis;
    }

    boolean isTimeBeforeDoubleCheck() {
        return System.currentTimeMillis() < doubleCheckTimeMillis;
    }

    void extendCheckTime(int times) {
        setCheckTimeMillis(checkTimeMillis * times);
    }
}
