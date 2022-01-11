package ua.com.alevel.dao.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import ua.com.alevel.dao.AuthorsDAO;
import ua.com.alevel.db.AuthorsDB;
import ua.com.alevel.db.BookInstancesDB;
import ua.com.alevel.db.BooksDB;
import ua.com.alevel.db.GenresDB;
import ua.com.alevel.db.impl.AuthorsDBImpl;
import ua.com.alevel.db.impl.BookInstancesDBImpl;
import ua.com.alevel.db.impl.BooksDBImpl;
import ua.com.alevel.db.impl.GenresDBImpl;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Genre;

import java.util.Collection;
import java.util.List;

@Service
public class AuthorsDAOImpl implements AuthorsDAO<Long> {

    @Autowired
    private AuthorsDB<Long> authorsTable;

    @Autowired
    private BooksDB<String> booksTable;

    @Autowired
    private BookInstancesDB<Long> bookInstancesTable;

    @Autowired
    private GenresDB<Long> genresTable;

    @Override
    public boolean create(Author author) {
        return authorsTable.create(author);
    }

    @Override
    public boolean update(Author author) {
        return authorsTable.update(author);
    }

    @Override
    public boolean delete(Long id) {
        Author author = authorsTable.findById(id);
        List<Book> books = author.getIsbn().stream().map(isbn -> booksTable.findById(isbn)).toList();
        boolean returnValue = true;
        for (Book book : books) {
            for (Long instanceId : book.getInstancesIds()) {
                returnValue = returnValue & bookInstancesTable.delete(instanceId);
            }
        }
        List<Genre> genres = genresTable.findAll();
        for (String isbn : author.getIsbn()) {
            for (Genre genre : genres) {
                if (genre.getIsbnList().contains(isbn)) {
                    genre.removeBook(isbn);
                    returnValue = returnValue & genresTable.update(genre);
                }
            }
        }
        for (String isbn : author.getIsbn()) {
            returnValue = returnValue & booksTable.delete(isbn);
        }
        returnValue = returnValue & authorsTable.delete(id);
        return returnValue;
    }

    @Override
    public Author findById(Long id) {
        return authorsTable.findById(id);
    }

    @Override
    public <COLLECTION extends Collection<Author>> COLLECTION findAll() {
        return authorsTable.findAll();
    }
}
