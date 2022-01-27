package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.GenreRequestDto;
import ua.com.alevel.view.dto.response.GenreResponseDto;

public interface GenreFacade extends DependentFacade<GenreRequestDto, GenreResponseDto, Long, String> {
}
