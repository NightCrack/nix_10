package ua.com.alevel.db.impl;

import ua.com.alevel.db.AuthorsDB;
import ua.com.alevel.entity.Author;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthorsImpl implements AuthorsDB {

    private static final AtomicInteger count = new AtomicInteger(0);
    private static AuthorsImpl instance;
    private final int arraySize = 10;
    private Author[] authors = new Author[arraySize];

    private AuthorsImpl() {
    }

    public static AuthorsImpl getInstance() {
        if (instance == null) {
            instance = new AuthorsImpl();
        }
        return instance;
    }

    @Override
    public void create(Author author) {
        author.setId(count.incrementAndGet());
        int authorsAmount = 0;
        for (Author value : authors) {
            if (value != null) {
                ++authorsAmount;
            }
        }
        if (!(authors.length > authorsAmount)) {
            authors = Arrays.copyOf(authors, (authors.length + arraySize));
        }
        authors[authorsAmount] = author;
    }

    @Override
    public boolean update(Author author) {
        Author current = findById(author.getId());
        if (current != null) {
            current.setFirstName(author.getFirstName());
            current.setLastName(author.getLastName());
            current.setDateOfBirth(author.getDateOfBirth());
            current.setDateOfDeath(author.getDateOfDeath());
            current.setIsbn(author.getIsbns());
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(Integer id) {
        for (int position = 0; position < authors.length; position++) {
            if ((authors[position] != null) && (authors[position].getId().equals(id))) {
                for (; position < authors.length - 1; position++) {
                    authors[position] = authors[position + 1];
                }
                authors = Arrays.copyOf(authors, (authors.length - 1));
                return true;
            }
        }
        return false;
    }

    @Override
    public Author findById(Integer id) {
        for (Author author : authors) {
            if ((author != null) && (author.getId().equals(id))) {
                return author;
            }
        }
        return null;
    }

    @Override
    public Author[] findAll() {
        return authors;
    }

}
