package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.GenresDAOImpl;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.service.GenresService;

public class GenresServiceImpl implements GenresService {

    private final GenresDAOImpl genresDAO = new GenresDAOImpl();

    @Override
    public void create(Genre genre) {
        genresDAO.create(genre);
    }

    @Override
    public boolean update(Genre genre) {
        return genresDAO.update(genre);
    }

    @Override
    public boolean delete(Integer id) {
        return genresDAO.delete(id);
    }

    @Override
    public Genre findById(Integer id) {
        return genresDAO.findById(id);
    }

    @Override
    public Genre[] findAll() {
        return genresDAO.findAll();
    }
}
