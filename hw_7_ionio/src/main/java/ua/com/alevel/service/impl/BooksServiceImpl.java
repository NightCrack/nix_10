package ua.com.alevel.service.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BooksDAO;
import ua.com.alevel.entity.Book;
import ua.com.alevel.service.BooksService;

import java.util.Collection;

@Service
public class BooksServiceImpl implements BooksService<String> {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static boolean status;
    @Autowired
    private BooksDAO<String> booksDAO;
    private long counterCreate = 0;
    private long counterUpdate = 0;
    private long counterDelete = 0;
    private long counterFindById = 0;
    private long counterFindAll = 0;

    @Override
    public boolean create(Book book) {
        String tableName = this.getClass().getSimpleName() + "counterCreate";
        counterCreate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterCreate + " create_book");
        status = booksDAO.create(book);
        LOGGER_INFO.info(counterCreate + " result: " + status + ", " + book.getIsbn());
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterCreate));
        return status;
    }

    @Override
    public boolean update(Book book) {
        String tableName = this.getClass().getSimpleName() + "counterUpdate";
        counterUpdate = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterUpdate + " update_book " + book.getIsbn());
        status = booksDAO.update(book);
        LOGGER_INFO.info(counterUpdate + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterUpdate));
        return status;
    }

    @Override
    public boolean delete(String isbn) {
        String tableName = this.getClass().getSimpleName() + "counterDelete";
        counterDelete = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterDelete + " delete_book " + isbn);
        try {
            status = booksDAO.delete(isbn);
        } catch (Exception exception) {
            status = false;
        }
        LOGGER_INFO.info(counterDelete + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterDelete));
        return status;
    }

    @Override
    public Book findById(String isbn) {
        String tableName = this.getClass().getSimpleName() + "counterFindById";
        counterFindById = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindById + " findById_book " + isbn);
        Book returnValue = booksDAO.findById(isbn);
        status = returnValue != null;
        LOGGER_INFO.info(counterFindById + " result: " + status);
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindById));
        return returnValue;
    }

    @Override
    public <COLLECTION extends Collection<Book>> COLLECTION findAll() {
        String tableName = this.getClass().getSimpleName() + "counterFindAll";
        counterFindAll = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), tableName));
        LOGGER_INFO.info(++counterFindAll + " findAll_book");
        ResourcesUtil.setLastId(this.getClass(), tableName, String.valueOf(counterFindAll));
        return booksDAO.findAll();
    }
}
