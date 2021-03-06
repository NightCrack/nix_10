package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.BookRequestDto;
import ua.com.alevel.view.dto.response.BookResponseDto;

public interface BookFacade extends SecondDependentFacade<BookRequestDto, BookResponseDto, String, Long, Long> {
}
