package ua.com.alevel.dto.genre;

import ua.com.alevel.dto.RequestDto;
import ua.com.alevel.type.GenreType;

public final class GenreRequestDto extends RequestDto {

    private GenreType type;

    public GenreType getType() {
        return type;
    }

    public void setType(GenreType type) {
        this.type = type;
    }

}
