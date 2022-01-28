package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Author;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorsDAO authorsDAO;
    private final BooksDAO booksDAO;

    public AuthorServiceImpl(AuthorsDAO authorsDAO, BooksDAO booksDAO) {
        this.authorsDAO = authorsDAO;
        this.booksDAO = booksDAO;
    }

    @Override
    public void create(CustomResultSet<Author> author) {
        authorsDAO.create(author);
    }

    @Override
    public void update(CustomResultSet<Author> customResultSet) {
        Long id = customResultSet.getEntity().getId();
        List<String> isbnList = (List<String>) customResultSet.getParams().get(0);
        if (authorsDAO.existsById(id) &&
                (isbnList.isEmpty() || isbnList.stream().allMatch(booksDAO::existsById))) {
            authorsDAO.update(customResultSet);
        }
    }

    @Override
    public void delete(Long id) {
        if (authorsDAO.existsById(id)) {
            authorsDAO.delete(id);
        }
    }

    @Override
    public Author findById(Long id) {
        return authorsDAO.findById(id);
    }

    @Override
    public DataTableResponse<Author> findAll(DataTableRequest request) {
        DataTableResponse<Author> dataTableResponse = authorsDAO.findAll(request);
        int count = authorsDAO.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Author> findAllByForeignId(DataTableRequest request, String isbn) {
        if (booksDAO.existsById(isbn)) {
            DataTableResponse<Author> dataTableResponse = authorsDAO.findAllByForeignId(request, isbn);
            int count = authorsDAO.foreignCount(isbn);
            WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
            return dataTableResponse;
        }
        return new DataTableResponse<>();
    }
}
