package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Book;

public interface BookService extends SecondDependentService<Book, String, Long, Long> {
}
