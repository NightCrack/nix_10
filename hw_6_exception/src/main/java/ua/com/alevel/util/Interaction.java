package ua.com.alevel.util;

import ua.com.alevel.customexception.LessThanZeroException;
import ua.com.alevel.entity.Date;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Interaction {

    public static Date modifyDay(Date date, int day, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            long time = date.getValue() % Date.DAY;
            long resultDays = date.getValue() / Date.DAY - day;
            date.setValue(time + resultDays * Date.DAY);
        } else {
            date.setDay(date.getDay() + day);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date modifyMonth(Date date, int month, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            int resultMonths = date.getYear() * 12 + date.getMonth() - month;
            date.setYear(resultMonths / 12);
            date.setMonth(resultMonths % 12);
        } else {
            date.setMonth(date.getMonth() + month);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date modifyYear(Date date, int year, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            date.setYear(date.getYear() - year);
        } else {
            date.setYear(date.getYear() + year);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date modifyHour(Date date, int hour, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            date.setHour(date.getHour() - hour);
        } else {
            date.setHour(date.getHour() + hour);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date modifyMinute(Date date, int minute, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            date.setMin(date.getMin() - minute);
        } else {
            date.setMin(date.getMin() + minute);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date modifySecond(Date date, int second, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            date.setSec(date.getSec() - second);
        } else {
            date.setSec(date.getSec() + second);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date modifyMillisecond(Date date, int millisecond, boolean subtracting) throws LessThanZeroException {
        if (subtracting) {
            date.setMil(date.getMil() - millisecond);
        } else {
            date.setMil(date.getMil() + millisecond);
        }
        if (date.getValue() < 0) {
            throw new LessThanZeroException();
        }
        return date;
    }

    public static Date getDifference(Date firstDate, Date secondDate) {
        Date returnDate = new Date(String.valueOf(firstDate.getTimeZone()), Math.abs(firstDate.getValue() - secondDate.getValue()));
        return returnDate;
    }

    public static List<Date> sortDates(List<Date> listOfDates, boolean ascending) {
        Date[] tempArray = listOfDates.toArray(new Date[0]);
        Arrays.sort(tempArray);
        listOfDates = Arrays.asList(tempArray);
        if (!ascending) {
            Collections.reverse(listOfDates);
        }
        return listOfDates;
    }
}
