package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.genre.GenreRequestDto;
import ua.com.alevel.dto.genre.GenreResponseDto;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.facade.GenreFacade;
import ua.com.alevel.service.GenreService;

import java.util.List;
import java.util.Optional;

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
        genreService.create(genre);
    }

    @Override
    public void update(GenreRequestDto genreRequestDto, Long id) {
        Optional<Genre> genreOptional = genreService.findById(id);
        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();
            genre.setGenreType(genreRequestDto.getType());
            genreService.update(genre);
        }
    }

    @Override
    public void delete(Long id) {
        genreService.delete(id);
    }

    @Override
    public GenreResponseDto findById(Long id) {
        Optional<Genre> optionalGenre = genreService.findById(id);
        return optionalGenre.map(GenreResponseDto::new).orElse(null);
    }

    @Override
    public List<GenreResponseDto> findAll() {
        return genreService.findAll()
                .stream()
                .map(GenreResponseDto::new)
                .toList();
    }
}
