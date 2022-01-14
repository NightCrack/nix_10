package ua.com.alevel.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;

import java.util.List;

@Repository
public interface BookInstanceRepository extends BaseRepository<BookInstance, Long> {

    List<BookInstance> findAllByBook(Book book);
}
