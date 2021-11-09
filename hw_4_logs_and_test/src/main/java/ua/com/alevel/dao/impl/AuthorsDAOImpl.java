package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.AuthorsDAO;
import ua.com.alevel.db.impl.AuthorsImpl;
import ua.com.alevel.entity.Author;

public class AuthorsDAOImpl implements AuthorsDAO {

    private final AuthorsImpl authorsTable = AuthorsImpl.getInstance();

    @Override
    public void create(Author author) {
        authorsTable.create(author);
    }

    @Override
    public boolean update(Author author) {
        return authorsTable.update(author);
    }

    @Override
    public boolean delete(Integer id) {
        return authorsTable.delete(id);
    }

    @Override
    public Author findById(Integer id) {
        return authorsTable.findById(id);
    }

    @Override
    public Author[] findAll() {
        return authorsTable.findAll();
    }
}
