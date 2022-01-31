package ua.com.alevel.view.dto.request;

import com.neovisionaries.i18n.CountryCode;
import ua.com.alevel.type.StatusType;
import ua.com.alevel.view.dto.response.BookInstanceResponseDto;

import java.sql.Date;
import java.time.LocalDateTime;

public class BookInstanceRequestDto extends RequestWithIdDto {

    private String imprint;
    private Date publishingDate;
    private CountryCode countryCode;
    private LocalDateTime dueBack;
    private StatusType status;
    private String bookIsbn;

    public BookInstanceRequestDto() {
    }

    public BookInstanceRequestDto(BookInstanceResponseDto responseDto) {
        this.imprint = responseDto.getImprint();
        this.publishingDate = responseDto.getPublishingDate();
        this.countryCode = responseDto.getCountryCode();
        this.dueBack = responseDto.getDueBack();
        this.status = responseDto.getStatus();
        this.bookIsbn = responseDto.getBook().getIsbn();
    }

    public BookInstanceRequestDto(String isbn) {
        this.bookIsbn = isbn;
    }

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

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = toSqlDate(publishingDate);
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

    public void setDueBack(String dueBack) {
        this.dueBack = LocalDateTime.parse(dueBack);
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
