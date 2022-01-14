package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.dto.bookInstance.BookInstanceRequestDto;
import ua.com.alevel.dto.bookInstance.BookInstanceResponseDto;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.type.StatusType;

import java.util.List;

@Controller
@RequestMapping("/bookInstances")

public class BookInstanceController implements BaseController<BookInstanceRequestDto, Long> {

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
    public String deleteEntity(Long aLong) {
        return null;
    }

    @Override
    public String getEntityDetails(Long aLong, Model model) {
        return null;
    }
}
