package ua.com.alevel.facade;

import ua.com.alevel.dto.book.BookRequestDto;
import ua.com.alevel.dto.book.BookResponseDto;

public interface BookFacade extends BaseFacade<BookRequestDto, BookResponseDto, String> {
}
