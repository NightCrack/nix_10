package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.repository.AuthorRepository;
import ua.com.alevel.repository.BookInstanceRepository;
import ua.com.alevel.repository.BookRepository;
import ua.com.alevel.repository.GenreRepository;
import ua.com.alevel.service.GenreService;
import ua.com.alevel.type.GenreType;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookInstanceRepository bookInstanceRepository;

    public GenreServiceImpl(GenreRepository genreRepository, BookRepository bookRepository, AuthorRepository authorRepository, BookInstanceRepository bookInstanceRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookInstanceRepository = bookInstanceRepository;
    }


    @Override
    public void create(Genre entity) {
        genreRepository.save(entity);
    }

    @Override
    public void update(Genre entity) {
        if (genreRepository.existsById(entity.getId())) {
            genreRepository.save(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (genreRepository.existsById(id)) {
            Genre defaultGenre = genreRepository.findAll()
                    .stream()
                    .filter(entry -> entry
                            .getGenreType()
                            .equals(GenreType.Undefined))
                    .toList().get(0);
            Genre genre = genreRepository.findById(id).get();
            List<Book> books = bookRepository.findAllByGenre(genre);
            books = books.stream().peek(book -> book.setGenre(defaultGenre)).toList();
            bookRepository.saveAll(books);
            genreRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

}
