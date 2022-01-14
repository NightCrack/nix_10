package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.repository.AuthorRepository;
import ua.com.alevel.repository.BookRepository;
import ua.com.alevel.repository.GenreRepository;
import ua.com.alevel.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void create(Author entity) {
        authorRepository.save(entity);
    }

    @Override
    public void update(Author entity) {
        if (authorRepository.existsById(entity.getId())) {
            authorRepository.save(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (authorRepository.existsById(id)) {
            Author defaultAuthor = authorRepository.findAll()
                    .stream()
                    .filter(entry -> entry
                            .getFirstName()
                            .equals("Un") &&
                            entry
                                    .getLastName()
                                    .equals("Defined"))
                    .toList().get(0);
            Author author = authorRepository.findById(id).get();
            List<Book> books = author.getBooks();
            books = books.stream().peek(entry -> entry.setAuthor(defaultAuthor)).toList();
            bookRepository.saveAll(books);
            authorRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
