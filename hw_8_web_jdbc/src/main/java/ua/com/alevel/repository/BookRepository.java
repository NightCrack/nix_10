package ua.com.alevel.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.Genre;

import java.util.List;

@Repository
public interface BookRepository extends BaseRepository<Book, String> {

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByGenre(Genre genre);
}
