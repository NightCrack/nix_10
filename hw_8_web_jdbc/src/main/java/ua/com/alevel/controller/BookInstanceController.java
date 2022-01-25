package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.bookInstance.BookInstanceRequestDto;
import ua.com.alevel.dto.bookInstance.BookInstanceResponseDto;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.type.StatusType;

import java.util.List;

@Controller
@RequestMapping("/bookInstances")
public class BookInstanceController implements BaseController<BookInstanceRequestDto, Long, Object> {

    private final BookInstanceFacade bookInstanceFacade;
    private final BookFacade bookFacade;

    public BookInstanceController(BookInstanceFacade bookInstanceFacade, BookFacade bookFacade) {
        this.bookInstanceFacade = bookInstanceFacade;
        this.bookFacade = bookFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<BookInstanceResponseDto> bookInstances = bookInstanceFacade.findAll();
        model.addAttribute("bookInstances", bookInstances);
        return "pages/bookInstances/bookInstances_all";
    }

    @Override
    public String findAllByEntity(Object o, Model model) {
        return null;
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
        model.addAttribute("bookInstance", new BookInstanceRequestDto());
        model.addAttribute("books", bookFacade.findAll());
        model.addAttribute("statuses", StatusType.values());
        return "pages/bookInstances/bookInstances_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("bookInstance") BookInstanceRequestDto dto) {
        bookInstanceFacade.create(dto);
        return "redirect:/bookInstances";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable Long id) {
        bookInstanceFacade.delete(id);
        return "redirect:/bookInstances";

    }

    @Override
    @GetMapping("/details/{id}")
    public String getEntityDetails(@PathVariable Long id, Model model) {
        BookInstanceResponseDto dto = bookInstanceFacade.findById(id);
        model.addAttribute("bookInstance", dto);
        return "pages/bookInstances/bookInstances_details";
    }
}
