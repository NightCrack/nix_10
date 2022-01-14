package ua.com.alevel.service;

import ua.com.alevel.entity.BookInstance;

import java.util.List;

public interface BookInstanceService extends BaseService<BookInstance, Long> {

    List<BookInstance> findAllByBook(String bookId);
}
