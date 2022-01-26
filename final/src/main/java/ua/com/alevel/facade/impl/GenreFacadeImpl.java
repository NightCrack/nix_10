package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.GenreFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Genre;
import ua.com.alevel.service.GenreService;
import ua.com.alevel.util.CustomResultSet;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.GenreRequestDto;
import ua.com.alevel.view.dto.response.GenreResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;

@Service
public class GenreFacadeImpl implements GenreFacade {

    private final GenreService genreService;

    public GenreFacadeImpl(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void create(GenreRequestDto genreRequestDto) {
        Genre genre = new Genre();
        genre.setGenreType(genreRequestDto.getType());
        genreService.create(new CustomResultSet<>(genre, null));
    }

    @Override
    public void update(GenreRequestDto genreRequestDto, Long id) {
//        -62167399200000
//        Genre genre = genreService.findById(id);
//        if (genre != null) {
//            genre.setGenreType(genreRequestDto.getType());
//            genreService.update(genre);
//        }
    }

    @Override
    public void delete(Long id) {
        genreService.delete(id);
    }

    @Override
    public GenreResponseDto findById(Long id) {
        return new GenreResponseDto(genreService.findById(id));
    }

    @Override
    public PageData<GenreResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Genre> all = genreService.findAll(dataTableRequest);
        List<GenreResponseDto> items = all.getItems()
                .stream()
                .map(GenreResponseDto::new)
                .peek(entity -> entity
                        .setBooksCount(all
                                .getOtherParamMap()
                                .get(entity.getId()).get(0)))
                .toList();
        PageData<GenreResponseDto> pageData = (PageData<GenreResponseDto>) WebResponseUtil.initPageData(all);
        pageData.setItems(items);
        return pageData;
    }

    @Override
    public List<GenreResponseDto> findAllByForeignId(String isbn) {
        return genreService
                .findAllByForeignId(isbn)
                .stream()
                .map(GenreResponseDto::new)
                .toList();
    }
}
