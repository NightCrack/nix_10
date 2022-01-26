package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.AuthorFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Author;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.AuthorRequestDto;
import ua.com.alevel.view.dto.response.AuthorResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorFacadeImpl implements AuthorFacade {

    private final AuthorService authorService;

    public AuthorFacadeImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void create(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setFirstName(authorRequestDto.getFirstName());
        author.setLastName(authorRequestDto.getLastName());
        author.setBirthDate(authorRequestDto.getDateOfBirth());
        author.setDeathDate(authorRequestDto.getDateOfDeath());
        authorService.create(new CustomResultSet<>(author, Collections.emptyList()));
    }

    @Override
    public void update(AuthorRequestDto authorRequestDto, Long id) {
//        Author author = authorService.findById(id);
//        if (author != null) {
//            author.setFirstName(authorRequestDto.getFirstName());
//            author.setLastName(authorRequestDto.getLastName());
//            author.setBirthDate(authorRequestDto.getDateOfBirth());
//            author.setDeathDate(authorRequestDto.getDateOfDeath());
//            authorService.update(author);
//        }
    }

    @Override
    public void delete(Long id) {
        authorService.delete(id);
    }

    @Override
    public AuthorResponseDto findById(Long id) {
        return new AuthorResponseDto(authorService.findById(id));
    }

    @Override
    public PageData<AuthorResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Author> all = authorService.findAll(dataTableRequest);
        List<AuthorResponseDto> items = all.getItems()
                .stream()
                .map(AuthorResponseDto::new)
                .peek(entity -> entity
                        .setBooksCount(all
                                .getOtherParamMap()
                                .get(entity.getId()).get(0)))
                .toList();
        PageData<AuthorResponseDto> pageData = (PageData<AuthorResponseDto>) WebResponseUtil.initPageData(all);
        pageData.setItems(items);
        return pageData;
    }

    @Override
    public List<AuthorResponseDto> findAllByForeignId(String isbn) {
        return authorService
                .findAllByForeignId(isbn)
                .stream()
                .map(AuthorResponseDto::new)
                .toList();
    }
}
