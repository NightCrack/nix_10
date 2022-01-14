package ua.com.alevel.dto.book;

import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.entity.Genre;

import java.util.List;

public class BookResponseDto extends ResponseDto {

    private String isbn;
    private String name;
    private Author author;
    private Genre genre;
    private String summary;
    private List<BookInstance> bookInstances;

    public BookResponseDto(Book book) {
        this.setIsbn(book.getIsbn());
        this.setName(book.getName());
        this.setAuthor(book.getAuthor());
        this.setGenre(book.getGenre());
        this.setSummary(book.getSummary());
        this.setBookInstances(book.getBookInstances());
        this.setCreated(book.getCreated());
        this.setUpdated(book.getUpdated());
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<BookInstance> getBookInstances() {
        return bookInstances;
    }

    public void setBookInstances(List<BookInstance> bookInstances) {
        this.bookInstances = bookInstances;
    }
}
