package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final GenresDAO genresDAO;
    private final BookInstancesDAO bookInstancesDAO;

    public BookServiceImpl(BooksDAO booksDAO, AuthorsDAO authorsDAO, GenresDAO genresDAO, BookInstancesDAO bookInstancesDAO) {
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
        this.genresDAO = genresDAO;
        this.bookInstancesDAO = bookInstancesDAO;
    }

    @Override
    public void create(CustomResultSet<Book> customResultSet) {
        List<Long> authorsId = (List<Long>) customResultSet.getParams().get(0);
        List<Long> genresId = (List<Long>) customResultSet.getParams().get(1);
        if (authorsId.stream().allMatch(authorsDAO::existsById) &&
                genresId.stream().allMatch(genresDAO::existsById)) {
            booksDAO.create(customResultSet);
        }
    }

    @Override
    public void update(CustomResultSet<Book> customResultSet) {
        String isbn = customResultSet.getEntity().getIsbn();
        List<Long> authorsIds = (List<Long>) customResultSet.getParams().get(0);
        List<Long> genresIds = (List<Long>) customResultSet.getParams().get(1);
        if (booksDAO.existsById(isbn) &&
                authorsIds.stream().allMatch(authorsDAO::existsById) &&
                genresIds.stream().allMatch(genresDAO::existsById)) {
            booksDAO.update(customResultSet);
        }
    }

    @Override
    public void delete(String isbn) {
        if (booksDAO.existsById(isbn)) {
            booksDAO.delete(isbn);
        }
    }

    @Override
    public Book findById(String isbn) {
        return booksDAO.findById(isbn);
    }

    @Override
    public DataTableResponse<Book> findAll(DataTableRequest request) {
        DataTableResponse<Book> dataTableResponse = booksDAO.findAll(request);
        int count = booksDAO.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(Long authorId) {
//        if (authorsDAO.existsById(authorId)) {
//            booksDAO.deleteAllByForeignId(authorId);
//        }
    }

    @Override
    public List<Book> findAllByForeignId(Long authorId) {
        if (authorsDAO.existsById(authorId)) {
            return booksDAO.findAllByForeignId(authorId);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteAllBySecondForeignId(Long genreId) {
//        if (genresDAO.existsById(genreId)) {
//            booksDAO.deleteAllBySecondForeignId(genreId);
//        }
    }

    @Override
    public List<Book> findAllBySecondForeignId(Long genreId) {
        if (genresDAO.existsById(genreId)) {
            return booksDAO.findAllBySecondForeignId(genreId);
        }
        return Collections.emptyList();
    }
}
