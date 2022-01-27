package ua.com.alevel.persistence.entity;

import ua.com.alevel.type.GenreType;
import ua.com.alevel.view.dto.request.GenreRequestDto;

public final class Genre extends WithIdEntity {
    private GenreType genreType;

    public Genre() {
    }

    public Genre(GenreRequestDto genreRequestDto) {
        this.genreType = genreRequestDto.getType();
    }

    public GenreType getGenreType() {
        return genreType;
    }

    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }
}
