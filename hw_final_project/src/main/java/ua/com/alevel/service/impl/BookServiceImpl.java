package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final GenresDAO genresDAO;

    public BookServiceImpl(BooksDAO booksDAO, AuthorsDAO authorsDAO, GenresDAO genresDAO) {
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
        this.genresDAO = genresDAO;
    }

    @Override
    public void create(CustomResultSet<Book> customResultSet) {
        List<Long> authorsIds = (List<Long>) customResultSet.getParams().get(0);
        List<Long> genresIds = (List<Long>) customResultSet.getParams().get(1);
        if ((authorsIds.isEmpty() || authorsIds.stream().allMatch(authorsDAO::existsById)) &&
                (genresIds.isEmpty() || genresIds.stream().allMatch(genresDAO::existsById))) {
            booksDAO.create(customResultSet);
        }
    }

    @Override
    public void update(CustomResultSet<Book> customResultSet) {
        String isbn = customResultSet.getEntity().getIsbn();
        List<Long> authorsIds = (List<Long>) customResultSet.getParams().get(0);
        List<Long> genresIds = (List<Long>) customResultSet.getParams().get(1);
        if (booksDAO.existsById(isbn) &&
                (authorsIds.isEmpty() || authorsIds.stream().allMatch(authorsDAO::existsById)) &&
                (genresIds.isEmpty() || genresIds.stream().allMatch(genresDAO::existsById))) {
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
    public DataTableResponse<Book> findAllByForeignId(DataTableRequest request, Long authorId) {
        if (authorsDAO.existsById(authorId)) {
            DataTableResponse<Book> dataTableResponse = booksDAO.findAllByForeignId(request, authorId);
            int count = booksDAO.foreignCount(authorId);
            WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
            return dataTableResponse;
        }
        return new DataTableResponse<>();
    }

    @Override
    public DataTableResponse<Book> findAllBySecondForeignId(DataTableRequest request, Long genreId) {
        if (genresDAO.existsById(genreId)) {
            DataTableResponse<Book> dataTableResponse = booksDAO.findAllBySecondForeignId(request, genreId);
            int count = booksDAO.secondForeignCount(genreId);
            WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
            return dataTableResponse;
        }
        return new DataTableResponse<>();
    }
}
