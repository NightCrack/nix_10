package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.BookInstancesDAOImpl;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.service.BookInstancesService;

public class BookInstancesServiceImpl implements BookInstancesService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    private final BookInstancesDAOImpl bookInstancesDAO = new BookInstancesDAOImpl();
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public void create(BookInstance bookInstance) {
        LOGGER_INFO.info(++counterCreate + " create_bookInstance");
        bookInstancesDAO.create(bookInstance);
        LOGGER_INFO.info(counterCreate + " result: " + bookInstance.getId());
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        LOGGER_INFO.info(++counterUpdate + " update_bookInstance " + bookInstance.getId());
        status = bookInstancesDAO.update(bookInstance);
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        return status;
    }

    @Override
    public boolean delete(Integer id) {
        LOGGER_INFO.info(++counterDelete + " delete_bookInstance " + id);
        status = bookInstancesDAO.delete(id);
        LOGGER_INFO.info(counterDelete + " result: " + status);
        return status;
    }

    @Override
    public BookInstance findById(Integer id) {
        LOGGER_INFO.info(++counterFindById + " findById_bookInstance " + id);
        return bookInstancesDAO.findById(id);
    }

    @Override
    public BookInstance[] findAll() {
        LOGGER_INFO.info(++counterFindAll + " findAll_bookInstance");
        return bookInstancesDAO.findAll();
    }
}
