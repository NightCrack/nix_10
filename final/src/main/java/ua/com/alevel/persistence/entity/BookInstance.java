package ua.com.alevel.persistence.entity;

import com.neovisionaries.i18n.CountryCode;
import ua.com.alevel.type.StatusType;

import java.time.Instant;
import java.util.Date;


public final class BookInstance extends WithIdEntity {

    private String imprint;
    private String publishingDate;
    private CountryCode countryCode;
    private Instant dueBack;
    private StatusType status;
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getImprint() {
        return imprint;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public Instant getDueBack() {
        return dueBack;
    }

    public void setDueBack(Instant dueBack) {
        this.dueBack = dueBack;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
