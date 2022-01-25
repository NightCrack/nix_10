package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.BookInstanceRequestDto;
import ua.com.alevel.view.dto.response.BookInstanceResponseDto;

public interface BookInstanceFacade extends DependentFacade<BookInstanceRequestDto, BookInstanceResponseDto, Long, String> {
}
