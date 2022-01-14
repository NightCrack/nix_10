package ua.com.alevel.dto.genre;

import ua.com.alevel.dto.ResponseWithIdDto;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.type.GenreType;

import java.util.List;

public final class GenreResponseDto extends ResponseWithIdDto {

    private GenreType genreType;
    private List<Book> books;

    public GenreResponseDto(Genre genre) {
        this.setGenreType(genre.getGenreType());
        this.setBooks(genre.getBooks());
        this.setCreated(genre.getCreated());
        this.setUpdated(genre.getUpdated());
        this.setId(genre.getId());
    }

    public GenreType getGenreType() {
        return genreType;
    }

    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
