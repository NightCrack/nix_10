package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.service.BookService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final GenresDAO genresDAO;
    private final BookInstancesDAO bookInstancesDAO;

    public BookServiceImpl(BooksDAO booksDAO, AuthorsDAO authorsDAO, GenresDAO genresDAO, BookInstancesDAO bookInstancesDAO) {
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
        this.genresDAO = genresDAO;
        this.bookInstancesDAO = bookInstancesDAO;
    }

    @Override
    public void create(CustomResultSet<Book> customResultSet) {
        List<Long> authorsId = (List<Long>) customResultSet.getParams().get(0);
        List<Long> genresId = (List<Long>) customResultSet.getParams().get(1);
        if (authorsId.stream().allMatch(authorsDAO::existsById) && genresId.stream().allMatch(genresDAO::existsById)) {
            booksDAO.create(customResultSet);
        }
    }

    @Override
    public void update(Book book) {
//        if (booksDAO.existsById(book.getIsbn())) {
//            booksDAO.update(book);
//        }
    }

    @Override
    public void delete(String isbn) {
//        Book defaultBook;
//        Optional<Book> optionalBook = booksDAO.findAll()
//                .stream()
//                .filter(entry -> entry
//                        .getName()
//                        .equals("Undefined"))
//                .findFirst();
//        if (optionalBook.isEmpty()) {
//            Book book = new Book();
//            book.setIsbn("Undefined");
//            book.setTitle("Undefined");
//            Genre defaultGenre;
//            Optional<Genre> optionalGenre = genresDAO.findAll()
//                    .stream()
//                    .filter(entry -> entry
//                            .getGenreType()
//                            .equals(GenreType.Undefined))
//                    .findFirst();
//            if (optionalGenre.isEmpty()) {
//                Genre genre = new Genre();
//                genre.setGenreType(GenreType.Undefined);
//                genresDAO.create(genre);
//                defaultGenre = genre;
//            } else {
//                defaultGenre = optionalGenre.get();
//            }
//            book.setGenre(Collections.singletonList(defaultGenre));
//            Optional<Author> optionalAuthor = authorsDAO.findAll()
//                    .stream()
//                    .filter(entry -> (entry.getFirstName() + " " + entry.getLastName())
//                            .equals("Un Defined"))
//                    .findFirst();
//            if (optionalAuthor.isEmpty()) {
//                Author author = new Author();
//                author.setFirstName("Un");
//                author.setLastName("Defined");
//                author.setBirthDate(new Date(0));
//                author.setDeathDate(new Date(0));
//                authorsDAO.create(author);
//                book.setAuthor(author);
//            } else {
//                book.setAuthor(optionalAuthor.get());
//            }
//            booksDAO.create(book);
//            defaultBook = book;
//        } else {
//            defaultBook = optionalBook.get();
//        }
//        if (booksDAO.existsById(isbn)) {
//            Book book = booksDAO.findById(isbn);
//            List<BookInstance> bookInstances = book.getBookInstances();
//            bookInstances = bookInstances.stream()
//                    .peek(bookInstance -> bookInstance
//                            .setBook(defaultBook)).toList();
//            List<Genre> genres = book.getGenre();
//            genres = genres.stream().peek(entry -> entry.getBooks().remove(book)).toList();
//            Author author = book.getAuthor();
//            author.getBooks().remove(book);
//            bookInstances.forEach(bookInstancesDAO::update);
//            genres.forEach(genresDAO::update);
//            authorsDAO.update(author);
//            booksDAO.delete(isbn);
//        }
    }

    @Override
    public Book findById(String isbn) {
        return booksDAO.findById(isbn);
    }

    @Override
    public DataTableResponse<Book> findAll(DataTableRequest request) {
        DataTableResponse<Book> dataTableResponse = booksDAO.findAll(request);
        int count = booksDAO.count();
        WebResponseUtil.initDataTableResponse(request,dataTableResponse,count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(Long authorId) {
//        if (authorsDAO.existsById(authorId)) {
//            booksDAO.deleteAllByForeignId(authorId);
//        }
    }

    @Override
    public List<Book> findAllByForeignId(Long authorId) {
        if (authorsDAO.existsById(authorId)) {
            return booksDAO.findAllByForeignId(authorId);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteAllBySecondForeignId(Long genreId) {
//        if (genresDAO.existsById(genreId)) {
//            booksDAO.deleteAllBySecondForeignId(genreId);
//        }
    }

    @Override
    public List<Book> findAllBySecondForeignId(Long genreId) {
        if (genresDAO.existsById(genreId)) {
            return booksDAO.findAllBySecondForeignId(genreId);
        }
        return Collections.emptyList();
    }
}
