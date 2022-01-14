package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.book.BookRequestDto;
import ua.com.alevel.dto.book.BookResponseDto;
import ua.com.alevel.dto.department.DepartmentResponseDto;
import ua.com.alevel.facade.AuthorFacade;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.facade.GenreFacade;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController implements BaseController<BookRequestDto, String> {

    private final BookFacade bookFacade;
    private final GenreFacade genreFacade;
    private final AuthorFacade authorFacade;

    public BookController(BookFacade bookFacade, GenreFacade genreFacade, AuthorFacade authorFacade) {
        this.bookFacade = bookFacade;
        this.genreFacade = genreFacade;
        this.authorFacade = authorFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<BookResponseDto> books = bookFacade.findAll();
        model.addAttribute("books", books);
        return "pages/books/books_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
        model.addAttribute("book", new BookRequestDto());
        model.addAttribute("genres", genreFacade.findAll());
        model.addAttribute("authors", authorFacade.findAll());
        return "pages/books/books_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("book") BookRequestDto dto) {
        bookFacade.create(dto);
        return "redirect:/books";
    }

    @Override
    @GetMapping("/delete/{isbn}")
    public String deleteEntity(@PathVariable String isbn) {
        bookFacade.delete(isbn);
        return "redirect:/books";

    }

    @Override
    @GetMapping("/details/{isbn}")
    public String getEntityDetails(@PathVariable String isbn, Model model) {
        BookResponseDto dto = bookFacade.findById(isbn);
        model.addAttribute("book", dto);
        return "pages/books/books_details";

    }
}

