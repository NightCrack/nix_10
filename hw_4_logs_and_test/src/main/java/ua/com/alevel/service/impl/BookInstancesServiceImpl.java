package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.BookInstancesDAOImpl;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.service.BookInstancesService;

public class BookInstancesServiceImpl implements BookInstancesService {

    private final BookInstancesDAOImpl bookInstancesDAO = new BookInstancesDAOImpl();

    @Override
    public void create(BookInstance bookInstance) {
        bookInstancesDAO.create(bookInstance);
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        return bookInstancesDAO.update(bookInstance);
    }

    @Override
    public boolean delete(Integer id) {
        return bookInstancesDAO.delete(id);
    }

    @Override
    public BookInstance findById(Integer id) {
        return bookInstancesDAO.findById(id);
    }

    @Override
    public BookInstance[] findAll() {
        return bookInstancesDAO.findAll();
    }
}
