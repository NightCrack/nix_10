package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BookInstance;
import ua.com.alevel.service.BookInstanceService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Collections;
import java.util.List;

@Service
public class BookInstanceServiceImpl implements BookInstanceService {

    private final BookInstancesDAO bookInstancesDAO;
    private final BooksDAO booksDAO;

    public BookInstanceServiceImpl(BookInstancesDAO bookInstancesDAO, BooksDAO booksDAO) {
        this.bookInstancesDAO = bookInstancesDAO;
        this.booksDAO = booksDAO;
    }

    @Override
    public void create(CustomResultSet<BookInstance> resultSet) {
        if (resultSet.getEntity().getBook() != null) {
            bookInstancesDAO.create(resultSet);
        }
    }

    @Override
    public void update(BookInstance bookInstance) {
//        if (bookInstancesDAO.existsById(bookInstance.getId())) {
//            bookInstancesDAO.update(bookInstance);
//        }
    }

    @Override
    public void delete(Long id) {
//        if (bookInstancesDAO.existsById(id)) {
//            BookInstance bookInstance = bookInstancesDAO.findById(id);
//            Book book = bookInstance.getBook();
//            book.getBookInstances().remove(bookInstance);
//            booksDAO.update(book);
//            bookInstancesDAO.delete(id);
//        }
    }

    @Override
    public BookInstance findById(Long id) {
        return bookInstancesDAO.findById(id);
    }

    @Override
    public DataTableResponse<BookInstance> findAll(DataTableRequest request) {
        DataTableResponse<BookInstance> dataTableResponse = bookInstancesDAO.findAll(request);
        int count = bookInstancesDAO.count();
        WebResponseUtil.initDataTableResponse(request,dataTableResponse,count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(String bookId) {
        if (booksDAO.existsById(bookId)) {
            bookInstancesDAO.deleteAllByForeignId(bookId);
        }
    }

    @Override
    public List<BookInstance> findAllByForeignId(String bookId) {
        if (booksDAO.existsById(bookId)) {
            return bookInstancesDAO.findAllByForeignId(bookId);
        }
        return Collections.emptyList();
    }
}
