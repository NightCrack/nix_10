package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.GenresDAO;
import ua.com.alevel.db.impl.GenresImpl;
import ua.com.alevel.entity.Genre;

public class GenresDAOImpl implements GenresDAO {

    private final GenresImpl genresTable = GenresImpl.getInstance();


    @Override
    public void create(Genre genre) {
        genresTable.create(genre);
    }

    @Override
    public boolean update(Genre genre) {
        return genresTable.update(genre);
    }

    @Override
    public boolean delete(Integer id) {
        return genresTable.delete(id);
    }

    @Override
    public Genre findById(Integer id) {
        return genresTable.findById(id);
    }

    @Override
    public Genre[] findAll() {
        return genresTable.findAll();
    }
}
