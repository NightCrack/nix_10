package ua.com.alevel.view.dto.request;

import com.neovisionaries.i18n.CountryCode;
import ua.com.alevel.type.StatusType;

import java.time.Instant;
import java.util.Date;

public class BookInstanceRequestDto extends RequestWithIdDto {

    private String imprint;
    private String publishingDate;
    private CountryCode countryCode;
    private Instant dueBack;
    private StatusType status;
    private String bookIsbn;

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
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
