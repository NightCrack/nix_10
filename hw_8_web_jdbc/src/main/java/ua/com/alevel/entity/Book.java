package ua.com.alevel.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public final class Book extends BaseEntity {

    @Id
    private String isbn;

    @Column(name = "title")
    private String name;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Genre genre;
    private String summary;

    @OneToMany
    private List<BookInstance> bookInstances;

    public List<BookInstance> getBookInstances() {
        return bookInstances;
    }

    public void setBookInstances(List<BookInstance> bookInstances) {
        this.bookInstances = bookInstances;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
