package ua.com.alevel.view.dto.response;

import com.neovisionaries.i18n.CountryCode;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.persistence.entity.BookInstance;
import ua.com.alevel.type.StatusType;

import java.time.Instant;

public class BookInstanceResponseDto extends ResponseWithIdDto {

    private String imprint;
    private String publishingDate;
    private CountryCode countryCode;
    private Instant dueBack;
    private StatusType status;
    private Book book;

    public BookInstanceResponseDto(BookInstance bookInstance) {
        imprint = bookInstance.getImprint();
        publishingDate = bookInstance.getPublishingDate();
        countryCode = bookInstance.getCountryCode();
        dueBack = bookInstance.getDueBack();
        status = bookInstance.getStatus();
        if(bookInstance.getBook() != null) {
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
