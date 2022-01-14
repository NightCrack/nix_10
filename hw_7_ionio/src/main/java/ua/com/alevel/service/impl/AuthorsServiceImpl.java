package ua.com.alevel.service.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.AuthorsDAO;
import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorsService;

import java.util.Collection;

@Service
public class AuthorsServiceImpl implements AuthorsService<Long> {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    @Autowired
    private AuthorsDAO<Long> authorsDAO;
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public boolean create(Author author) {
        String tableName = this.getClass().getSimpleName() + "counterCreate";
        counterCreate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterCreate + " create_author");
        status = authorsDAO.create(author);
        LOGGER_INFO.info(counterCreate + " result: " + status + ", " + author.getId());
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterCreate));
        return status;
    }

    @Override
    public boolean update(Author author) {
        String tableName = this.getClass().getSimpleName() + "counterUpdate";
        counterUpdate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterUpdate));
        LOGGER_INFO.info(++counterUpdate + " update_author " + author.getId());
        try {
            status = authorsDAO.update(author);
        } catch (Exception exception) {
            status = false;
        }
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        return status;
    }

    @Override
    public boolean delete(Long id) {
        String tableName = this.getClass().getSimpleName() + "counterDelete";
        counterDelete = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterDelete + " delete_author " + id);
        try {
            status = authorsDAO.delete(id);
        } catch (Exception exception) {
            status = false;
        }
        LOGGER_INFO.info(counterDelete + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterDelete));
        return status;
    }

    @Override
    public Author findById(Long id) {
        String tableName = this.getClass().getSimpleName() + "counterFindById";
        counterFindById = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindById + " findById_author " + id);
        Author returnValue = authorsDAO.findById(id);
        status = returnValue != null;
        LOGGER_INFO.info(counterFindById + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindById));
        return returnValue;
    }

    @Override
    public <COLLECTION extends Collection<Author>> COLLECTION findAll() {
        String tableName = this.getClass().getSimpleName() + "counterFindAll";
        counterFindAll = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindAll + " findAll_author");
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindAll));
        return authorsDAO.findAll();
    }
}
