package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.AuthorsDAOImpl;
import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorsService;

public class AuthorsServiceImpl implements AuthorsService {

    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;
    private static boolean status;
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    private final AuthorsDAOImpl authorsDAO = new AuthorsDAOImpl();

    @Override
    public void create(Author author) {
        LOGGER_INFO.info(++counterCreate + " create_author");
        authorsDAO.create(author);
        LOGGER_INFO.info(counterCreate + " result: " + author.getId());
    }

    @Override
    public boolean update(Author author) {
        LOGGER_INFO.info(++counterUpdate + " update_author " + author.getId());
        status = authorsDAO.update(author);
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        return status;
    }

    @Override
    public boolean delete(Integer id) {
        LOGGER_INFO.info(++counterDelete + " delete_author " + id);
        status = authorsDAO.delete(id);
        LOGGER_INFO.info(counterDelete + " result: " + status);
        return status;

    }

    @Override
    public Author findById(Integer id) {
        LOGGER_INFO.info(++counterFindById + " findById_author " + id);
        return authorsDAO.findById(id);
    }

    @Override
    public Author[] findAll() {
        LOGGER_INFO.info(++counterFindAll + " findAll_author");
        return authorsDAO.findAll();
    }
}
