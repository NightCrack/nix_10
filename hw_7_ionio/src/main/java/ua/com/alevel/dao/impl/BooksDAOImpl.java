package ua.com.alevel.dao.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import ua.com.alevel.dao.BooksDAO;
import ua.com.alevel.db.AuthorsDB;
import ua.com.alevel.db.BookInstancesDB;
import ua.com.alevel.db.BooksDB;
import ua.com.alevel.db.GenresDB;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Genre;

import java.util.Collection;
import java.util.List;

@Service
public class BooksDAOImpl implements BooksDAO<String> {

    @Autowired
    private BooksDB<String> booksTable;
    @Autowired
    private GenresDB<Long> genresTable;
    @Autowired
    private AuthorsDB<Long> authorsTable;
    @Autowired
    private BookInstancesDB<Long> bookInstancesTable;

    @Override
    public boolean create(Book book) {
        Genre genre = genresTable.findById(book.getGenreId());
        Author author = authorsTable.findById(book.getAuthorId());
        return booksTable.create(book) &&
                genresTable.update(genre.addBook(book.getIsbn())) &&
                authorsTable.update(author.addBook(book.getIsbn()));
    }

    @Override
    public boolean update(Book book) {
        return booksTable.update(book);
    }

    @Override
    public boolean delete(String isbn) {
        Book book = booksTable.findById(isbn);
        Genre genre = genresTable.findById(book.getGenreId());
        genre.removeBook(isbn);
        Author author = authorsTable.findById(book.getAuthorId());
        author.removeBook(isbn);
        boolean returnValue = true;
        List<Long> instancesId = book.getInstancesIds();
        for (Long id : instancesId) {
            returnValue = returnValue & bookInstancesTable.delete(id);
        }
        returnValue = returnValue & booksTable.delete(isbn);
        returnValue = returnValue & genresTable.update(genre);
        returnValue = returnValue & authorsTable.update(author);
        return returnValue;
    }

    @Override
    public Book findById(String isbn) {
        return booksTable.findById(isbn);
    }

    @Override
    public <COLLECTION extends Collection<Book>> COLLECTION findAll() {
        return booksTable.findAll();
    }
}
