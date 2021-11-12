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
        if (bookIsbn != null) {
            return bookIsbn;
        }
        return null;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getImprint() {
        if (imprint != null) {
            return imprint;
        }
        return null;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }

    public String getStatus() {
        if (status != null) {
            return status;
        }
        return null;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueBack() {
        if (dueBack != null) {
            return dueBack;
        }
        return null;
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
