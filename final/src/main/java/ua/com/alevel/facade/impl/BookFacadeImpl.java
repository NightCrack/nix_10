package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookService;
import ua.com.alevel.service.GenreService;
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
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookFacadeImpl(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
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
//        Author author = authorService.findById(bookRequestDto.getAuthorId());
//        List<Genre> genres = bookRequestDto.getGenreIds().stream().map(genreService::findById).toList();
//        Book book = bookService.findById(isbn);
//        if ((book != null) &&
//                (author != null) &&
//                genres.stream().allMatch(Objects::nonNull)) {
//            book.setIsbn(bookRequestDto.getIsbn());
//            book.setTitle(bookRequestDto.getName());
//            book.setAuthor(author);
//            book.setGenre(genres);
//            book.setSummary(bookRequestDto.getSummary());
//            bookService.update(book);
//        }
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

    @Override
    public List<BookResponseDto> findAllByForeignId(Long authorId) {
        return generateDtoListByEntities(bookService.findAllByForeignId(authorId));
    }

    @Override
    public List<BookResponseDto> findAllBySecondForeignId(Long genreId) {
        return generateDtoListByEntities(bookService.findAllBySecondForeignId(genreId));
    }

    private List<BookResponseDto> generateDtoListByEntities(List<Book> list) {
        return list.stream()
                .map(BookResponseDto::new)
                .toList();
    }
}
