package ua.com.alevel.dto.bookInstance;

import ua.com.alevel.dto.RequestWithIdDto;
import ua.com.alevel.type.StatusType;

import java.util.Date;

public class BookInstanceRequestDto extends RequestWithIdDto {

    private String bookId;
    private String imprint;
    private Date dueBack;
    private StatusType status;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public void setDueBack(String dueBack) {
        this.dueBack = dateFromString(dueBack);
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
