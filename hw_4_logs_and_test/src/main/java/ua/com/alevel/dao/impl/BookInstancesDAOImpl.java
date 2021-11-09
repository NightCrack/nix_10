package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.BookInstancesDAO;
import ua.com.alevel.db.impl.BookInstancesImpl;
import ua.com.alevel.entity.BookInstance;

public class BookInstancesDAOImpl implements BookInstancesDAO {

    private final BookInstancesImpl instancesTable = BookInstancesImpl.getInstance();

    @Override
    public void create(BookInstance bookInstance) {
        instancesTable.create(bookInstance);
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        return instancesTable.update(bookInstance);
    }

    @Override
    public boolean delete(Integer id) {
        return instancesTable.delete(id);
    }

    @Override
    public BookInstance findById(Integer id) {
        return instancesTable.findById(id);
    }

    @Override
    public BookInstance[] findAll() {
        return instancesTable.findAll();
    }
}
