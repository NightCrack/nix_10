package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.BooksDAOImpl;
import ua.com.alevel.entity.Book;
import ua.com.alevel.service.BooksService;

public class BooksServiceImpl implements BooksService {

    private final BooksDAOImpl booksDAO = new BooksDAOImpl();

    @Override
    public void create(Book book) {
        booksDAO.create(book);
    }

    @Override
    public boolean update(Book book) {
        return booksDAO.update(book);
    }

    @Override
    public boolean delete(String isbn) {
        return booksDAO.delete(isbn);
    }

    @Override
    public Book findById(String isbn) {
        return findByIsbn(isbn);
    }

    public Book findByIsbn(String isbn) {
        return booksDAO.findByIsbn(isbn);
    }

    @Override
    public Book[] findAll() {
        return booksDAO.findAll();
    }
}
