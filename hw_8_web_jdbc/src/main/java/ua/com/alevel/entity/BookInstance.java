package ua.com.alevel.entity;

import ua.com.alevel.type.StatusType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_instances")
public final class BookInstance extends WithIdEntity {

    @ManyToOne
    private Book book;
    private String imprint;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_back")
    private Date dueBack;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    public BookInstance() {
        this.status = StatusType.Maintenance;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public String getImprint() {
        return imprint;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }
}
