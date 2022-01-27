package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Genre;
import ua.com.alevel.service.GenreService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenresDAO genresDAO;
    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final BookInstancesDAO bookInstancesDAO;

    public GenreServiceImpl(GenresDAO genresDAO, BooksDAO booksDAO, AuthorsDAO authorsDAO, BookInstancesDAO bookInstancesDAO) {
        this.genresDAO = genresDAO;
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
        this.bookInstancesDAO = bookInstancesDAO;
    }

    @Override
    public void create(CustomResultSet<Genre> genre) {
        genresDAO.create(genre);
    }

    @Override
    public void update(CustomResultSet<Genre> customResultSet) {
        Long id = customResultSet.getEntity().getId();
        List<String> isbnList = (List<String>) customResultSet.getParams().get(0);
        if (genresDAO.existsById(id) && isbnList.stream().allMatch(booksDAO::existsById)) {
            genresDAO.update(customResultSet);
        }
    }

    @Override
    public void delete(Long id) {
        if (genresDAO.existsById(id)) {
            genresDAO.delete(id);
        }
    }

    @Override
    public Genre findById(Long id) {
        return genresDAO.findById(id);
    }

    @Override
    public DataTableResponse<Genre> findAll(DataTableRequest request) {
        DataTableResponse<Genre> dataTableResponse = genresDAO.findAll(request);
        int count = genresDAO.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(String s) {

    }

    @Override
    public List<Genre> findAllByForeignId(String isbn) {
        return genresDAO.findAllByForeignId(isbn);
    }
}
