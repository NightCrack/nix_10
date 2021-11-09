package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.BooksDAO;
import ua.com.alevel.db.impl.BooksImpl;
import ua.com.alevel.entity.Book;

public class BooksDAOImpl implements BooksDAO {

    private final BooksImpl booksTable = BooksImpl.getInstance();

    @Override
    public void create(Book book) {
        booksTable.create(book);
    }

    @Override
    public boolean update(Book book) {
        return booksTable.update(book);
    }

    @Override
    public boolean delete(String isbn) {
        return booksTable.delete(isbn);
    }

    @Override
    public Book findById(String isbn) {
        return findByIsbn(isbn);
    }

    public Book findByIsbn(String isbn) {
        return booksTable.findByIsbn(isbn);
    }

    @Override
    public Book[] findAll() {
        return booksTable.findAll();
    }
}
