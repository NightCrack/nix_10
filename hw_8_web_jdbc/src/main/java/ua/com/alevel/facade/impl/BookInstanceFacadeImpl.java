package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.bookInstance.BookInstanceRequestDto;
import ua.com.alevel.dto.bookInstance.BookInstanceResponseDto;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.service.BookInstanceService;
import ua.com.alevel.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookInstanceFacadeImpl implements BookInstanceFacade {

    private final BookInstanceService bookInstanceService;
    private final BookService bookService;

    public BookInstanceFacadeImpl(BookInstanceService bookInstanceService, BookService bookService) {
        this.bookInstanceService = bookInstanceService;
        this.bookService = bookService;
    }

    @Override
    public void create(BookInstanceRequestDto bookInstanceRequestDto) {
        Optional<Book> optionalBook = bookService.findById(bookInstanceRequestDto.getBookId());
        if (optionalBook.isPresent()) {
            BookInstance bookInstance = new BookInstance();
            bookInstance.setBook(optionalBook.get());
            bookInstance.setImprint(bookInstanceRequestDto.getImprint());
            bookInstance.setDueBack(bookInstanceRequestDto.getDueBack());
            bookInstance.setStatus(bookInstanceRequestDto.getStatus());
            bookInstanceService.create(bookInstance);
        }
    }

    @Override
    public void update(BookInstanceRequestDto bookInstanceRequestDto, Long id) {
        Optional<BookInstance> optionalBookInstance = bookInstanceService.findById(id);
        Optional<Book> optionalBook = bookService.findById(bookInstanceRequestDto.getBookId());
        if (optionalBookInstance.isPresent() && optionalBook.isPresent()) {
            BookInstance bookInstance = optionalBookInstance.get();
            bookInstance.setBook(optionalBook.get());
            bookInstance.setImprint(bookInstanceRequestDto.getImprint());
            bookInstance.setDueBack(bookInstanceRequestDto.getDueBack());
            bookInstance.setStatus(bookInstanceRequestDto.getStatus());
            bookInstanceService.update(bookInstance);
        }
    }

    @Override
    public void delete(Long id) {
        bookInstanceService.delete(id);
    }

    @Override
    public BookInstanceResponseDto findById(Long id) {
        Optional<BookInstance> optionalBookInstance = bookInstanceService.findById(id);
        return optionalBookInstance.map(BookInstanceResponseDto::new).orElse(null);
    }

    @Override
    public List<BookInstanceResponseDto> findAll() {
        return bookInstanceService.findAll()
                .stream()
                .map(BookInstanceResponseDto::new)
                .toList();
    }
}
