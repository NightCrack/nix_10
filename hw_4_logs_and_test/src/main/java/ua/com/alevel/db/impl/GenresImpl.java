package ua.com.alevel.db.impl;


import ua.com.alevel.db.GenresDB;
import ua.com.alevel.entity.Genre;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


public class GenresImpl implements GenresDB {

    private static GenresImpl instance;
    private final int arraySize = 10;
    private final AtomicInteger count = new AtomicInteger(0);
    private Genre[] genres = new Genre[arraySize];

    private GenresImpl() {
    }

    public static GenresImpl getInstance() {
        if (instance == null) {
            instance = new GenresImpl();
        }
        return instance;
    }

    @Override
    public void create(Genre genre) {
        genre.setId(count.incrementAndGet());
        int genresAmount = 0;
        for (Genre count : genres) {
            if (count != null) {
                genresAmount++;
            }
        }
        if (!(genres.length > genresAmount)) {
            genres = Arrays.copyOf(genres, (genres.length + arraySize));
        }
        genres[genresAmount] = genre;
    }

    @Override
    public boolean update(Genre genre) {
        Genre current = findById(genre.getId());
        if (current != null) {
            current.setName(genre.getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        for (int position = 0; position < genres.length; position++) {
            if (genres[position].getId().equals(id)) {
                for (; position < genres.length - 1; position++) {
                    genres[position] = genres[position + 1];
                }
                genres = Arrays.copyOf(genres, (genres.length - 1));
                return true;
            }
        }
        return false;
    }

    @Override
    public Genre findById(Integer id) {
        for (Genre genre : genres) {
            if ((genre != null) && (genre.getId().equals(id))) {
                return genre;
            }
        }
        return null;
    }

    @Override
    public Genre[] findAll() {
        return genres;
    }
}
