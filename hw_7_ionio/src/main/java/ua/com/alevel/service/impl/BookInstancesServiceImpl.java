package ua.com.alevel.service.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BookInstancesDAO;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.service.BookInstancesService;

import java.util.Collection;

@Service
public class BookInstancesServiceImpl implements BookInstancesService<Long> {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    @Autowired
    private BookInstancesDAO<Long> bookInstancesDAO;
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public boolean create(BookInstance bookInstance) {
        String tableName = this.getClass().getSimpleName() + "counterCreate";
        counterCreate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterCreate + " create_bookInstance");
        status = bookInstancesDAO.create(bookInstance);
        LOGGER_INFO.info(counterCreate + " result: " + status + ", " + bookInstance.getId());
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterCreate));
        return status;
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        String tableName = this.getClass().getSimpleName() + "counterUpdate";
        counterUpdate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterUpdate + " update_bookInstance " + bookInstance.getId());
        try {
            status = bookInstancesDAO.update(bookInstance);
        } catch (Exception exception) {
            status = false;
        }
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterUpdate));
        return status;
    }

    @Override
    public boolean delete(Long id) {
        String tableName = this.getClass().getSimpleName() + "counterDelete";
        counterDelete = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterDelete + " delete_bookInstance " + id);
        try {
            status = bookInstancesDAO.delete(id);
        } catch (Exception exception) {
            status = false;
        }
        LOGGER_INFO.info(counterDelete + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterDelete));
        return status;
    }

    @Override
    public BookInstance findById(Long id) {
        String tableName = this.getClass().getSimpleName() + "counterFindById";
        counterFindById = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindById + " findById_bookInstance " + id);
        BookInstance returnValue = bookInstancesDAO.findById(id);
        status = returnValue != null;
        LOGGER_INFO.info(counterFindById + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindById));
        return returnValue;
    }

    @Override
    public <COLLECTION extends Collection<BookInstance>> COLLECTION findAll() {
        String tableName = this.getClass().getSimpleName() + "counterFindAll";
        counterFindAll = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindAll + " findAll_bookInstance");
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindAll));
        return bookInstancesDAO.findAll();
    }
}
