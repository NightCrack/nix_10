package ua.com.alevel.db.impl;

import ua.com.alevel.db.BookInstancesDB;
import ua.com.alevel.entity.BookInstance;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BookInstancesImpl implements BookInstancesDB {

    private static final AtomicInteger count = new AtomicInteger(0);
    private static BookInstancesImpl instance;
    private final int arraySize = 10;
    private BookInstance[] bookInstances = new BookInstance[arraySize];

    private BookInstancesImpl() {
    }

    public static BookInstancesImpl getInstance() {
        if (instance == null) {
            instance = new BookInstancesImpl();
        }
        return instance;
    }

    @Override
    public void create(BookInstance bookInstance) {
        bookInstance.setId(count.incrementAndGet());
        int bookInstancesAmount = 0;
        for (BookInstance value : bookInstances) {
            if (value != null) {
                ++bookInstancesAmount;
            }
        }
        if (!(bookInstances.length > bookInstancesAmount)) {
            bookInstances = Arrays.copyOf(bookInstances, (bookInstances.length + arraySize));
        }
        bookInstances[bookInstancesAmount] = bookInstance;
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        BookInstance current = findById(bookInstance.getId());
        if (current != null) {
            current.setBookIsbn(bookInstance.getIsbn());
            current.setImprint(bookInstance.getImprint());
            current.setDueBack(bookInstance.getDueBack());
            current.setStatus(bookInstance.getStatus());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        for (int position = 0; position < bookInstances.length; position++) {
            if ((bookInstances[position] != null) && (bookInstances[position].getId().equals(id))) {
                for (; position < bookInstances.length - 1; position++) {
                    bookInstances[position] = bookInstances[position + 1];
                }
                bookInstances = Arrays.copyOf(bookInstances, (bookInstances.length - 1));
                return true;
            }
        }
        return false;
    }

    @Override
    public BookInstance findById(Integer id) {
        for (BookInstance bookInstance : bookInstances) {
            if ((bookInstance != null) && (bookInstance.getId().equals(id))) {
                return bookInstance;
            }
        }
        return null;
    }

    @Override
    public BookInstance[] findAll() {
        return bookInstances;
    }
}
