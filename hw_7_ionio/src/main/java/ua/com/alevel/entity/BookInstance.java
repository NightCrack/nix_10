package ua.com.alevel.entity;

import custom.annotations.Entity;
import custom.annotations.SetClass;
import ua.com.alevel.db.BooksDB;
import ua.com.alevel.util.CustomUtil;

import java.sql.Date;

@Entity
public final class BookInstance extends WithIDEntity {

    @SetClass
    private transient static Class booksDB;

    private Date dueBack;
    private String status;
    private String imprint;
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

    private Book findBook() {
        try {
            return (Book) ((BooksDB) booksDB
                    .getDeclaredConstructor()
                    .newInstance())
                    .findById(this.bookIsbn);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String toStringBrief() {
        return "(ID: " + this.getId() +
                ") Title: " + findBook().getName() +
                "; Imprint: " + imprint +
                "; Status: " + status;
    }

    public String toStringShort() {
        return "(ID: " + this.getId() +
                ") Status: " + status;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                findBook().toStringBrief() +
                System.lineSeparator() +
                "Imprint: " + imprint +
                System.lineSeparator() +
                "Status: " + status +
                " (" + CustomUtil.dateToString(dueBack) + ")";
    }
}
