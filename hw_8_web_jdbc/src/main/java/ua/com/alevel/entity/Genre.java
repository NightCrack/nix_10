package ua.com.alevel.entity;


import ua.com.alevel.type.GenreType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
public final class Genre extends WithIdEntity {

    @Enumerated(EnumType.STRING)
    private GenreType genreType;

    @OneToMany
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public GenreType getGenreType() {
        return genreType;
    }

    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }
}
