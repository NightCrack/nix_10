package ua.com.alevel.dao.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import ua.com.alevel.dao.GenresDAO;
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
public class GenresDAOImpl implements GenresDAO<Long> {

    @Autowired
    private GenresDB<Long> genresTable;

    @Autowired
    private BooksDB<String> booksTable;

    @Autowired
    private AuthorsDB<Long> authorsTable;

    @Autowired
    private BookInstancesDB<Long> bookInstanceTable;

    @Override
    public boolean create(Genre genre) {
        return genresTable.create(genre);
    }

    @Override
    public boolean update(Genre genre) {
        return genresTable.update(genre);
    }

    @Override
    public boolean delete(Long id) {
        Genre genre = genresTable.findById(id);
        List<Book> books = booksTable.findAll().stream().filter(book -> book.getGenreId().equals(id)).toList();
        boolean returnValue = true;
        for (Book book : books) {
            for (Long instanceId : book.getInstancesIds()) {
                returnValue = returnValue & bookInstanceTable.delete(instanceId);
            }
        }
        for (Book book : books) {
            returnValue = returnValue & booksTable.delete(book.getIsbn());
        }
        List<Author> authors = authorsTable.findAll();
        for (Author author : authors) {
            for (Book book : books) {
                if (author.getIsbn().contains(book.getIsbn())) {
                    author.removeBook(book.getIsbn());
                    returnValue = returnValue & authorsTable.update(author);
                }
            }
        }
        returnValue = returnValue & genresTable.delete(id);
        return returnValue;
    }

    @Override
    public Genre findById(Long id) {
        return genresTable.findById(id);
    }

    @Override
    public <COLLECTION extends Collection<Genre>> COLLECTION findAll() {
        return genresTable.findAll();
    }
}
