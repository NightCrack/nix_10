package ua.com.alevel.persistence.entity;

import com.neovisionaries.i18n.CountryCode;
import ua.com.alevel.type.StatusType;
import ua.com.alevel.view.dto.request.BookInstanceRequestDto;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneOffset;


public final class BookInstance extends WithIdEntity {

    private String imprint;
    private Date publishingDate;
    private CountryCode countryCode;
    private Instant dueBack;
    private StatusType status;
    private Book book;

    public BookInstance() {
    }

    public BookInstance(BookInstanceRequestDto requestDto, Book book) {
        imprint = requestDto.getImprint();
        publishingDate = requestDto.getPublishingDate();
        countryCode = requestDto.getCountryCode();
        dueBack = requestDto.getDueBack().toInstant(ZoneOffset.UTC);
        status = requestDto.getStatus();
        this.book = book;
    }

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

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
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
