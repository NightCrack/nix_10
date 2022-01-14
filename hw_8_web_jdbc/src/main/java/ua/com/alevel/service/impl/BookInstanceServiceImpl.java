package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.repository.BookInstanceRepository;
import ua.com.alevel.repository.BookRepository;
import ua.com.alevel.service.BookInstanceService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookInstanceServiceImpl implements BookInstanceService {

    private final BookInstanceRepository bookInstanceRepository;
    private final BookRepository bookRepository;

    public BookInstanceServiceImpl(BookInstanceRepository bookInstanceRepository, BookRepository bookRepository) {
        this.bookInstanceRepository = bookInstanceRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void create(BookInstance entity) {
        Optional<Book> optionalBook = bookRepository.findById(entity.getBook().getIsbn());
        if (optionalBook.isPresent()) {
            optionalBook.get().getBookInstances().add(entity);
            bookInstanceRepository.save(entity);
            bookRepository.save(optionalBook.get());
        }
    }

    @Override
    public void update(BookInstance entity) {
        if (bookInstanceRepository.existsById(entity.getId())) {
            bookInstanceRepository.save(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (bookInstanceRepository.existsById(id)) {
            BookInstance bookInstance = bookInstanceRepository.findById(id).get();
            Book book = bookInstance.getBook();
            book.getBookInstances().remove(bookInstance);
            bookRepository.save(book);
            bookInstanceRepository.deleteById(id);
        }
    }

    @Override
    public Optional<BookInstance> findById(Long id) {
        return bookInstanceRepository.findById(id);
    }

    @Override
    public List<BookInstance> findAll() {
        return bookInstanceRepository.findAll();
    }


    @Override
    public List<BookInstance> findAllByBook(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            return bookInstanceRepository.findAllByBook(optionalBook.get());
        }
        return Collections.emptyList();
    }
}
