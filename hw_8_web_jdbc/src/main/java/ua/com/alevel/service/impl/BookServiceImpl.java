package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.repository.AuthorRepository;
import ua.com.alevel.repository.BookInstanceRepository;
import ua.com.alevel.repository.BookRepository;
import ua.com.alevel.repository.GenreRepository;
import ua.com.alevel.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookInstanceRepository bookInstanceRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, BookInstanceRepository bookInstanceRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookInstanceRepository = bookInstanceRepository;
    }

    @Override
    public void create(Book entity) {
        Optional<Author> optionalAuthor = authorRepository.findById(entity.getAuthor().getId());
        Optional<Genre> optionalGenre = genreRepository.findById(entity.getGenre().getId());
        if (optionalAuthor.isPresent() && optionalGenre.isPresent()) {
            optionalAuthor.get().getBooks().add(entity);
            optionalGenre.get().getBooks().add(entity);
            bookRepository.save(entity);
            authorRepository.save(optionalAuthor.get());
            genreRepository.save(optionalGenre.get());
        }
    }

    @Override
    public void update(Book entity) {
        if (bookRepository.existsById(entity.getIsbn())) {
            bookRepository.save(entity);
        }
    }

    @Override
    public void delete(String isbn) {
        if (bookRepository.existsById(isbn)) {
            Book defaultBook = bookRepository.findAll()
                    .stream()
                    .filter(entry -> entry
                            .getName()
                            .equals("Undefined"))
                    .toList().get(0);
            Book book = bookRepository.findById(isbn).get();
            List<BookInstance> bookInstances = book.getBookInstances();
            bookInstances = bookInstances.stream().peek(bookInstance -> bookInstance.setBook(defaultBook)).toList();
            Genre genre = book.getGenre();
            genre.getBooks().remove(book);
            Author author = book.getAuthor();
            author.getBooks().remove(book);
            bookInstanceRepository.saveAll(bookInstances);
            genreRepository.save(genre);
            authorRepository.save(author);
            bookRepository.deleteById(isbn);
        }
    }

    @Override
    public Optional<Book> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByAuthor(Long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isPresent()) {
            return bookRepository.findAllByAuthor(optionalAuthor.get());
        }
        return Collections.emptyList();
    }
}
