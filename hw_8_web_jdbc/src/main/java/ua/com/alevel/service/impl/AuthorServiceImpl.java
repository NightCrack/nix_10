package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.repository.AuthorRepository;
import ua.com.alevel.repository.BookRepository;
import ua.com.alevel.repository.GenreRepository;
import ua.com.alevel.service.AuthorService;

import java.util.Date;
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
        Author defaultAuthor;
        Optional<Author> optionalAuthor = authorRepository.findAll()
                .stream()
                .filter(entry -> (entry.getFirstName() + " " + entry.getLastName())
                        .equals("Un Defined"))
                .findFirst();
        if (optionalAuthor.isEmpty()) {
            Author author = new Author();
            author.setFirstName("Un");
            author.setLastName("Defined");
            author.setDateOfBirth(new Date(0));
            author.setDateOfDeath(new Date(0));
            authorRepository.save(author);
            defaultAuthor = author;
        } else {
            defaultAuthor = optionalAuthor.get();
        }
        if (authorRepository.existsById(id)) {
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
