package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.AuthorsDAOImpl;
import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorsService;

public class AuthorsServiceImpl implements AuthorsService {

    private final AuthorsDAOImpl authorsDAO = new AuthorsDAOImpl();

    @Override
    public void create(Author author) {
        authorsDAO.create(author);
    }

    @Override
    public boolean update(Author author) {
        return authorsDAO.update(author);
    }

    @Override
    public boolean delete(Integer id) {
        return authorsDAO.delete(id);
    }

    @Override
    public Author findById(Integer id) {
        return authorsDAO.findById(id);
    }

    @Override
    public Author[] findAll() {
        return authorsDAO.findAll();
    }
}
