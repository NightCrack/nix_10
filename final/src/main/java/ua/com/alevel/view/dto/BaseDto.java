package ua.com.alevel.view.dto;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class BaseDto {

    private final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public Instant instantFromString(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date).toInstant();
        } catch (ParseException exception) {
            return null;
        }
    }

    public Date dateFormatting(String stringDate) {
        Date localDate = Date.valueOf(stringDate);
        return localDate;
    }

    public String getLifespan(Date birthDate, Date deathDate) {
        long lifespan = ChronoUnit.YEARS.between(birthDate.toLocalDate(), deathDate.toLocalDate());
        return String.valueOf(lifespan);
    }

}
