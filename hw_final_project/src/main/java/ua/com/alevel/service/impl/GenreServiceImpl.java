package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
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

    public GenreServiceImpl(GenresDAO genresDAO, BooksDAO booksDAO) {
        this.genresDAO = genresDAO;
        this.booksDAO = booksDAO;
    }

    @Override
    public void create(CustomResultSet<Genre> genre) {
        genresDAO.create(genre);
    }

    @Override
    public void update(CustomResultSet<Genre> customResultSet) {
        Long id = customResultSet.getEntity().getId();
        List<String> isbnList = (List<String>) customResultSet.getParams().get(0);
        if (genresDAO.existsById(id) &&
                (isbnList.isEmpty() || isbnList.stream().allMatch(booksDAO::existsById))) {
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
    public DataTableResponse<Genre> findAllByForeignId(DataTableRequest request, String isbn) {
        if (booksDAO.existsById(isbn)) {
            DataTableResponse<Genre> dataTableResponse = genresDAO.findAllByForeignId(request, isbn);
            int count = genresDAO.foreignCount(isbn);
            WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
            return dataTableResponse;
        }
        return new DataTableResponse<>();
    }
}
