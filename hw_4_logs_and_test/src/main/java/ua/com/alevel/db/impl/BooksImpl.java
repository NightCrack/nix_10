package ua.com.alevel.db.impl;

import ua.com.alevel.db.BooksDB;
import ua.com.alevel.entity.Book;

import java.util.Arrays;

public class BooksImpl implements BooksDB {

    private static BooksImpl instance;
    private final int arraySize = 10;
    private Book[] books = new Book[arraySize];

    private BooksImpl() {
    }

    public static BooksImpl getInstance() {
        if (instance == null) {
            instance = new BooksImpl();
        }
        return instance;
    }

    @Override
    public void create(Book book) {
        int booksAmount = 0;
        for (Book value : books) {
            if (value != null) {
                booksAmount++;
            }
        }
        if (!(books.length > booksAmount)) {
            books = Arrays.copyOf(books, books.length + arraySize);
        }
        books[booksAmount] = book;

    }

    @Override
    public boolean update(Book book) {
        Book current = findByIsbn(book.getIsbn());
        if (current != null) {
            current.setTitle(book.getTitle());
            current.setSummary(book.getSummary());
            current.setAuthorId(book.getAuthorId());
            current.setIsbn(book.getIsbn());
            current.setGenreId(book.getGenreId());
            current.setInstancesIds(book.getInstancesIds());
            return true;
        }
        return false;
    }

    @Override
    public Book findById(String isbn) {
        for (Book book : books) {
            if ((book != null) && (book.getIsbn().equals(isbn))) {
                return book;
            }
        }
        return null;
    }

    public Book findByIsbn(String isbn) {
        return findById(isbn);
    }

    @Override
    public boolean delete(String isbn) {
        for (int position = 0; position < books.length; position++) {
            if ((books[position] != null) && (books[position].getIsbn().equals(isbn))) {
                for (; position < books.length - 1; position++) {
                    books[position] = books[position + 1];
                }
                books = Arrays.copyOf(books, (books.length - 1));
                return true;
            }
        }
        return false;
    }

    @Override
    public Book[] findAll() {
        return books;
    }
}
