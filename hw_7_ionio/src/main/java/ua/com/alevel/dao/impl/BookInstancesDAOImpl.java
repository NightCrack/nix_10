package ua.com.alevel.dao.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import ua.com.alevel.dao.BookInstancesDAO;
import ua.com.alevel.db.BookInstancesDB;
import ua.com.alevel.db.BooksDB;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;

import java.util.Collection;

@Service
public class BookInstancesDAOImpl implements BookInstancesDAO<Long> {

    @Autowired
    private BookInstancesDB<Long> bookInstancesTable;

    @Autowired
    private BooksDB<String> booksTable;

    @Override
    public boolean create(BookInstance bookInstance) {
        Book book = booksTable.findById(bookInstance.getBookIsbn());
        return bookInstancesTable.create(bookInstance) &&
                booksTable.update(book.addInstance(bookInstance.getId()));
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        return bookInstancesTable.update(bookInstance);
    }

    @Override
    public boolean delete(Long id) {
        BookInstance bookInstance = bookInstancesTable.findById(id);
        Book book = booksTable.findById(bookInstance.getBookIsbn());
        book.removeInstance(id);
        boolean returnValue = bookInstancesTable.delete(id);
        returnValue = returnValue & booksTable.update(book);
        return returnValue;
    }

    @Override
    public BookInstance findById(Long id) {
        return bookInstancesTable.findById(id);
    }

    @Override
    public <COLLECTION extends Collection<BookInstance>> COLLECTION findAll() {
        return bookInstancesTable.findAll();
    }
}
