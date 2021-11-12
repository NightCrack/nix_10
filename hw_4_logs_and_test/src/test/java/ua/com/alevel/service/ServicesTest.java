package ua.com.alevel.service;

import org.junit.jupiter.api.*;
import ua.com.alevel.data.CustomData;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.service.impl.AuthorsServiceImpl;
import ua.com.alevel.service.impl.BookInstancesServiceImpl;
import ua.com.alevel.service.impl.BooksServiceImpl;
import ua.com.alevel.service.impl.GenresServiceImpl;
import ua.com.alevel.util.CustomUtil;
import ua.com.alevel.util.Populate;

import java.util.Arrays;
import java.util.Date;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServicesTest {

    private final static int amountOfEntries = 10;
    private final static int amountOfEntriesTest = 1;
    private static AuthorsServiceImpl authorsService = new AuthorsServiceImpl();
    private static GenresServiceImpl genresService = new GenresServiceImpl();
    private static BooksServiceImpl booksService = new BooksServiceImpl();
    private static BookInstancesServiceImpl bookInstancesService = new BookInstancesServiceImpl();
    private static int maxEntries = 0;
    private final String AUTHOR_FIRST_NAME = CustomUtil.nameOpt(CustomData.firstNamePool());
    private final String AUTHOR_LAST_NAME = CustomUtil.nameOpt(CustomData.lastNamePool());
    private final String ISBN = CustomUtil.nameOpt(CustomData.isbnPool());
    private final String TITLE = CustomUtil.nameOpt(CustomData.bookTitlePool());
    private final String SUMMARY = "summary";
    private final String GENRE_NAME = CustomUtil.nameOpt(CustomData.genreNamePool());
    private final String BOOK_STATUS = CustomUtil.nameOpt(CustomData.bookStatusPool());
    private final String DUE_BACK = CustomUtil.dateToString(new Date((new Date()).getTime() + (long) (Math.random() * (new Date()).getTime())));
    private final String IMPRINT = CustomUtil.nameOpt(CustomData.publisherPool());
    private final Date AUTHOR_DATE_OF_BIRTH = CustomUtil.generateDate();
    private final Date AUTHOR_DATE_OF_DEATH = CustomUtil.generateDate(AUTHOR_DATE_OF_BIRTH);

    @BeforeAll
    public static void setUp() {
        try {
            while (true) {
                Populate.PopulateBase(authorsService, genresService, booksService, bookInstancesService, amountOfEntriesTest);
                ++maxEntries;
            }
        } catch (Error error) {
            System.out.println("Max amount of entries in sheets: " + maxEntries * 4);
        }
        cleanUp();
        Populate.PopulateBase(authorsService, genresService, booksService, bookInstancesService, amountOfEntries);

    }

    private static void cleanUp() {
        for (int index = bookInstancesService.findAll().length; index > 0; index--) {
            bookInstancesService.delete(index);
        }
        for (int index = booksService.findAll().length; index > 0; index--) {
            if (booksService.findAll()[index - 1] != null) {
                booksService.delete(booksService.findAll()[index - 1].getIsbn());
            }
        }
        for (int index = authorsService.findAll().length; index > 0; index--) {
            authorsService.delete(index);
        }
        for (int index = genresService.findAll().length; index > 0; index--) {
            genresService.delete(index - 1);
        }
    }

    @Order(1)
    @Test
    public void shouldBeAuthorCreate() {
        authorsService.create(generateAuthor());
        Author[] authors = authorsService.findAll();
        for (int index = 0; index < authors.length; index++) {
            if (authors[index] == null) {
                authors = Arrays.copyOf(authors, index);
                break;
            }
        }
        Assertions.assertEquals(amountOfEntries + 1, authors.length);
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
        author.removeBook(CustomUtil.getNotNullString(author.getIsbns()));
        String[] isbn = author.getIsbns();
        authorsService.update(author);
        author = authorsService.findById(author.getId());
        Assertions.assertEquals(AUTHOR_FIRST_NAME, author.getFirstName());
        Assertions.assertEquals(AUTHOR_LAST_NAME, author.getLastName());
        Assertions.assertEquals(AUTHOR_DATE_OF_BIRTH, author.getDateOfBirth());
        Assertions.assertEquals(AUTHOR_DATE_OF_DEATH, author.getDateOfDeath());
        Assertions.assertEquals(Arrays.toString(isbn), Arrays.toString(author.getIsbns()));
    }

    @Order(3)
    @Test
    public void shouldBeAuthorDelete() {
        int lengthBeforeRemoval = authorsService.findAll().length;
        Author author = getRandomAuthor();
        Assertions.assertNotNull(author.getId());
        authorsService.delete(author.getId());
        Assertions.assertEquals(lengthBeforeRemoval - 1, authorsService.findAll().length);
        Assertions.assertNull(authorsService.findById(author.getId()));
    }

    @Order(4)
    @Test
    public void shouldBeGenreCreate() {
        genresService.create(generateGenre());
        Genre[] genres = genresService.findAll();
        for (int index = 0; index < genres.length; index++) {
            if (genres[index] == null) {
                genres = Arrays.copyOf(genres, index);
                break;
            }
        }
        Assertions.assertEquals(amountOfEntries + 1, genres.length);
    }

    @Order(5)
    @Test
    public void shouldBeGenreFindAndUpdate() {
        Genre genre = getRandomGenre();
        genre.setName(GENRE_NAME);
        genre.addBook(getRandomBook().getIsbn());
        genre.removeBook(CustomUtil.getNotNullString(genre.getIsbns()));
        String[] isbns = genre.getIsbns();
        genresService.update(genre);
        Assertions.assertEquals(GENRE_NAME, genre.getName());
        Assertions.assertEquals(Arrays.toString(isbns), Arrays.toString(genre.getIsbns()));
    }

    @Order(6)
    @Test
    public void shouldBeGenreDelete() {
        int lengthBeforeRemoval = genresService.findAll().length;
        Genre genre = getRandomGenre();
        Assertions.assertNotNull(genre.getId());
        genresService.delete(genre.getId());
        Assertions.assertEquals(lengthBeforeRemoval - 1, genresService.findAll().length);
        Assertions.assertNull(genresService.findById(genre.getId()));
    }

    @Order(7)
    @Test
    public void shouldBeBookCreate() {
        booksService.create(generateBook());
        Book[] books = booksService.findAll();
        for (int index = 0; index < books.length; index++) {
            if (books[index] == null) {
                books = Arrays.copyOf(books, index);
                break;
            }
        }
        Book book = books[books.length - 1];
        String[] isbns = authorsService
                .findById(book.getAuthorId())
                .getIsbns();
        String isbn = book.getIsbn();
        Assertions.assertEquals(amountOfEntries + 1, books.length);
        Assertions.assertFalse(CustomUtil.checkUniqueName(isbns, isbn));
    }

    @Order(8)
    @Test
    public void shouldBeBookFindAndUpdate() {
        Book book = getRandomBook();
        book.setAuthorId(getRandomAuthor().getId());
        book.setGenreId(getRandomGenre().getId());
        book.setIsbn(ISBN);
        book.setTitle(TITLE);
        book.setSummary(SUMMARY);
        book.addInstance(getRandomBookInstance().getId());
        book.removeInstance(CustomUtil.getNotNullInteger(book.getInstancesIds()));
        Integer[] instancesIds = book.getInstancesIds();
        booksService.update(book);
        book = booksService.findByIsbn(book.getIsbn());
        Assertions.assertEquals(ISBN, book.getIsbn());
        Assertions.assertEquals(TITLE, book.getTitle());
        Assertions.assertEquals(SUMMARY, book.getSummary());
        Assertions.assertEquals(Arrays.toString(instancesIds), Arrays.toString(book.getInstancesIds()));
    }

    @Order(9)
    @Test
    public void shouldBeBookDelete() {
        int lengthBeforeRemoval = booksService.findAll().length;
        Book book = getRandomBook();
        Assertions.assertNotNull(book.getIsbn());
        booksService.delete(book.getIsbn());
        Assertions.assertEquals(lengthBeforeRemoval - 1, booksService.findAll().length);
        Assertions.assertNull(booksService.findById(book.getIsbn()));
    }

    @Order(10)
    @Test
    public void shouldBeBookInstanceCreate() {
        bookInstancesService.create(generateBookInstance());
        BookInstance[] bookInstances = bookInstancesService.findAll();
        for (int index = 0; index < bookInstances.length; index++) {
            if (bookInstances[index] == null) {
                bookInstances = Arrays.copyOf(bookInstances, index);
                break;
            }
        }
        BookInstance bookInstance = bookInstances[bookInstances.length - 1];
        booksService.findByIsbn(bookInstance.getIsbn()).addInstance(bookInstance.getId());
        Integer[] instancesIds = booksService
                .findById(bookInstance.getIsbn())
                .getInstancesIds();
        Integer instancesId = bookInstance.getId();
        Assertions.assertEquals(amountOfEntries + 1, bookInstances.length);
        Assertions.assertFalse(CustomUtil.checkUniqueId(instancesIds, instancesId));
    }

    @Order(11)
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
        Assertions.assertEquals(date, bookInstance.getDueBack());
    }

    @Order(12)
    @Test
    public void shouldBeBookInstanceDelete() {
        int lengthBeforeRemoval = bookInstancesService.findAll().length;
        BookInstance bookInstance = getRandomBookInstance();
        Assertions.assertNotNull(bookInstance.getId());
        bookInstancesService.delete(bookInstance.getId());
        Assertions.assertEquals(lengthBeforeRemoval - 1, bookInstancesService.findAll().length);
        Assertions.assertNull(bookInstancesService.findById(bookInstance.getId()));
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
        Author author = authorsService.findAll()[(int) ((authorsService.findAll().length - 1) * Math.random())];
        if (author == null) {
            return getRandomAuthor();
        }
        return author;
    }

    private Genre getRandomGenre() {
        Genre genre = genresService.findAll()[(int) ((genresService.findAll().length - 1) * Math.random())];
        if (genre == null) {
            return getRandomGenre();
        }
        return genre;
    }

    private Book getRandomBook() {
        Book book = booksService.findAll()[(int) ((booksService.findAll().length - 1) * Math.random())];
        if (book == null) {
            return getRandomBook();
        }
        return book;
    }

    private BookInstance getRandomBookInstance() {
        BookInstance bookInstance = bookInstancesService.findAll()[(int) ((bookInstancesService.findAll().length - 1) * Math.random())];
        if (bookInstance == null) {
            return getRandomBookInstance();
        }
        return bookInstance;
    }
}
