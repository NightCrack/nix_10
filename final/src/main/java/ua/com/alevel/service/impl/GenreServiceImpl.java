package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Genre;
import ua.com.alevel.service.GenreService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenresDAO genresDAO;
    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final BookInstancesDAO bookInstancesDAO;

    public GenreServiceImpl(GenresDAO genresDAO, BooksDAO booksDAO, AuthorsDAO authorsDAO, BookInstancesDAO bookInstancesDAO) {
        this.genresDAO = genresDAO;
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
        this.bookInstancesDAO = bookInstancesDAO;
    }

    @Override
    public void create(CustomResultSet<Genre> genre) {
        genresDAO.create(genre);
    }

    @Override
    public void update(Genre genre) {
//        if (genresDAO.existsById(genre.getId())) {
//            genresDAO.update(genre);
//        }
    }

    @Override
    public void delete(Long id) {
//        Genre defaultGenre;
//        Optional<Genre> optionalGenre = genresDAO.findAll()
//                .stream()
//                .filter(entry -> entry
//                        .getGenreType()
//                        .equals(GenreType.Undefined))
//                .findFirst();
//        if (optionalGenre.isEmpty()) {
//            Genre genre = new Genre();
//            genre.setGenreType(GenreType.Undefined);
//            genresDAO.create(genre);
//            defaultGenre = genre;
//        } else {
//            defaultGenre = optionalGenre.get();
//        }
//        if (genresDAO.existsById(id)) {
//            Genre genre = genresDAO.findById(id);
//            List<Book> books = booksDAO.findAllBySecondForeignId(id);
//            books = books.stream().peek(book -> {
//                book.getGenre().remove(genre);
//                if (book.getGenre().size() == 0) {
//                    book.setGenre(Collections.singletonList(defaultGenre));
//                }
//            }).toList();
//            books.forEach(booksDAO::update);
//            genresDAO.delete(id);
//        }
    }

    @Override
    public Genre findById(Long id) {
        return genresDAO.findById(id);
    }

    @Override
    public DataTableResponse<Genre> findAll(DataTableRequest request) {
        DataTableResponse<Genre> dataTableResponse = genresDAO.findAll(request);
        int count = genresDAO.count();
        WebResponseUtil.initDataTableResponse(request,dataTableResponse,count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(String s) {

    }

    @Override
    public List<Genre> findAllByForeignId(String isbn) {
        return genresDAO.findAllByForeignId(isbn);
    }
}
