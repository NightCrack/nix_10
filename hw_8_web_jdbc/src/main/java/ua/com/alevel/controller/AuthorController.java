package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.author.AuthorRequestDto;
import ua.com.alevel.dto.author.AuthorResponseDto;
import ua.com.alevel.facade.AuthorFacade;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController implements BaseController<AuthorRequestDto, Long, Object> {

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
    public String findAllByEntity(Object o, Model model) {
        return null;
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
    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable Long id) {
        authorFacade.delete(id);
        return "redirect:/authors";

    }

    @Override
    @GetMapping("/details/{id}")
    public String getEntityDetails(@PathVariable Long id, Model model) {
        AuthorResponseDto dto = authorFacade.findById(id);
        model.addAttribute("author", dto);
        return "pages/authors/authors_details";

    }
}
