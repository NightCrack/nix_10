package ua.com.alevel.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Genre;

@Repository
public interface GenreRepository extends BaseRepository<Genre, Long> {

}
