package ua.com.alevel.persistence.entity;

import ua.com.alevel.type.GenreType;

public final class Genre extends WithIdEntity {

    private GenreType genreType;

    public GenreType getGenreType() {
        return genreType;
    }

    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }
}
