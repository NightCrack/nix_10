package ua.com.alevel.entity;

import ua.com.alevel.customexception.WrongInputException;

public class Date implements Comparable {
    public static final long SEC = 1000;
    public static final long MIN = 60 * SEC;
    public static final long HOUR = 60 * MIN;
    public static final long DAY = 24 * HOUR;
    public static final long[] INTERCALARY_MONTHS = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final long[] ORDINARY_MONTHS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int timeZone = 0;
    private long value;

    public Date(String timeZone, long value) {
        this.timeZone = Integer.parseInt(timeZone);
        this.value = value;
    }

    public Date(String timeZone) {
        this.timeZone = Integer.parseInt(timeZone);
        this.value = System.currentTimeMillis() + evaluateYear(1969) + this.timeZone * HOUR;
    }

    public Date() {
        this.value = System.currentTimeMillis() + evaluateYear(1969) + this.timeZone * HOUR;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) throws WrongInputException {
        try {
            this.value -= this.timeZone * HOUR;
            this.timeZone = Integer.parseInt(timeZone);
            this.value += this.timeZone * HOUR;
        } catch (Exception exception) {
            throw new WrongInputException();
        }

    }

    public int getMil() {
        long processedTime = this.value;
        int returnValue = (int) (processedTime % SEC);
        return returnValue;
    }

    public void setMil(int mil) {
        this.value = this.value + (mil - this.getMil());
    }

    public int getSec() {
        long processedTime = this.value;
        int returnValue = (int) ((processedTime % MIN) / SEC);
        return returnValue;
    }

    public void setSec(int sec) {
        this.value = this.value + (sec - this.getSec()) * SEC;
    }

    public int getMin() {
        long processedTime = this.value;
        int returnValue = (int) ((processedTime % HOUR) / MIN);
        return returnValue;
    }

    public void setMin(int min) {
        this.value = this.value + (min - this.getMin()) * MIN;
    }

    public int getHour() {
        long processedTime = this.value;
        int returnValue = (int) ((processedTime % DAY) / HOUR);
        return returnValue;
    }

    public void setHour(int hour) {
        this.value = this.value + (hour - this.getHour()) * HOUR;
    }

    public int getDay() {
        long processedTime = this.value;
        processedTime -= evaluateYear(this.getYear());
        int months = this.getMonth();
        if (months > 0) {
            processedTime = processedTime - countDays((this.getMonth()), isIntercalary(this.getYear() % 4)) * DAY;
        }
        int day = (int) (processedTime / DAY);
        return day;
    }

    public void setDay(int day) {
        this.value = this.value + (day - this.getDay()) * DAY;
    }

    public int getMonth() {
        long processedTime = value;
        int year = this.getYear();
        int isIntercalary = year % 4;
        processedTime -= evaluateYear(year);
        int days = (int) (processedTime / DAY);
        int month = 0;
        while (days >= yearType(isIntercalary(isIntercalary))[month]) {
            month++;
            days -= yearType(isIntercalary(isIntercalary))[month - 1];
        }
        return month;
    }

    public void setMonth(int month) {
        this.value = this.value + (countDays(month, isIntercalary(this.getYear() % 4)) -
                countDays(this.getMonth(), isIntercalary(this.getYear() % 4))) * DAY;
    }

    public int getYear() {
        long processedTime = value;
        int intercalaryCounter = 0;
        int year = 0;
        for (; processedTime > 0; ) {
            processedTime -= daysInYear(isIntercalary(intercalaryCounter));
            intercalaryCounter = (intercalaryCounter + 1) % 4;
            if (processedTime >= 0) {
                year++;
            }
        }
        return year;
    }

    public void setYear(int year) {
        this.value = this.value - evaluateYear(this.getYear()) + evaluateYear(year);
    }

    private long evaluateYear(int year) {
        int intercalaryYears = (year / 4);
        int ordinaryYears = year - intercalaryYears;
        long returnValue = intercalaryYears * daysInYear(true) + ordinaryYears * daysInYear(false);
        return returnValue;
    }

    private boolean isIntercalary(int yearCount) {
        boolean returnValue;
        switch (yearCount) {
            case 3 -> returnValue = true;
            default -> returnValue = false;
        }
        return returnValue;
    }

    private long[] yearType(boolean isIntercalary) {
        if (isIntercalary) {
            return INTERCALARY_MONTHS;
        }
        return ORDINARY_MONTHS;
    }

    private int countDays(int monthNumber, boolean isIntercalary) {
        int returnValue = 0;
        for (int index = 0; index < monthNumber; index++) {
            returnValue += yearType(isIntercalary)[index];
        }
        return returnValue;
    }

    private long daysInYear(boolean isIntercalary) {
        long year = 0;
        for (int index = 0; index < yearType(isIntercalary).length; index++) {
            year += yearType(isIntercalary)[index] * DAY;
        }
        return year;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
