package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BookInstance;
import ua.com.alevel.service.BookInstanceService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebResponseUtil;

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
    public void update(CustomResultSet<BookInstance> customResultSet) {
        Long id = customResultSet.getEntity().getId();
        String isbn = customResultSet.getEntity().getBook().getIsbn();
        if (bookInstancesDAO.existsById(id) && booksDAO.existsById(isbn)) {
            bookInstancesDAO.update(customResultSet);
        }
    }

    @Override
    public void delete(Long id) {
        if (bookInstancesDAO.existsById(id)) {
            bookInstancesDAO.delete(id);
        }
    }

    @Override
    public BookInstance findById(Long id) {
        return bookInstancesDAO.findById(id);
    }

    @Override
    public DataTableResponse<BookInstance> findAll(DataTableRequest request) {
        DataTableResponse<BookInstance> dataTableResponse = bookInstancesDAO.findAll(request);
        int count = bookInstancesDAO.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<BookInstance> findAllByForeignId(DataTableRequest request, String bookId) {
        if (booksDAO.existsById(bookId)) {
            DataTableResponse<BookInstance> dataTableResponse = bookInstancesDAO.findAllByForeignId(request, bookId);
            int count = bookInstancesDAO.foreignCount(bookId);
            WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
            return dataTableResponse;
        }
        return new DataTableResponse<>();
    }
}
