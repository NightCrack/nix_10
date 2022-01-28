package ua.com.alevel.view.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.facade.GenreFacade;
import ua.com.alevel.type.GenreType;
import ua.com.alevel.view.controller.DependentController;
import ua.com.alevel.view.dto.request.GenreRequestDto;
import ua.com.alevel.view.dto.response.GenreResponseDto;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/genres")
public class GenreController extends BaseControllerImpl<GenreRequestDto, Long> implements DependentController<GenreRequestDto, Long, String> {

    private static Long genreId;
    private final GenreFacade genreFacade;
    private final BookFacade bookFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("Genre", "genreType", "genre_type"),
            new HeaderName("Books count", "booksCount", "books"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public GenreController(GenreFacade genreFacade, BookFacade bookFacade) {
        this.genreFacade = genreFacade;
        this.bookFacade = bookFacade;
    }

    @Override
    @GetMapping
    public String findAll(WebRequest request, Model model) {
        PageData<GenreResponseDto> response = genreFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/genres/all");
        model.addAttribute("createNew", "/genres/new");
        model.addAttribute("cardHeader", "Genres");
        return "pages/genres/genres_all";
    }

    @Override
    @GetMapping("/books/{isbn}")
    public String findAllByEntity(WebRequest request, @PathVariable String isbn, Model model) {
        PageData<GenreResponseDto> response = genreFacade.findAllByForeignId(request, isbn);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/genres/all");
        model.addAttribute("createNew", "/genres/new");
        model.addAttribute("cardHeader", "Genres");
        return "pages/genres/genres_all";
    }

    @Override
    public String redirectToNewEntityPageWithParentId(String s, Model model) {
        return null;
    }

    @Override
    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "genres");
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(WebRequest request, Model model) {
        model.addAttribute("genre", new GenreRequestDto());
        model.addAttribute("types", GenreType.values());
        return "pages/genres/genres_new";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String redirectToEditPage(WebRequest request, @PathVariable Long id, Model model) {
        genreId = id;
        GenreResponseDto responseDto = genreFacade.findById(id);
        GenreRequestDto requestDto = new GenreRequestDto(responseDto);
        model.addAttribute("genre", requestDto);
        model.addAttribute("books", bookFacade.findAll(request));
        model.addAttribute("types", GenreType.values());
        return "pages/genres/genres_edit";
    }

    @Override
    @PostMapping("/edit")
    public String updateEntity(@ModelAttribute("genre") GenreRequestDto reqDto) {
        genreFacade.update(reqDto, genreId);
        return "redirect:/genres/details/" + genreId;
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
    public String getEntityDetails(WebRequest request, @PathVariable Long id, Model model) {
        GenreResponseDto dto = genreFacade.findById(id);
        model.addAttribute("genre", dto);
        model.addAttribute("books", bookFacade.findAllBySecondForeignId(request, id));
        return "pages/genres/genres_details";
    }
}
