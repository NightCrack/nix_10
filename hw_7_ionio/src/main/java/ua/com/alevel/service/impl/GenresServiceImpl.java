package ua.com.alevel.service.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.GenresDAO;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.service.GenresService;

import java.util.Collection;

@Service
public class GenresServiceImpl implements GenresService<Long> {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    @Autowired
    private GenresDAO<Long> genresDAO;
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public boolean create(Genre genre) {
        String tableName = this.getClass().getSimpleName() + "counterCreate";
        counterCreate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterCreate + " create_genre");
        status = genresDAO.create(genre);
        LOGGER_INFO.info(counterCreate + " result: " + status + ", " + genre.getId());
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterCreate));
        return status;
    }

    @Override
    public boolean update(Genre genre) {
        String tableName = this.getClass().getSimpleName() + "counterUpdate";
        counterUpdate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterUpdate + " update_genre " + genre.getId());
        try {
            status = genresDAO.update(genre);
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
        LOGGER_INFO.info(++counterDelete + " delete_genre " + id);
        try {
            status = genresDAO.delete(id);
        } catch (Exception exception) {
            status = false;
        }
        LOGGER_INFO.info(counterDelete + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterDelete));
        return status;
    }

    @Override
    public Genre findById(Long id) {
        String tableName = this.getClass().getSimpleName() + "counterFindById";
        counterFindById = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindById + " findById_genre " + id);
        Genre returnValue = genresDAO.findById(id);
        status = returnValue != null;
        LOGGER_INFO.info(counterFindById + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindById));
        return returnValue;
    }

    @Override
    public <COLLECTION extends Collection<Genre>> COLLECTION findAll() {
        String tableName = this.getClass().getSimpleName() + "counterFindAll";
        counterFindAll = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindAll + " findAll_genre");
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindAll));
        return genresDAO.findAll();
    }
}
