package ua.com.alevel.dto.bookInstance;

import ua.com.alevel.dto.ResponseWithIdDto;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.type.StatusType;

import java.util.Date;

public class BookInstanceResponseDto extends ResponseWithIdDto {

    private Book book;
    private String imprint;
    private Date dueBack;
    private StatusType status;

    public BookInstanceResponseDto(BookInstance bookInstance) {
        this.setBook(bookInstance.getBook());
        this.setImprint(bookInstance.getImprint());
        this.setDueBack(bookInstance.getDueBack());
        this.setStatus(bookInstance.getStatus());
        this.setCreated(bookInstance.getCreated());
        this.setUpdated(bookInstance.getUpdated());
        this.setId(bookInstance.getId());
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

    public Date getDueBack() {
        return dueBack;
    }

    public void setDueBack(Date dueBack) {
        this.dueBack = dueBack;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
