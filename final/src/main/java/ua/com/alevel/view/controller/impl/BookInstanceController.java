package ua.com.alevel.view.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.view.controller.DependentController;
import ua.com.alevel.view.dto.request.BookInstanceRequestDto;
import ua.com.alevel.view.dto.response.BookInstanceResponseDto;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/bookInstances")
public class BookInstanceController extends BaseControllerImpl<BookInstanceRequestDto, Long> implements DependentController<BookInstanceRequestDto, Long, String> {

    private final BookInstanceFacade bookInstanceFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("Book title", null, null),
            new HeaderName("Status", "status", "status"),
            new HeaderName("Due back", "dueBack", "due_back"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public BookInstanceController(BookInstanceFacade bookInstanceFacade) {
        this.bookInstanceFacade = bookInstanceFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<BookInstanceResponseDto> response = bookInstanceFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/bookInstances/all");
        model.addAttribute("createNew", "/bookInstances/new");
        model.addAttribute("cardHeader", "Books' instances");
        return "pages/bookInstances/bookInstances_all";
    }

    @Override
    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "bookInstances");
    }

    @Override
    @GetMapping("/books/{bookId}")
    public String findAllByEntity(@PathVariable String bookId, Model model) {
        model.addAttribute("bookInstances", bookInstanceFacade.findAllByForeignId(bookId));
        return "/pages/bookInstances/bookInstances_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model, WebRequest request) {
//        model.addAttribute("bookInstance", new BookInstanceRequestDto());
//        model.addAttribute("books", bookFacade.findAll());
//        model.addAttribute("statuses", StatusType.values());
        return "pages/bookInstances/bookInstances_new";
    }

    @Override
    @GetMapping("/new/{bookId}")
    public String redirectToNewEntityPageWithParentId(@PathVariable String bookId, Model model) {
//        model.addAttribute("bookInstance", new BookInstanceRequestDto(bookId));
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
