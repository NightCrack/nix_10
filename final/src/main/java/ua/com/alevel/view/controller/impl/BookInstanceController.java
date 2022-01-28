package ua.com.alevel.view.controller.impl;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.type.StatusType;
import ua.com.alevel.view.controller.DependentController;
import ua.com.alevel.view.dto.request.BookInstanceRequestDto;
import ua.com.alevel.view.dto.response.BookInstanceResponseDto;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/bookInstances")
public class BookInstanceController extends BaseControllerImpl<BookInstanceRequestDto, Long> implements DependentController<BookInstanceRequestDto, Long, String> {

    private static Long instancesId;
    private final BookInstanceFacade bookInstanceFacade;
    private final BookFacade bookFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("Book title", null, null),
            new HeaderName("Status", "status", "status"),
            new HeaderName("Due back", "dueBack", "due_back"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public BookInstanceController(BookInstanceFacade bookInstanceFacade, BookFacade bookFacade) {
        this.bookInstanceFacade = bookInstanceFacade;
        this.bookFacade = bookFacade;
    }

    @Override
    @GetMapping
    public String findAll(WebRequest request, Model model) {
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
    @GetMapping("/books/{bookIsbn}")
    public String findAllByEntity(WebRequest request, @PathVariable String bookIsbn, Model model) {
        PageData<BookInstanceResponseDto> response = bookInstanceFacade.findAllByForeignId(request, bookIsbn);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/bookInstances/all");
        model.addAttribute("createNew", "/bookInstances/new");
        model.addAttribute("cardHeader", "Books' instances");
        return "/pages/bookInstances/bookInstances_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(WebRequest request, Model model) {
        model.addAttribute("bookInstance", new BookInstanceRequestDto());
        model.addAttribute("books", bookFacade.findAll(request));
        model.addAttribute("statuses", StatusType.values());
        model.addAttribute("countryCodes", CountryCode.values());
        return "pages/bookInstances/bookInstances_new";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String redirectToEditPage(WebRequest request, @PathVariable Long id, Model model) {
        instancesId = id;
        BookInstanceResponseDto responseDto = bookInstanceFacade.findById(id);
        BookInstanceRequestDto requestDto = new BookInstanceRequestDto(responseDto);
        model.addAttribute("bookInstance", requestDto);
        model.addAttribute("books", bookFacade.findAll(request));
        model.addAttribute("statuses", StatusType.values());
        model.addAttribute("countryCodes", CountryCode.values());
        return "pages/bookInstances/bookInstances_edit";
    }

    @Override
    @PostMapping("/edit")
    public String updateEntity(@ModelAttribute("bookInstance") BookInstanceRequestDto reqDto) {
        bookInstanceFacade.update(reqDto, instancesId);
        return "redirect:/bookInstances/details/" + instancesId;
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
    public String getEntityDetails(WebRequest request, @PathVariable Long id, Model model) {
        BookInstanceResponseDto dto = bookInstanceFacade.findById(id);
        model.addAttribute("bookInstance", dto);
        return "pages/bookInstances/bookInstances_details";
    }
}
