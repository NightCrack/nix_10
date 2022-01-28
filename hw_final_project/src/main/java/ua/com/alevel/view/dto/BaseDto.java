package ua.com.alevel.view.dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public abstract class BaseDto {

    public Instant instantFromString(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date).toInstant();
        } catch (ParseException exception) {
            return null;
        }
    }

    public Date toSqlDate(String stringDate) {
        Date localDate = Date.valueOf(stringDate);
        return localDate;
    }

    public Instant toInstant(String stringDate) {
        LocalDate date = LocalDate.parse(stringDate);
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant;
    }

    public String getLifespan(Date birthDate, Date deathDate) {
        long lifespan = ChronoUnit.YEARS.between(birthDate.toLocalDate(), deathDate.toLocalDate());
        return String.valueOf(lifespan);
    }

}
