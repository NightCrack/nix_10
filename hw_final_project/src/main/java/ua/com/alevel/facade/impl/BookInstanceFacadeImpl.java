package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.persistence.entity.BookInstance;
import ua.com.alevel.service.BookInstanceService;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.BookInstanceRequestDto;
import ua.com.alevel.view.dto.response.BookInstanceResponseDto;
import ua.com.alevel.view.dto.response.BookResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;

@Service
public class BookInstanceFacadeImpl implements BookInstanceFacade {

    private final BookInstanceService bookInstanceService;
    private final BookService bookService;

    public BookInstanceFacadeImpl(BookInstanceService bookInstanceService, BookService bookService) {
        this.bookInstanceService = bookInstanceService;
        this.bookService = bookService;
    }

    @Override
    public void create(BookInstanceRequestDto bookInstanceRequestDto) {
        Book book = bookService.findById(bookInstanceRequestDto.getBookIsbn());
        BookInstance bookInstance = new BookInstance(bookInstanceRequestDto, book);
        bookInstanceService.create(new CustomResultSet<>(bookInstance, null));
    }

    @Override
    public void update(BookInstanceRequestDto bookInstanceRequestDto, Long id) {
        Book book = bookService.findById(bookInstanceRequestDto.getBookIsbn());
        BookInstance bookInstance = new BookInstance(bookInstanceRequestDto, book);
        bookInstance.setId(id);
        bookInstanceService.update(new CustomResultSet<>(bookInstance, null));
    }

    @Override
    public void delete(Long id) {
        bookInstanceService.delete(id);
    }

    @Override
    public BookInstanceResponseDto findById(Long id) {
        return new BookInstanceResponseDto(bookInstanceService.findById(id));
    }

    @Override
    public PageData<BookInstanceResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<BookInstance> all = bookInstanceService.findAll(dataTableRequest);
        PageData<BookInstanceResponseDto> pageData = findAllByResponse(all);
        return pageData;
    }

    @Override
    public PageData<BookInstanceResponseDto> findAllByForeignId(WebRequest request, String bookId) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<BookInstance> all = bookInstanceService.findAllByForeignId(dataTableRequest, bookId);
        PageData<BookInstanceResponseDto> pageData = findAllByResponse(all);
        return pageData;
    }

    private PageData<BookInstanceResponseDto> findAllByResponse(DataTableResponse<BookInstance> all) {
        List<BookInstanceResponseDto> items = all.getItems()
                .stream()
                .map(BookInstanceResponseDto::new)
                .toList();
        PageData<BookInstanceResponseDto> pageData = (PageData<BookInstanceResponseDto>) WebResponseUtil.initPageData(all);
        pageData.setItems(items);
        return pageData;
    }
}
