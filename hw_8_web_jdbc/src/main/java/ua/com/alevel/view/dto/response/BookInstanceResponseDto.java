package ua.com.alevel.view.dto.response;

import com.neovisionaries.i18n.CountryCode;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.persistence.entity.BookInstance;
import ua.com.alevel.type.StatusType;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BookInstanceResponseDto extends ResponseWithIdDto {

    private String imprint;
    private Date publishingDate;
    private CountryCode countryCode;
    private LocalDateTime dueBack;
    private StatusType status;
    private Book book;

    public BookInstanceResponseDto(BookInstance bookInstance) {
        imprint = bookInstance.getImprint();
        publishingDate = bookInstance.getPublishingDate();
        countryCode = bookInstance.getCountryCode();
        dueBack = LocalDateTime.ofInstant(bookInstance.getDueBack(), ZoneId.systemDefault());
        status = bookInstance.getStatus();
        if (bookInstance.getBook() != null) {
            book = bookInstance.getBook();
        }
        this.setId(bookInstance.getId());
        this.setCreated(bookInstance.getCreated());
        this.setUpdated(bookInstance.getUpdated());
        this.setVisible(bookInstance.getVisible());
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

    public LocalDateTime getDueBack() {
        return dueBack;
    }

    public void setDueBack(LocalDateTime dueBack) {
        this.dueBack = dueBack;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
