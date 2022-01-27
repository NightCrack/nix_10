package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Genre;
import ua.com.alevel.type.GenreType;

public final class GenreResponseDto extends ResponseWithIdDto {

    private GenreType genreType;
    private Integer booksCount;

    public GenreResponseDto(Genre genre) {
        genreType = genre.getGenreType();
        this.setCreated(genre.getCreated());
        this.setUpdated(genre.getUpdated());
        this.setVisible(genre.getVisible());
        this.setId(genre.getId());
    }

    public GenreType getGenreType() {
        return genreType;
    }

    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }

    public Integer getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Integer booksCount) {
        this.booksCount = booksCount;
    }
}
