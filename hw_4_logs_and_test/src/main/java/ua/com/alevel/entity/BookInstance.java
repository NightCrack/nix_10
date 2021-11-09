package ua.com.alevel.entity;

import ua.com.alevel.db.impl.BooksImpl;
import ua.com.alevel.util.CustomUtil;

import java.util.Date;

public class BookInstance extends BaseEntity {

    private String bookIsbn;
    private String imprint;
    private String status;
    private Date dueBack;

    public String getIsbn() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueBack() {
        return dueBack;
    }

    public void setDueBack(Date dueBack) {
        this.dueBack = dueBack;
    }

    public String toStringBrief() {
        return "(ID: " + this.getId() +
                ") Title: " + BooksImpl.getInstance().findById(this.bookIsbn).getTitle() +
                "; Imprint: " + imprint +
                "; Status: " + status;
    }

    public String toStringShort() {
        return "(ID: " + this.getId() +
                ") Status: " + status;
    }

    @Override
    public String toString() {
        return BooksImpl.getInstance().findById(this.bookIsbn).toStringBriefWithName() +
                System.lineSeparator() + System.lineSeparator() +
                "Imprint: " + imprint +
                System.lineSeparator() + System.lineSeparator() +
                "Status: " + status +
                " (" + CustomUtil.dateToString(dueBack) + ")";
    }
}
