package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.BooksDAOImpl;
import ua.com.alevel.entity.Book;
import ua.com.alevel.service.BooksService;

public class BooksServiceImpl implements BooksService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    private final BooksDAOImpl booksDAO = new BooksDAOImpl();
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public void create(Book book) {
        LOGGER_INFO.info(++counterCreate + " create_book");
        booksDAO.create(book);
        LOGGER_INFO.info(counterCreate + " result: " + book.getId());
    }

    @Override
    public boolean update(Book book) {
        LOGGER_INFO.info(++counterUpdate + " update_book " + book.getId());
        status = booksDAO.update(book);
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        return status;
    }

    @Override
    public boolean delete(String isbn) {
        LOGGER_INFO.info(++counterDelete + " delete_book " + isbn);
        status = booksDAO.delete(isbn);
        LOGGER_INFO.info(counterDelete + " result: " + status);
        return status;
    }

    @Override
    public Book findById(String isbn) {
        return findByIsbn(isbn);
    }

    public Book findByIsbn(String isbn) {
        LOGGER_INFO.info(++counterFindById + " findById_book " + isbn);
        return booksDAO.findByIsbn(isbn);
    }

    @Override
    public Book[] findAll() {
        LOGGER_INFO.info(++counterFindAll + " findAll_book");
        return booksDAO.findAll();
    }
}
