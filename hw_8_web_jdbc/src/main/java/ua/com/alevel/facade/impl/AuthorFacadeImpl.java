package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.author.AuthorRequestDto;
import ua.com.alevel.dto.author.AuthorResponseDto;
import ua.com.alevel.entity.Author;
import ua.com.alevel.facade.AuthorFacade;
import ua.com.alevel.service.AuthorService;

import java.util.List;
import java.util.Optional;

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
        author.setDateOfBirth(authorRequestDto.getDateOfBirth());
        author.setDateOfDeath(authorRequestDto.getDateOfDeath());
        authorService.create(author);
    }

    @Override
    public void update(AuthorRequestDto authorRequestDto, Long id) {
        Optional<Author> optionalAuthor = authorService.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setFirstName(authorRequestDto.getFirstName());
            author.setLastName(authorRequestDto.getLastName());
            author.setDateOfBirth(authorRequestDto.getDateOfBirth());
            author.setDateOfDeath(authorRequestDto.getDateOfDeath());
            authorService.update(author);
        }
    }

    @Override
    public void delete(Long id) {
        authorService.delete(id);
    }

    @Override
    public AuthorResponseDto findById(Long id) {
        Optional<Author> optionalAuthor = authorService.findById(id);
        return authorService.findById(id).map(AuthorResponseDto::new).orElse(null);
    }

    @Override
    public List<AuthorResponseDto> findAll() {
        return authorService.findAll()
                .stream()
                .map(AuthorResponseDto::new)
                .toList();
    }
}
