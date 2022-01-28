package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Book;

public interface BooksDAO extends SecondDependentDAO<Book, String, Long, Long> {
}
