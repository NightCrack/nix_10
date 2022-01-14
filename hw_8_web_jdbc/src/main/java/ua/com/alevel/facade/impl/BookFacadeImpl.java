package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.book.BookRequestDto;
import ua.com.alevel.dto.book.BookResponseDto;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookService;
import ua.com.alevel.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class BookFacadeImpl implements BookFacade {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookFacadeImpl(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void create(BookRequestDto bookRequestDto) {
        Optional<Author> optionalAuthor = authorService.findById(bookRequestDto.getAuthorId());
        Optional<Genre> optionalGenre = genreService.findById(bookRequestDto.getGenreId());
        if (optionalAuthor.isPresent() && optionalGenre.isPresent()) {
            Book book = new Book();
            book.setIsbn(bookRequestDto.getIsbn());
            book.setName(bookRequestDto.getName());
            book.setAuthor(optionalAuthor.get());
            book.setGenre(optionalGenre.get());
            book.setSummary(bookRequestDto.getSummary());
            bookService.create(book);
        }
    }

    @Override
    public void update(BookRequestDto bookRequestDto, String isbn) {
        Optional<Author> optionalAuthor = authorService.findById(bookRequestDto.getAuthorId());
        Optional<Genre> optionalGenre = genreService.findById(bookRequestDto.getGenreId());
        Optional<Book> optionalBook = bookService.findById(isbn);
        if (optionalBook.isPresent() &&
                optionalAuthor.isPresent() &&
                optionalGenre.isPresent()) {
            Book book = optionalBook.get();
            book.setIsbn(bookRequestDto.getIsbn());
            book.setName(bookRequestDto.getName());
            book.setAuthor(optionalAuthor.get());
            book.setGenre(optionalGenre.get());
            book.setSummary(bookRequestDto.getSummary());
            bookService.update(book);
        }
    }

    @Override
    public void delete(String isbn) {
        bookService.delete(isbn);
    }

    @Override
    public BookResponseDto findById(String isbn) {
        Optional<Book> optionalBook = bookService.findById(isbn);
        return optionalBook.map(BookResponseDto::new).orElse(null);
    }

    @Override
    public List<BookResponseDto> findAll() {
        return bookService.findAll()
                .stream()
                .map(BookResponseDto::new)
                .toList();
    }
}
