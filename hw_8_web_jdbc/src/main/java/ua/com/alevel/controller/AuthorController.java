package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.dto.author.AuthorRequestDto;
import ua.com.alevel.dto.author.AuthorResponseDto;
import ua.com.alevel.facade.AuthorFacade;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController implements BaseController<AuthorRequestDto, Long> {

    private final AuthorFacade authorFacade;

    public AuthorController(AuthorFacade authorFacade) {
        this.authorFacade = authorFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<AuthorResponseDto> authors = authorFacade.findAll();
        model.addAttribute("authors", authors);
        return "pages/authors/authors_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
        model.addAttribute("author", new AuthorRequestDto());
        return "pages/authors/authors_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("author") AuthorRequestDto dto) {
        authorFacade.create(dto);
        return "redirect:/authors";
    }

    @Override
    public String deleteEntity(Long aLong) {
        return null;
    }

    @Override
    public String getEntityDetails(Long aLong, Model model) {
        return null;
    }
}
