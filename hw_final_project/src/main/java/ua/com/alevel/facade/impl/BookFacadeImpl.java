package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.BookRequestDto;
import ua.com.alevel.view.dto.response.BookResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookFacadeImpl implements BookFacade {

    private final BookService bookService;

    public BookFacadeImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void create(BookRequestDto bookRequestDto) {
        List<Long> authorsId = bookRequestDto.getAuthors();
        List<Long> genresId = bookRequestDto.getGenres();
        List<List<?>> references = new ArrayList<>();
        references.add(authorsId);
        references.add(genresId);
        CustomResultSet<Book> createRequest = new CustomResultSet<>(new Book(bookRequestDto), references);
        bookService.create(createRequest);
    }

    @Override
    public void update(BookRequestDto bookRequestDto, String isbn) {
        Book book = new Book(bookRequestDto);
        book.setIsbn(isbn);
        List<Long> authorsId = bookRequestDto.getAuthors();
        List<Long> genresId = bookRequestDto.getGenres();
        List<List<?>> references = new ArrayList<>();
        references.add(authorsId);
        references.add(genresId);
        bookService.update(new CustomResultSet<>(book, references));
    }

    @Override
    public void delete(String isbn) {
        bookService.delete(isbn);
    }

    @Override
    public BookResponseDto findById(String isbn) {
        return new BookResponseDto(bookService.findById(isbn));
    }

    @Override
    public PageData<BookResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Book> all = bookService.findAll(dataTableRequest);
        PageData<BookResponseDto> pageData = findAllByResponse(all);
//        List<BookResponseDto> items = all.getItems()
//                .stream()
//                .map(BookResponseDto::new)
//                .peek(entity -> {
//                    entity.setAuthorsCount(all
//                            .getOtherParamMap()
//                            .get(entity.getIsbn()).get(0));
//                    entity.setGenresCount(all
//                            .getOtherParamMap()
//                            .get(entity.getIsbn()).get(1));
//                    entity.setBookInstancesCount(all
//                            .getOtherParamMap()
//                            .get(entity.getIsbn()).get(2));
//                })
//                .toList();
//        PageData<BookResponseDto> pageData = (PageData<BookResponseDto>) WebResponseUtil.initPageData(all);
//        pageData.setItems(items);
        return pageData;
    }

    @Override
    public PageData<BookResponseDto> findAllByForeignId(WebRequest request, Long authorId) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Book> all = bookService.findAllByForeignId(dataTableRequest, authorId);
        PageData<BookResponseDto> pageData = findAllByResponse(all);
        return pageData;
    }

    @Override
    public PageData<BookResponseDto> findAllBySecondForeignId(WebRequest request, Long genreId) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Book> all = bookService.findAllBySecondForeignId(dataTableRequest, genreId);
        PageData<BookResponseDto> pageData = findAllByResponse(all);
        return pageData;
    }

    private PageData<BookResponseDto> findAllByResponse(DataTableResponse<Book> all) {
        List<BookResponseDto> items = all.getItems()
                .stream()
                .map(BookResponseDto::new)
                .peek(entity -> {
                    entity.setAuthorsCount(all
                            .getOtherParamMap()
                            .get(entity.getIsbn()).get(0));
                    entity.setGenresCount(all
                            .getOtherParamMap()
                            .get(entity.getIsbn()).get(1));
                    entity.setBookInstancesCount(all
                            .getOtherParamMap()
                            .get(entity.getIsbn()).get(2));
                })
                .toList();
        PageData<BookResponseDto> pageData = (PageData<BookResponseDto>) WebResponseUtil.initPageData(all);
        pageData.setItems(items);
        return pageData;
    }
}
