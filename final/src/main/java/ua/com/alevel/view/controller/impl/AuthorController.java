package ua.com.alevel.view.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AuthorFacade;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.controller.DependentController;
import ua.com.alevel.view.dto.request.AuthorRequestDto;
import ua.com.alevel.view.dto.response.AuthorResponseDto;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/authors")
public class AuthorController extends BaseControllerImpl<AuthorRequestDto, Long> implements DependentController<AuthorRequestDto, Long, String> {

    private static Long authorsId;
    private final AuthorFacade authorFacade;
    private final BookFacade bookFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("First name", "firstName", "first_name"),
            new HeaderName("Last name", "lastName", "last_name"),
            new HeaderName("Books count", "booksCount", "books"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public AuthorController(AuthorFacade authorFacade, BookFacade bookFacade) {
        this.authorFacade = authorFacade;
        this.bookFacade = bookFacade;
    }

    @Override
    @GetMapping
    public String findAll(WebRequest request, Model model) {
        PageData<AuthorResponseDto> response = authorFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/authors/all");
        model.addAttribute("createNew", "/authors/new");
        model.addAttribute("cardHeader", "Authors");
        return "pages/authors/authors_all";
    }

    @Override
    @GetMapping("/books/{isbn}")
    public String findAllByEntity(WebRequest request, @PathVariable String isbn, Model model) {
        PageData<AuthorResponseDto> response = authorFacade.findAllByForeignId(request, isbn);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/authors/all");
        model.addAttribute("createNew", "/authors/new");
        model.addAttribute("cardHeader", "Authors");
        return "pages/authors/authors_all";
    }

    @Override
    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "authors");
    }

    @Override
    public String redirectToNewEntityPageWithParentId(String isbn, Model model) {
        return null;
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(WebRequest request, Model model) {
        model.addAttribute("author", new AuthorRequestDto());
        return "pages/authors/authors_new";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String redirectToEditPage(WebRequest request, @PathVariable Long id, Model model) {
        authorsId = id;
        AuthorResponseDto responseDto = authorFacade.findById(id);
        AuthorRequestDto requestDto = new AuthorRequestDto(responseDto);
        model.addAttribute("author", requestDto);
        model.addAttribute("books", bookFacade.findAll(request));
        return "pages/authors/authors_edit";
    }

    @Override
    @PostMapping("/edit")
    public String updateEntity(@ModelAttribute("author") AuthorRequestDto reqDto) {
        authorFacade.update(reqDto, authorsId);
        return "redirect:/authors/details/" + authorsId;
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
    public String getEntityDetails(WebRequest request, @PathVariable Long id, Model model) {
        AuthorResponseDto dto = authorFacade.findById(id);
        model.addAttribute("author", dto);
        model.addAttribute("books", bookFacade.findAllByForeignId(request, id));
        return "pages/authors/authors_details";

    }
}
