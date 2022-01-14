package ua.com.alevel.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseDto {

    public Date dateFromString(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException exception) {
            return null;
        }
    }

}
