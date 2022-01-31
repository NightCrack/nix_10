package ua.com.alevel.view.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AuthorFacade;
import ua.com.alevel.facade.BookFacade;
import ua.com.alevel.facade.BookInstanceFacade;
import ua.com.alevel.facade.GenreFacade;
import ua.com.alevel.view.controller.SecondDependentController;
import ua.com.alevel.view.dto.request.BookRequestDto;
import ua.com.alevel.view.dto.response.BookResponseDto;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/books")
public class BookController extends BaseControllerImpl<BookRequestDto, String> implements SecondDependentController<BookRequestDto, String, Long, Long> {

    private static String booksIsbn;
    private final BookFacade bookFacade;
    private final AuthorFacade authorFacade;
    private final GenreFacade genreFacade;
    private final BookInstanceFacade bookInstanceFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("ISBN", "isbn", "isbn"),
            new HeaderName("Image", null, null),
            new HeaderName("Title", "title", "title"),
            new HeaderName("Publication date", "publicationDate", "publication_date"),
            new HeaderName("Authors", "authorsCount", "authors"),
            new HeaderName("Genres", "genresCount", "genres"),
            new HeaderName("Instances", "bookInstancesCount", "instances"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public BookController(BookFacade bookFacade, AuthorFacade authorFacade, GenreFacade genreFacade, BookInstanceFacade bookInstanceFacade) {
        this.bookFacade = bookFacade;
        this.authorFacade = authorFacade;
        this.genreFacade = genreFacade;
        this.bookInstanceFacade = bookInstanceFacade;
    }

    @Override
    @GetMapping
    public String findAll(WebRequest request, Model model) {
        PageData<BookResponseDto> response = bookFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/books/all");
        model.addAttribute("createNew", "/books/new");
        model.addAttribute("cardHeader", "Books");
        return "pages/books/books_all";
    }

    @Override
    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "books");
    }

    @Override
    @GetMapping("/authors/{authorId}")
    public String findAllByEntity(WebRequest request, @PathVariable Long authorId, Model model) {
        PageData<BookResponseDto> response = bookFacade.findAllByForeignId(request, authorId);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/books/all");
        model.addAttribute("createNew", "/books/new");
        model.addAttribute("cardHeader", "Books");
        return "/pages/books/books_all";
    }

    @Override
    @GetMapping("/genres/{genreId}")
    public String findAllBySecondEntity(WebRequest request, @PathVariable Long genreId, Model model) {
        PageData<BookResponseDto> response = bookFacade.findAllBySecondForeignId(request, genreId);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/books/all");
        model.addAttribute("createNew", "/books/new");
        model.addAttribute("cardHeader", "Books");
        return "/pages/books/books_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(WebRequest request, Model model) {
        model.addAttribute("book", new BookRequestDto());
        model.addAttribute("genres", genreFacade.findAll(request));
        model.addAttribute("authors", authorFacade.findAll(request));
        return "pages/books/books_new";
    }

    @Override
    @GetMapping("/edit/{isbn}")
    public String redirectToEditPage(WebRequest request, @PathVariable String isbn, Model model) {
        booksIsbn = isbn;
        BookResponseDto responseDto = bookFacade.findById(isbn);
        BookRequestDto requestDto = new BookRequestDto(responseDto);
        model.addAttribute("book", requestDto);
        model.addAttribute("authors", authorFacade.findAll(request));
        model.addAttribute("currentAuthors", authorFacade.findAllByForeignId(request,isbn));
        model.addAttribute("genres", genreFacade.findAll(request));
        model.addAttribute("currentGenres", genreFacade.findAllByForeignId(request,isbn));
//        model.addAttribute("file", new Object());
        return "/pages/books/books_edit";
    }

    @Override
    @PostMapping("/edit")
    public String updateEntity(@ModelAttribute("book") BookRequestDto reqDto) {
        bookFacade.update(reqDto, booksIsbn);
        return "redirect:/books/details/" + booksIsbn;
    }

    @Override
    @GetMapping("/new/{bookId}")
    public String redirectToNewEntityPageWithParentId(@PathVariable Long authorId, Model model) {
//        model.addAttribute("book", new BookRequestDto(authorId));
        return "pages/bookInstances/bookInstances_new";
    }

    @Override
    @GetMapping("/new/{genreId}")
    public String redirectToNewEntityPageWithSecondParentId(@PathVariable Long genreId, Model model) {
//        model.addAttribute("book", new BookRequestDto(Collections.singletonList(genreId)));
        return "pages/bookInstances/bookInstances_new";
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
    public String getEntityDetails(WebRequest request, @PathVariable String isbn, Model model) {
        BookResponseDto dto = bookFacade.findById(isbn);
        model.addAttribute("book", dto);
        model.addAttribute("authors", authorFacade.findAllByForeignId(request, isbn));
        model.addAttribute("genres", genreFacade.findAllByForeignId(request, isbn));
        model.addAttribute("instances", bookInstanceFacade.findAllByForeignId(request, isbn).getItemsSize());
        return "pages/books/books_details";

    }
}

