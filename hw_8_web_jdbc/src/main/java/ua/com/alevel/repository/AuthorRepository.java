package ua.com.alevel.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Author;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long> {
}
