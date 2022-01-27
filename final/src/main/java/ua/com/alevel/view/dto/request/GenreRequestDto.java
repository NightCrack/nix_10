package ua.com.alevel.view.dto.request;

import ua.com.alevel.type.GenreType;
import ua.com.alevel.view.dto.response.GenreResponseDto;

import java.util.List;

public final class GenreRequestDto extends RequestDto {

    private GenreType type;
    private List<String> isbnList;

    public GenreRequestDto() {
    }

    public GenreRequestDto(GenreResponseDto responseDto) {
        this.type = responseDto.getGenreType();
    }

    public GenreType getType() {
        return type;
    }

    public void setType(GenreType type) {
        this.type = type;
    }

    public List<String> getIsbnList() {
        return isbnList;
    }

    public void setIsbnList(List<String> isbnList) {
        this.isbnList = isbnList;
    }
}
