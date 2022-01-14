package ua.com.alevel.service;

import ua.com.alevel.entity.Book;

import java.util.List;

public interface BookService extends BaseService<Book, String> {

    List<Book> findAllByAuthor(Long authorId);
}
