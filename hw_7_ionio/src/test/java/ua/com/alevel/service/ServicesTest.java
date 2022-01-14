package ua.com.alevel.service;

import custom.CustomFramework;
import org.junit.jupiter.api.*;
import ua.com.alevel.Main;
import ua.com.alevel.data.CustomData;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.util.CustomUtil;
import ua.com.alevel.util.Populate;

import java.sql.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServicesTest {

    private final static int amountOfEntries = 10;
    private static AuthorsService<Long> authorsService;
    private static GenresService<Long> genresService;
    private static BooksService<String> booksService;
    private static BookInstancesService<Long> bookInstancesService;
    private final String AUTHOR_FIRST_NAME = CustomUtil.nameOpt(CustomData.firstNamePool());
    private final String AUTHOR_LAST_NAME = CustomUtil.nameOpt(CustomData.lastNamePool());
    private final String ISBN = CustomUtil.nameOpt(CustomData.isbnPool());
    private final String TITLE = CustomUtil.nameOpt(CustomData.bookTitlePool());
    private final String SUMMARY = "summary";
    private final String GENRE_NAME = CustomUtil.nameOpt(CustomData.genreNamePool());
    private final String BOOK_STATUS = CustomUtil.nameOpt(CustomData.bookStatusPool());
    private final String DUE_BACK = CustomUtil.dateToString(new Date((new Date(System.currentTimeMillis())).getTime() +
            (long) (Math.random() * (new Date(System.currentTimeMillis())).getTime())));
    private final String IMPRINT = CustomUtil.nameOpt(CustomData.publisherPool());
    private final Date AUTHOR_DATE_OF_BIRTH = CustomUtil.generateDate();
    private final Date AUTHOR_DATE_OF_DEATH = CustomUtil.generateDate(AUTHOR_DATE_OF_BIRTH);

    @BeforeAll
    public static void setUp() {
        CustomFramework.run(Main.class, false, AuthorsService.class, GenresService.class, BooksService.class, BookInstancesService.class);
        authorsService = (AuthorsService<Long>) CustomFramework.getReturnObjects().get(0);
        genresService = (GenresService<Long>) CustomFramework.getReturnObjects().get(1);
        booksService = (BooksService<String>) CustomFramework.getReturnObjects().get(2);
        bookInstancesService = (BookInstancesService<Long>) CustomFramework.getReturnObjects().get(3);
        Populate.PopulateBase(authorsService, genresService, booksService, bookInstancesService, amountOfEntries);
    }

    @AfterAll
    public static void clear() {
        for (Author author : authorsService.findAll()) {
            authorsService.delete(author.getId());
        }
        for (Genre genre : genresService.findAll()) {
            genresService.delete(genre.getId());
        }
    }

    @Order(1)
    @Test
    public void shouldBeAuthorCreate() {
        int size = authorsService.findAll().size();
        authorsService.create(generateAuthor());
        Assertions.assertEquals(size + 1, authorsService.findAll().size());
    }

    @Order(2)
    @Test
    public void shouldBeAuthorFindAndUpdate() {
        Author author = getRandomAuthor();
        author.setFirstName(AUTHOR_FIRST_NAME);
        author.setLastName(AUTHOR_LAST_NAME);
        author.setDateOfBirth(AUTHOR_DATE_OF_BIRTH);
        author.setDateOfDeath(AUTHOR_DATE_OF_DEATH);
        author.addBook(getRandomBook().getIsbn());
        author.removeBook(CustomUtil.getRandomString(author.getIsbn()));
        List<String> isbn = author.getIsbn();
        authorsService.update(author);
        author = authorsService.findById(author.getId());
        Assertions.assertEquals(AUTHOR_FIRST_NAME, author.getFirstName());
        Assertions.assertEquals(AUTHOR_LAST_NAME, author.getLastName());
        Assertions.assertEquals(AUTHOR_DATE_OF_BIRTH.toString(), author.getDateOfBirth().toString());
        Assertions.assertEquals(AUTHOR_DATE_OF_DEATH.toString(), author.getDateOfDeath().toString());
        Assertions.assertEquals(isbn, author.getIsbn());
    }

    @Order(3)
    @Test
    public void shouldBeGenreCreate() {
        int size = genresService.findAll().size();
        genresService.create(generateGenre());
        Assertions.assertEquals(size + 1, genresService.findAll().size());
    }

    @Order(4)
    @Test
    public void shouldBeGenreFindAndUpdate() {
        Genre genre = getRandomGenre();
        genre.setName(GENRE_NAME);
        genre.addBook(getRandomBook().getIsbn());
        genre.removeBook(CustomUtil.getRandomString(genre.getIsbnList()));
        List<String> isbns = genre.getIsbnList();
        genresService.update(genre);
        Assertions.assertEquals(GENRE_NAME, genre.getName());
        Assertions.assertEquals(isbns, genre.getIsbnList());
    }

    @Order(5)
    @Test
    public void shouldBeBookCreate() {
        int size = booksService.findAll().size();
        booksService.create(generateBook());
        Book book = ((List<Book>) booksService
                .findAll())
                .get(booksService
                        .findAll().size() - 1);
        List<String> isbns = authorsService
                .findById(book.getAuthorId())
                .getIsbn();
        String isbn = book.getIsbn();
        Assertions.assertEquals(size + 1, booksService.findAll().size());
        Assertions.assertFalse(CustomUtil.checkUniqueName(isbns, isbn));
    }

    @Order(6)
    @Test
    public void shouldBeBookFindAndUpdate() {
        Book book = getRandomBook();
        book.setAuthorId(getRandomAuthor().getId());
        book.setGenreId(getRandomGenre().getId());
        book.setIsbn(ISBN);
        book.setName(TITLE);
        book.setSummary(SUMMARY);
        book.addInstance(getRandomBookInstance().getId());
        book.removeInstance(CustomUtil.getRandomLong(book.getInstancesIds()));
        List<Long> instancesIds = book.getInstancesIds();
        booksService.update(book);
        book = booksService.findById(book.getIsbn());
        Assertions.assertEquals(ISBN, book.getIsbn());
        Assertions.assertEquals(TITLE, book.getName());
        Assertions.assertEquals(SUMMARY, book.getSummary());
        Assertions.assertEquals(instancesIds, book.getInstancesIds());
    }

    @Order(7)
    @Test
    public void shouldBeBookInstanceCreate() {
        int size = bookInstancesService.findAll().size();
        bookInstancesService.create(generateBookInstance());
        List<BookInstance> bookInstances = bookInstancesService.findAll();
        BookInstance bookInstance = bookInstances.get(bookInstances.size() - 1);
        booksService.findById(bookInstance.getBookIsbn()).addInstance(bookInstance.getId());
        List<Long> instancesIds = booksService
                .findById(bookInstance.getBookIsbn())
                .getInstancesIds();
        Long instancesId = bookInstance.getId();
        Assertions.assertEquals(size + 1, bookInstances.size());
        Assertions.assertFalse(CustomUtil.checkUniqueId(instancesIds, instancesId));
    }

    @Order(8)
    @Test
    public void shouldBeBookInstanceFindAndUpdate() {
        BookInstance bookInstance = getRandomBookInstance();
        bookInstance.setImprint(IMPRINT);
        bookInstance.setStatus(BOOK_STATUS);
        Date date = CustomUtil.setDate(DUE_BACK);
        bookInstance.setDueBack(date);
        bookInstancesService.update(bookInstance);
        bookInstance = bookInstancesService.findById(bookInstance.getId());
        Assertions.assertEquals(IMPRINT, bookInstance.getImprint());
        Assertions.assertEquals(BOOK_STATUS, bookInstance.getStatus());
        Assertions.assertEquals(date.toString(), bookInstance.getDueBack().toString());
    }

    @Order(9)
    @Test
    public void shouldBeBookInstanceDelete() {
        int lengthBeforeRemoval = bookInstancesService.findAll().size();
        BookInstance bookInstance = getRandomBookInstance();
        Assertions.assertNotNull(bookInstance.getId());
        bookInstancesService.delete(bookInstance.getId());
        Assertions.assertEquals(lengthBeforeRemoval - 1, bookInstancesService.findAll().size());
        Assertions.assertNull(bookInstancesService.findById(bookInstance.getId()));
    }

    @Order(10)
    @Test
    public void shouldBeBookDelete() {
        int lengthBeforeRemoval = booksService.findAll().size();
        Book book = getRandomBook();
        Assertions.assertNotNull(book.getIsbn());
        booksService.delete(book.getIsbn());
        Assertions.assertEquals(lengthBeforeRemoval - 1, booksService.findAll().size());
        Assertions.assertNull(booksService.findById(book.getIsbn()));
    }

    @Order(11)
    @Test
    public void shouldBeGenreDelete() {
        int lengthBeforeRemoval = genresService.findAll().size();
        Genre genre = getRandomGenre();
        Assertions.assertNotNull(genre.getId());
        genresService.delete(genre.getId());
        Assertions.assertEquals(lengthBeforeRemoval - 1, genresService.findAll().size());
        Assertions.assertNull(genresService.findById(genre.getId()));
    }

    @Order(12)
    @Test
    public void shouldBeAuthorDelete() {
        int lengthBeforeRemoval = authorsService.findAll().size();
        Author author = getRandomAuthor();
        Assertions.assertNotNull(author.getId());
        authorsService.delete(author.getId());
        Assertions.assertEquals(lengthBeforeRemoval - 1, authorsService.findAll().size());
        Assertions.assertNull(authorsService.findById(author.getId()));
    }

    private Author generateAuthor() {
        Author author = new Author();
        author.setFirstName(AUTHOR_FIRST_NAME + Math.random());
        author.setLastName(AUTHOR_LAST_NAME + Math.random());
        author.setDateOfBirth(AUTHOR_DATE_OF_BIRTH);
        author.setDateOfDeath(AUTHOR_DATE_OF_DEATH);
        return author;
    }

    private Genre generateGenre() {
        Genre genre = new Genre();
        genre.setName(GENRE_NAME);
        return genre;
    }

    private Book generateBook() {
        Book book = new Book();
        book.setAuthorId(getRandomAuthor().getId());
        book.setGenreId(getRandomGenre().getId());
        book.setIsbn(ISBN);
        book.setSummary("summary");
        authorsService.findById(book.getAuthorId()).addBook(book.getIsbn());
        return book;
    }


    private BookInstance generateBookInstance() {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setBookIsbn(getRandomBook().getIsbn());
        bookInstance.setImprint(IMPRINT);
        bookInstance.setStatus(BOOK_STATUS);
        bookInstance.setDueBack(CustomUtil.setDate(DUE_BACK));
        return bookInstance;
    }

    private Author getRandomAuthor() {
        Author author = ((List<Author>) authorsService.findAll()).get((int) ((authorsService.findAll().size() - 1) * Math.random()));
        return author;
    }

    private Genre getRandomGenre() {
        Genre genre = ((List<Genre>) genresService.findAll()).get((int) ((genresService.findAll().size() - 1) * Math.random()));
        return genre;
    }

    private Book getRandomBook() {
        Book book = ((List<Book>) booksService.findAll()).get((int) ((booksService.findAll().size() - 1) * Math.random()));
        return book;
    }

    private BookInstance getRandomBookInstance() {
        BookInstance bookInstance = ((List<BookInstance>) bookInstancesService
                .findAll()).get((int) ((bookInstancesService
                .findAll().size() - 1) * Math.random()));
        return bookInstance;
    }
}
