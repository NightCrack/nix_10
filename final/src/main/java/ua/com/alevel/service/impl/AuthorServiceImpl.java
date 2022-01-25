package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Author;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorsDAO authorsDAO;
    private final GenresDAO genresDAO;
    private final BooksDAO booksDAO;

    public AuthorServiceImpl(AuthorsDAO authorsDAO, GenresDAO genresDAO, BooksDAO booksDAO) {
        this.authorsDAO = authorsDAO;
        this.genresDAO = genresDAO;
        this.booksDAO = booksDAO;
    }

    @Override
    public void create(CustomResultSet<Author> author) {
        authorsDAO.create(author);
    }

    @Override
    public void update(Author author) {
        if (authorsDAO.existsById(author.getId())) {
            authorsDAO.update(author);
        }
    }

    @Override
    public void delete(Long id) {
//        Author defaultAuthor;
//        Optional<Author> optionalAuthor = authorsDAO.findAll()
//                .stream()
//                .filter(entry -> (entry.getFirstName() + " " + entry.getLastName())
//                        .equals("Un Defined"))
//                .findFirst();
//        if (optionalAuthor.isEmpty()) {
//            Author author = new Author();
//            author.setFirstName("Un");
//            author.setLastName("Defined");
//            author.setBirthDate(new Date(0));
//            author.setDeathDate(new Date(0));
//            authorsDAO.create(author);
//            defaultAuthor = author;
//        } else {
//            defaultAuthor = optionalAuthor.get();
//        }
//        if (authorsDAO.existsById(id)) {
//            Author author = authorsDAO.findById(id);
//            List<Book> books = author.getBooks();
//            books = books.stream().peek(entry -> entry.setAuthor(defaultAuthor)).toList();
//            books.forEach(booksDAO::update);
//            authorsDAO.delete(id);
//        }
    }

    @Override
    public Author findById(Long id) {
        return authorsDAO.findById(id);
    }

    @Override
    public DataTableResponse<Author> findAll(DataTableRequest request) {
        DataTableResponse<Author> dataTableResponse = authorsDAO.findAll(request);
        int count = authorsDAO.count();
        WebResponseUtil.initDataTableResponse(request,dataTableResponse,count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(String s) {

    }

    @Override
    public List<Author> findAllByForeignId(String isbn) {
        return authorsDAO.findAllByForeignId(isbn);
    }
}
