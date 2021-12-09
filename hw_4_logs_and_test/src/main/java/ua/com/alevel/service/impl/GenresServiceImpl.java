package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.GenresDAOImpl;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.service.GenresService;

public class GenresServiceImpl implements GenresService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    private final GenresDAOImpl genresDAO = new GenresDAOImpl();
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public void create(Genre genre) {
        LOGGER_INFO.info(++counterCreate + " create_genre");
        genresDAO.create(genre);
        LOGGER_INFO.info(counterCreate + " result: " + genre.getId());
    }

    @Override
    public boolean update(Genre genre) {
        LOGGER_INFO.info(++counterUpdate + " update_genre " + genre.getId());
        status = genresDAO.update(genre);
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        return status;
    }

    @Override
    public boolean delete(Integer id) {
        LOGGER_INFO.info(++counterDelete + " delete_genre " + id);
        status = genresDAO.delete(id);
        LOGGER_INFO.info(counterDelete + " result: " + status);
        return status;
    }

    @Override
    public Genre findById(Integer id) {
        LOGGER_INFO.info(++counterFindById + " findById_genre " + id);
        return genresDAO.findById(id);
    }

    @Override
    public Genre[] findAll() {
        LOGGER_INFO.info(++counterFindAll + " findAll_genre");
        return genresDAO.findAll();
    }
}
