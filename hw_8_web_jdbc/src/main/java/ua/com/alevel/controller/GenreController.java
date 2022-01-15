package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.bookInstance.BookInstanceResponseDto;
import ua.com.alevel.dto.genre.GenreRequestDto;
import ua.com.alevel.dto.genre.GenreResponseDto;
import ua.com.alevel.facade.GenreFacade;
import ua.com.alevel.type.GenreType;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController implements BaseController<GenreRequestDto, Long, Object> {

    private final GenreFacade genreFacade;

    public GenreController(GenreFacade genreFacade) {
        this.genreFacade = genreFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<GenreResponseDto> genres = genreFacade.findAll();
        model.addAttribute("genres", genres);
        return "pages/genres/genres_all";
    }

    @Override
    public String findAllByEntity(Object o, Model model) {
        return null;
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
        model.addAttribute("genre", new GenreRequestDto());
        model.addAttribute("types", GenreType.values());
        return "pages/genres/genres_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("genre") GenreRequestDto dto) {
        genreFacade.create(dto);
        return "redirect:/genres";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable Long id) {
        genreFacade.delete(id);
        return "redirect:/genres";

    }

    @Override
    @GetMapping("/details/{id}")
    public String getEntityDetails(@PathVariable Long id, Model model) {
        GenreResponseDto dto = genreFacade.findById(id);
        model.addAttribute("genre",dto);
        return "pages/genres/genres_details";
    }
}
