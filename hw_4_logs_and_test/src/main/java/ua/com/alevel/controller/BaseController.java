package ua.com.alevel.controller;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BaseController {

    private static final String CREATE_VALUE = "new";
    private static final String UPDATE_VALUE = "upd";
    private static final String DELETE_VALUE = "del";
    private static final String FIND_ONE_VALUE = "one";
    private static final String FIND_ALL_VALUE = "all";
    private static final String EXIT_VALUE = "ext";
    private static final String POPULATE_VALUE = "pop";
    private static final String MENU_VALUE = "tip";
    private static final String[] DATABASE_OBJECTS = {"Author", "Genre", "Book", "BookInstance"};
    private static final String OBJECT_ONE_CREATE = "Author" + " " + CREATE_VALUE;
    private static final String OBJECT_ONE_UPDATE = "Author" + " " + UPDATE_VALUE;
    private static final String OBJECT_ONE_DELETE = "Author" + " " + DELETE_VALUE;
    private static final String OBJECT_ONE_FIND_ONE = "Author" + " " + FIND_ONE_VALUE;
    private static final String OBJECT_ONE_FIND_ALL = "Author" + " " + FIND_ALL_VALUE;
    private static final String OBJECT_TWO_CREATE = "Genre" + " " + CREATE_VALUE;
    private static final String OBJECT_TWO_UPDATE = "Genre" + " " + UPDATE_VALUE;
    private static final String OBJECT_TWO_DELETE = "Genre" + " " + DELETE_VALUE;
    private static final String OBJECT_TWO_FIND_ONE = "Genre" + " " + FIND_ONE_VALUE;
    private static final String OBJECT_TWO_FIND_ALL = "Genre" + " " + FIND_ALL_VALUE;
    private static final String OBJECT_THREE_CREATE = "Book" + " " + CREATE_VALUE;
    private static final String OBJECT_THREE_UPDATE = "Book" + " " + UPDATE_VALUE;
    private static final String OBJECT_THREE_DELETE = "Book" + " " + DELETE_VALUE;
    private static final String OBJECT_THREE_FIND_ONE = "Book" + " " + FIND_ONE_VALUE;
    private static final String OBJECT_THREE_FIND_ALL = "Book" + " " + FIND_ALL_VALUE;
    private static final String OBJECT_FOUR_CREATE = "BookInstance" + " " + CREATE_VALUE;
    private static final String OBJECT_FOUR_UPDATE = "BookInstance" + " " + UPDATE_VALUE;
    private static final String OBJECT_FOUR_DELETE = "BookInstance" + " " + DELETE_VALUE;
    private static final String OBJECT_FOUR_FIND_ONE = "BookInstance" + " " + FIND_ONE_VALUE;
    private static final String OBJECT_FOUR_FIND_ALL = "BookInstance" + " " + FIND_ALL_VALUE;
    private final AuthorsServiceImpl authorsService = new AuthorsServiceImpl();
    private final BookInstancesServiceImpl bookInstancesService = new BookInstancesServiceImpl();
    private final BooksServiceImpl booksService = new BooksServiceImpl();
    private final GenresServiceImpl genresService = new GenresServiceImpl();
    private String input;
    private String breaker;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String dateOfDeath;
    private String title;
    private Integer authorId;
    private String summary;
    private String isbn;
    private Integer genreId;
    private String imprint;
    private String status;
    private String dueBack;
    private Integer instancesId;
    private String genreName;

    public void run() {
        messages("presentation");
        messages("menu");
        do {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                messages("input");
                String input = reader.readLine();
                crud(input, reader);
            } catch (IOException exception) {

                System.out.println("problem: " + exception.getMessage());
            }

        } while (breaker != EXIT_VALUE);
    }


    private void messages(String opt) {
        switch (opt) {
            case "presentation":
                System.out.println();
                System.out.println("This DB is made alike to \"Express Tutorial: The Local Library website\"");
                System.out.println("https://developer.mozilla.org/en-US/docs/Learn/Server-side/Express_Nodejs/Tutorial_local_library_website");
                break;
            case "menu":
                System.out.println();
                System.out.println("Make your choice:");
                System.out.println("Database objects names: " + Arrays.toString(DATABASE_OBJECTS));
                System.out.println("Create an object - enter 'objectName' + whitespace + '" + CREATE_VALUE + "';");
                System.out.println("Update an object - enter 'objectName' + whitespace + '" + UPDATE_VALUE + "';");
                System.out.println("Delete an object - enter 'objectName' + whitespace + '" + DELETE_VALUE + "';");
                System.out.println("Find an object by ID - enter 'objectName' + whitespace + '" + FIND_ONE_VALUE + "';");
                System.out.println("List all object - enter 'objectName' + whitespace + '" + FIND_ALL_VALUE + "';");
                System.out.println("Populate base with genres, authors, books and bookInstances - enter '" + POPULATE_VALUE + "';");
                System.out.println("Call this menu - enter '" + MENU_VALUE + "';");
                System.out.println("Exit the program - enter '" + EXIT_VALUE + "';");
                break;
            case "input":
                System.out.println();
                System.out.print("Your input (tip?): ");
                break;
            case "badInput":
                System.out.println();
                System.out.println("Incorrect input.");
                break;
            case "allGood":
                System.out.println();
                System.out.println("Operation complete");
                break;
            case "allBad":
                System.out.println();
                System.out.println("Operation failed");
                break;
            case "dateBad":
                System.out.println();
                System.out.println("Incorrect date format");
                break;
            case "empty":
                System.out.println();
                System.out.println("The list is empty");
                break;
        }
    }


    private void crud(String input, BufferedReader reader) {
        switch (input) {
            case OBJECT_ONE_CREATE -> createAuthor(reader);
            case OBJECT_ONE_UPDATE -> updateAuthor(reader);
            case OBJECT_ONE_DELETE -> deleteAuthor(reader);
            case OBJECT_ONE_FIND_ONE -> findByIdAuthor(reader);
            case OBJECT_ONE_FIND_ALL -> findAllAuthor(reader);
            case OBJECT_TWO_CREATE -> createGenre(reader);
            case OBJECT_TWO_UPDATE -> updateGenre(reader);
            case OBJECT_TWO_DELETE -> deleteGenre(reader);
            case OBJECT_TWO_FIND_ONE -> findByIdGenre(reader);
            case OBJECT_TWO_FIND_ALL -> findAllGenre(reader);
            case OBJECT_THREE_CREATE -> createBook(reader);
            case OBJECT_THREE_UPDATE -> updateBook(reader);
            case OBJECT_THREE_DELETE -> deleteBook(reader);
            case OBJECT_THREE_FIND_ONE -> findByIsbnBook(reader);
            case OBJECT_THREE_FIND_ALL -> findAllBook(reader);
            case OBJECT_FOUR_CREATE -> createBookInstance(reader);
            case OBJECT_FOUR_UPDATE -> updateBookInstance(reader);
            case OBJECT_FOUR_DELETE -> deleteBookInstance(reader);
            case OBJECT_FOUR_FIND_ONE -> findByIdBookInstance(reader);
            case OBJECT_FOUR_FIND_ALL -> findAllBookInstance(reader);
            case POPULATE_VALUE -> Populate.PopulateBase(authorsService, genresService, booksService, bookInstancesService, 5);
            case MENU_VALUE -> messages("menu");
            case EXIT_VALUE -> breaker = EXIT_VALUE;
            default -> messages("badInput");
        }
    }

    private void findAllBookInstance(BufferedReader reader) {
        System.out.println();
        if ((bookInstancesService.findAll() != null) && (bookInstancesService.findAll().length != 0)) {
            for (int index = 0; index < bookInstancesService.findAll().length; index++) {
                if (bookInstancesService.findAll()[index] != null) {
                    System.out.println(bookInstancesService.findAll()[index].toStringBrief());
                }
            }
        } else {
            System.out.println("The bookInstances base is empty.");
        }
    }

    private void findByIdBookInstance(BufferedReader reader) {
        try {
            if (bookInstancesService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter bookInstance's ID: ");
                input = reader.readLine();
                if (!CustomUtil.isNumber(input)) {
                    messages("badInput");
                } else {
                    instancesId = Integer.valueOf(input);
                    BookInstance bookInstance = bookInstancesService.findById(instancesId);
                    System.out.print("Your bookInstance: ");
                    System.out.println(bookInstance.toString());
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void deleteBookInstance(BufferedReader reader) {
        try {
            System.out.println();
            System.out.print("Enter ID of bookInstance to delete: ");
            instancesId = Integer.valueOf(reader.readLine());
            boolean checkFlag = false;
            for (int index = 0; index < bookInstancesService.findAll().length; index++) {
                if (bookInstancesService.findAll()[index] != null) {
                    if (bookInstancesService.findAll()[index].getId().equals(instancesId)) {
                        checkFlag = true;
                        break;
                    }
                }
            }
            if (!checkFlag) {
                messages("badInput");
            } else {
                boolean deleteFlag = booksService
                        .findByIsbn(bookInstancesService.findById(instancesId).getIsbn())
                        .removeInstance(instancesId);
                if (!deleteFlag) {
                    messages("allBad");
                } else {
                    deleteFlag = bookInstancesService.delete(instancesId);
                    if (!deleteFlag) {
                        messages("allBad");
                    } else {
                        messages("allGood");
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void updateBookInstance(BufferedReader reader) {
        try {
            if (bookInstancesService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter ID of bookInstance to edit: ");
                input = reader.readLine();
                if (!CustomUtil.isNumber(input)) {
                    messages("badInput");
                } else {
                    instancesId = Integer.valueOf(input);
                    boolean checkFlag = false;
                    for (int index = 0; index < bookInstancesService.findAll().length; index++) {
                        if ((bookInstancesService.findAll()[index] != null) &&
                                (bookInstancesService.findAll()[index].getId().equals(instancesId))) {
                            checkFlag = true;
                            break;
                        }
                    }
                    if (!checkFlag) {
                        messages("badInput");
                    } else {
                        System.out.println();
                        System.out.print("Book's new imprint: ");
                        imprint = reader.readLine();
                        System.out.println();
                        System.out.println("*List of available statuses*");
                        CustomUtil.printStringArray(CustomData.bookStatusPool());
                        System.out.print("Book's new status: ");
                        status = reader.readLine();
                        checkFlag = false;
                        for (int index = 0; index < CustomData.bookStatusPool().length; index++) {
                            if (status.equals(CustomData.bookStatusPool()[index])) {
                                checkFlag = true;
                                break;
                            }
                        }
                        if (!checkFlag) {
                            System.out.println("Wrong status");
                        } else {
                            boolean dueBackCheck = false;
                            if (status.equals("Available")) {
                                dueBack = null;
                            } else {
                                System.out.println();
                                System.out.print("Book's new due-back (MM.DD.YYYY): ");
                                dueBack = reader.readLine();
                                dueBackCheck = CustomUtil.simpleDatePatternCheck(dueBack);
                            }
                            if (dueBackCheck || dueBack == null) {
                                BookInstance bookInstance = new BookInstance();
                                bookInstance.setId(instancesId);
                                bookInstance.setBookIsbn(bookInstancesService.findById(instancesId).getIsbn());
                                bookInstance.setImprint(imprint);
                                bookInstance.setStatus(status);
                                bookInstance.setDueBack(CustomUtil.setDate(dueBack));
                                bookInstancesService.update(bookInstance);
                            } else {
                                messages("dateBad");
                            }
                        }
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void createBookInstance(BufferedReader reader) {
        try {
            if (genresService.findAll()[0] == null) {
                System.out.println("Create some genre first");
            } else if (authorsService.findAll()[0] == null) {
                System.out.println("Create some author first");
            } else if (booksService.findAll()[0] == null) {
                System.out.println("Create some book first");
            } else {
                System.out.println();
                String[] isbns = new String[0];
                for (int index = 0; index < booksService.findAll().length; index++) {
                    if (booksService.findAll()[index] != null) {
                        isbns = Arrays.copyOf(isbns, isbns.length + 1);
                        isbns[isbns.length - 1] = booksService.findAll()[index].getIsbn();
                    }
                }
                System.out.println("*List of available ISBNs*");
                CustomUtil.printStringArray(isbns);
                System.out.print("Book's ISBN: ");
                isbn = reader.readLine();
                boolean checkFlag = false;
                for (int index = 0; index < booksService.findAll().length; index++) {
                    if (booksService.findAll()[index] != null) {
                        if (isbn.equals(booksService.findAll()[index].getIsbn())) {
                            checkFlag = true;
                            break;
                        }
                    }
                }
                if (!checkFlag) {
                    System.out.println("ISBN not found.");
                } else {
                    System.out.println();
                    System.out.print("Book's imprint: ");
                    imprint = reader.readLine();
                    System.out.println();
                    System.out.println("*List of available statuses*");
                    CustomUtil.printStringArray(CustomData.bookStatusPool());
                    System.out.print("Book's status: ");
                    status = reader.readLine();
                    checkFlag = false;
                    for (int index = 0; index < CustomData.bookStatusPool().length; index++) {
                        if (status.equals(CustomData.bookStatusPool()[index])) {
                            checkFlag = true;
                            break;
                        }
                    }
                    if (!checkFlag) {
                        System.out.println("Wrong status");
                    } else {
                        boolean dueBackCheck = false;
                        if (status.equals("Available")) {
                            dueBack = null;
                        } else {
                            System.out.println();
                            System.out.print("Book's due-back (MM.DD.YYYY): ");
                            dueBack = reader.readLine();
                            dueBackCheck = CustomUtil.simpleDatePatternCheck(dueBack);
                        }
                        if (dueBackCheck || dueBack == null) {
                            BookInstance bookInstance = new BookInstance();
                            bookInstance.setBookIsbn(isbn);
                            bookInstance.setImprint(imprint);
                            bookInstance.setStatus(status);
                            bookInstance.setDueBack(CustomUtil.setDate(dueBack));
                            bookInstancesService.create(bookInstance);
                            booksService.findByIsbn(isbn).addInstance(bookInstance.getId());
                        } else {
                            messages("dateBad");
                        }
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void findAllBook(BufferedReader reader) {
        System.out.println();
        if ((booksService.findAll() != null) && (booksService.findAll().length != 0)) {
            for (int index = 0; index < booksService.findAll().length; index++) {
                if (booksService.findAll()[index] != null) {
                    System.out.println(booksService.findAll()[index].toStringBriefWithName());
                }
            }
        } else {
            System.out.println("The books base is empty.");
        }
    }

    private void findByIsbnBook(BufferedReader reader) {
        try {
            if (booksService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter book's ISBN: ");
                isbn = reader.readLine();
                boolean checkFlag = false;
                for (int index = 0; index < booksService.findAll().length; index++) {
                    if ((booksService.findAll()[index] != null) &&
                            (booksService.findAll()[index].getIsbn().equals(isbn))) {
                        checkFlag = true;
                        break;
                    }
                }
                if (!checkFlag) {
                    messages("badInput");
                } else {
                    Book book = booksService.findByIsbn(isbn);
                    System.out.println("Your book: ");
                    System.out.println(book.toString());
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void deleteBook(BufferedReader reader) {
        try {
            System.out.println();
            System.out.print("Enter ISBN of book to delete: ");
            isbn = reader.readLine();
            boolean checkFlag = false;
            for (int index = 0; index < booksService.findAll().length; index++) {
                if (booksService.findAll()[index] != null) {
                    if (booksService.findAll()[index].getIsbn().equals(isbn)) {
                        checkFlag = true;
                        break;
                    }
                }
            }
            if (!checkFlag) {
                messages("badInput");
            } else {
                boolean deleteFlag = authorsService
                        .findById(booksService.findByIsbn(isbn).getAuthorId())
                        .removeBook(isbn);
                if (!deleteFlag) {
                    messages("allBad");
                } else {
                    deleteFlag = genresService
                            .findById(booksService.findByIsbn(isbn).getGenreId())
                            .removeBook(isbn);
                    if (!deleteFlag) {
                        messages("allBad");
                    } else {
                        for (int index = 0; index < bookInstancesService.findAll().length; index++) {
                            if (bookInstancesService.findAll()[index] != null) {
                                if (bookInstancesService.findAll()[index].getIsbn().equals(isbn)) {
                                    bookInstancesService.delete(bookInstancesService.findAll()[index].getId());
                                }
                            }
                        }
                        deleteFlag = booksService.delete(isbn);
                        if (!deleteFlag) {
                            messages("allBad");
                        } else {
                            messages("allGood");
                        }
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void updateBook(BufferedReader reader) {
        try {
            if (booksService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter ID of book to edit: ");
                isbn = reader.readLine();
                boolean checkFlag = false;
                for (int index = 0; index < booksService.findAll().length; index++) {
                    if ((booksService.findAll()[index] != null) &&
                            (booksService.findAll()[index].getIsbn().equals(isbn))) {
                        checkFlag = true;
                        break;
                    }
                }
                if (!checkFlag) {
                    messages("badInput");
                } else {
                    System.out.println();
                    System.out.print("Book's new title: ");
                    title = reader.readLine();
                    System.out.println();
                    System.out.print("Book's new summary: ");
                    summary = reader.readLine();
                    Book book = new Book();
                    book.setAuthorId(booksService.findByIsbn(isbn).getAuthorId());
                    book.setTitle(title);
                    book.setIsbn(isbn);
                    book.setGenreId(booksService.findByIsbn(isbn).getGenreId());
                    book.setSummary(summary);
                    booksService.update(book);
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void createBook(BufferedReader reader) {
        try {
            if (genresService.findAll()[0] == null) {
                System.out.println("Create some genre first");
            } else if (authorsService.findAll()[0] == null) {
                System.out.println("Create some author first");
            } else {
                System.out.println();
                String[] authors = new String[0];
                for (int index = 0; index < authorsService.findAll().length; index++) {
                    if (authorsService.findAll()[index] != null) {
                        authors = Arrays.copyOf(authors, authors.length + 1);
                        authors[authors.length - 1] = authorsService.findAll()[index].getFullName();
                    }
                }
                System.out.println("*Available authors*");
                CustomUtil.printStringArray(authors);
                System.out.print("Book's author ('FirstName' whitespace 'LastName'): ");
                String authorFullName = reader.readLine();
                authorId = null;
                for (int index = 0; index < authorsService.findAll().length; index++) {
                    if (authorsService.findAll()[index] != null) {
                        if (authorFullName.equals(authorsService.findAll()[index].getFullName())) {
                            authorId = authorsService.findAll()[index].getId();
                            break;
                        }
                    }
                }
                if (authorId == null) {
                    System.out.println("The author's not found.");
                } else {
                    System.out.println();
                    String[] genres = new String[0];
                    for (int index = 0; index < genresService.findAll().length; index++) {
                        if (genresService.findAll()[index] != null) {
                            genres = Arrays.copyOf(genres, genres.length + 1);
                            genres[genres.length - 1] = genresService.findAll()[index].getName();
                        }
                    }
                    System.out.println("*Available genres*");
                    CustomUtil.printStringArray(genres);
                    System.out.print("Book's genre: ");
                    genreName = reader.readLine();
                    genreId = null;
                    for (int index = 0; index < genresService.findAll().length; index++) {
                        if (genresService.findAll()[index] != null) {
                            if (genreName.equals(genresService.findAll()[index].getName())) {
                                genreId = genresService.findAll()[index].getId();
                                break;
                            }
                        }
                    }
                    if (genreId == null) {
                        System.out.println("The genre's not found.");
                    } else {
                        System.out.println();
                        System.out.print("Book's title: ");
                        title = reader.readLine();
                        System.out.println();
                        System.out.print("(Format: \\d{1-to-any}-\\d{1-to-any}-\\d{1-to-any}-\\d{1-to-any}-\\d{1-to-any})" + System.lineSeparator() +
                                "Book's ISBN: ");
                        isbn = reader.readLine();
                        System.out.println();
                        System.out.print("Book's summary: ");
                        summary = reader.readLine();
                        Book book = new Book();
                        book.setAuthorId(authorId);
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        book.setGenreId(genreId);
                        book.setSummary(summary);
                        booksService.create(book);
                        authorsService.findById(authorId).addBook(isbn);
                        genresService.findById(genreId).addBook(isbn);
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void findAllGenre(BufferedReader reader) {
        System.out.println();
        if ((genresService.findAll() != null) && (genresService.findAll().length != 0)) {
            for (int index = 0; index < genresService.findAll().length; index++) {
                if (genresService.findAll()[index] != null) {
                    System.out.println(genresService.findAll()[index].toStringBrief());
                }
            }
        } else {
            System.out.println("The genres base is empty.");
        }
    }

    private void findByIdGenre(BufferedReader reader) {
        try {
            if (genresService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter genre's ID: ");
                input = reader.readLine();
                if (!CustomUtil.isNumber(input)) {
                    messages("badInput");
                } else {
                    genreId = Integer.valueOf(input);
                    Genre genre = genresService.findById(genreId);
                    System.out.print("Your genre: ");
                    System.out.println(genre.toString());
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void deleteGenre(BufferedReader reader) {
        try {
            System.out.println();
            System.out.print("Enter ID of genre to delete: ");
            genreId = Integer.valueOf(reader.readLine());
            boolean checkFlag = false;
            for (int index = 0; index < genresService.findAll().length; index++) {
                if (genresService.findAll()[index] != null) {
                    if (genresService.findAll()[index].getId().equals(genreId)) {
                        checkFlag = true;
                        break;
                    }
                }
            }
            if (!checkFlag) {
                messages("badInput");
            } else {
                for (int index = 0; index < booksService.findAll().length; index++) {
                    if (booksService.findAll()[index] != null) {
                        if (booksService.findAll()[index].getGenreId().equals(genreId)) {
                            booksService.findAll()[index].setGenreId(null);
                        }
                    }
                }
                boolean deleteFlag = genresService.delete(genreId);
                if (!deleteFlag) {
                    messages("allBad");
                } else {
                    messages("allGood");
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void updateGenre(BufferedReader reader) {
        try {
            if (genresService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter ID of genre to edit: ");
                input = reader.readLine();
                if (!CustomUtil.isNumber(input)) {
                    messages("badInput");
                } else {
                    genreId = Integer.valueOf(input);
                    boolean checkFlag = false;
                    for (int index = 0; index < genresService.findAll().length; index++) {
                        if ((genresService.findAll()[index] != null) &&
                                (genresService.findAll()[index].getId().equals(genreId))) {
                            checkFlag = true;
                            break;
                        }
                    }
                    if (!checkFlag) {
                        messages("badInput");
                    } else {
                        String[] existedNames = new String[0];
                        for (int index = 0; index < genresService.findAll().length; index++) {
                            if (genresService.findAll()[index] != null) {
                                existedNames = Arrays.copyOf(existedNames, existedNames.length + 1);
                                existedNames[existedNames.length - 1] = genresService.findAll()[index].getName();
                            }
                        }
                        System.out.println();
                        System.out.print("Genre's new name: ");
                        genreName = reader.readLine();
                        if (!CustomUtil.checkUniqueName(existedNames, genreName)) {
                            System.out.println("Genre with such name is already exists");
                        } else {
                            Genre genre = new Genre();
                            genre.setId(genreId);
                            genre.setName(genreName);
                            genresService.update(genre);
                        }
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void createGenre(BufferedReader reader) {
        try {
            String[] existedNames = new String[0];
            for (int index = 0; index < genresService.findAll().length; index++) {
                if (genresService.findAll()[index] != null) {
                    existedNames = Arrays.copyOf(existedNames, existedNames.length + 1);
                    existedNames[existedNames.length - 1] = genresService.findAll()[index].getName();
                }
            }
            System.out.println();
            System.out.print("Genre's name: ");
            genreName = reader.readLine();
            if (!CustomUtil.checkUniqueName(existedNames, genreName)) {
                System.out.println("Genre with such name is already exists");
            } else {
                Genre genre = new Genre();
                genre.setName(genreName);
                genresService.create(genre);
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void findAllAuthor(BufferedReader reader) {
        System.out.println();
        if ((authorsService.findAll() != null) && (authorsService.findAll().length != 0)) {
            for (int index = 0; index < authorsService.findAll().length; index++) {
                if (authorsService.findAll()[index] != null) {
                    System.out.println(authorsService.findAll()[index].toStringBrief());
                }
            }
        } else {
            System.out.println("The authors base is empty.");
        }
    }

    private void findByIdAuthor(BufferedReader reader) {
        try {
            if (authorsService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter author's ID: ");
                input = reader.readLine();
                if (!CustomUtil.isNumber(input)) {
                    messages("badInput");
                } else {
                    authorId = Integer.valueOf(input);
                    Author author = authorsService.findById(authorId);
                    System.out.print("Your author: ");
                    System.out.println(author.toString());
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void deleteAuthor(BufferedReader reader) {
        try {
            System.out.println();
            System.out.print("Enter ID of author to delete: ");
            authorId = Integer.valueOf(reader.readLine());
            boolean checkFlag = false;
            for (int index = 0; index < authorsService.findAll().length; index++) {
                if (authorsService.findAll()[index] != null) {
                    if (authorsService.findAll()[index].getId().equals(authorId)) {
                        checkFlag = true;
                        break;
                    }
                }
            }
            if (!checkFlag) {
                messages("badInput");
            } else {
                for (int indexA = 0; indexA < booksService.findAll().length; indexA++) {
                    if ((booksService.findAll()[indexA] != null) &&
                            (booksService.findAll()[indexA].getAuthorId().equals(authorId))) {
                        for (int indexB = 0; indexB < bookInstancesService.findAll().length; indexB++) {
                            if ((bookInstancesService.findAll()[indexB] != null) &&
                                    (bookInstancesService.findAll()[indexB].getIsbn().equals(booksService.findAll()[indexA].getIsbn()))) {
                                bookInstancesService.delete(bookInstancesService.findAll()[indexB].getId());
                            }
                        }
                        booksService.delete(booksService.findAll()[indexA].getIsbn());
                    }
                }
                boolean deleteFlag = authorsService.delete(authorId);
                if (!deleteFlag) {
                    messages("allBad");
                } else {
                    messages("allGood");
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void updateAuthor(BufferedReader reader) {
        try {
            if (authorsService.findAll()[0] == null) {
                messages("empty");
            } else {
                System.out.println();
                System.out.print("Enter ID of author to edit: ");
                input = reader.readLine();
                if (!CustomUtil.isNumber(input)) {
                    messages("badInput");
                } else {
                    authorId = Integer.valueOf(input);
                    boolean checkFlag = false;
                    for (int index = 0; index < authorsService.findAll().length; index++) {
                        if ((authorsService.findAll()[index] != null) &&
                                (authorsService.findAll()[index].getId().equals(authorId))) {
                            checkFlag = true;
                            break;
                        }
                    }
                    if (!checkFlag) {
                        messages("badInput");
                    } else {
                        System.out.println();
                        System.out.print("Author's new first name: ");
                        firstName = reader.readLine();
                        firstName = CustomUtil.formatName(firstName);
                        System.out.println();
                        System.out.print("Author's new last name: ");
                        lastName = reader.readLine();
                        lastName = CustomUtil.formatName(lastName);
                        String[] existedNames = new String[0];
                        for (int index = 0; index < authorsService.findAll().length; index++) {
                            if (authorsService.findAll()[index] != null) {
                                existedNames = Arrays.copyOf(existedNames, existedNames.length + 1);
                                existedNames[existedNames.length - 1] = authorsService.findAll()[index].getFullName();
                            }
                        }
                        if (!CustomUtil.checkUniqueName(existedNames, (firstName + " " + lastName))) {
                            System.out.println("Author with such name is already exists");
                        } else {
                            System.out.println();
                            System.out.print("Author's new date of birth (MM.DD.YYYY): ");
                            dateOfBirth = reader.readLine();
                            System.out.println();
                            System.out.print("Author's new date of death (MM.DD.YYYY): ");
                            dateOfDeath = reader.readLine();
                            boolean birthDateCheck = CustomUtil.datePatternCheck(dateOfBirth);
                            boolean deathDateCheck = CustomUtil.datePatternCheck(dateOfDeath);
                            boolean dateOrderCheck = false;
                            if (birthDateCheck && deathDateCheck) {
                                dateOrderCheck = (CustomUtil.setDate(dateOfBirth).getTime() < CustomUtil.setDate(dateOfDeath).getTime());
                            }
                            if (birthDateCheck && deathDateCheck && dateOrderCheck) {
                                Author author = new Author();
                                author.setId(authorId);
                                author.setFirstName(firstName);
                                author.setLastName(lastName);
                                author.setDateOfBirth(CustomUtil.setDate(dateOfBirth));
                                author.setDateOfDeath(CustomUtil.setDate(dateOfDeath));
                                authorsService.update(author);
                            } else {
                                messages("dateBad");
                            }
                        }
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }

    private void createAuthor(BufferedReader reader) {
        try {
            System.out.println();
            System.out.print("Author's first name: ");
            firstName = reader.readLine();
            firstName = CustomUtil.formatName(firstName);
            System.out.println();
            System.out.print("Author's last name: ");
            lastName = reader.readLine();
            lastName = CustomUtil.formatName(lastName);
            String[] existedNames = new String[0];
            for (int index = 0; index < authorsService.findAll().length; index++) {
                if (authorsService.findAll()[index] != null) {
                    existedNames = Arrays.copyOf(existedNames, existedNames.length + 1);
                    existedNames[existedNames.length - 1] = authorsService.findAll()[index].getFullName();
                }
            }
            if (!CustomUtil.checkUniqueName(existedNames, (firstName + " " + lastName))) {
                System.out.println("Author with such name is already exists");
            } else {
                System.out.println();
                System.out.print("Author's date of birth (MM.DD.YYYY): ");
                dateOfBirth = reader.readLine();
                System.out.println();
                System.out.print("Author's date of death (MM.DD.YYYY): ");
                dateOfDeath = reader.readLine();
                boolean birthDateCheck = CustomUtil.datePatternCheck(dateOfBirth);
                boolean deathDateCheck = CustomUtil.datePatternCheck(dateOfDeath);
                boolean dateOrderCheck = false;
                if (birthDateCheck && deathDateCheck) {
                    dateOrderCheck = (CustomUtil.setDate(dateOfBirth).getTime() < CustomUtil.setDate(dateOfDeath).getTime());
                }
                if (birthDateCheck && deathDateCheck && dateOrderCheck) {
                    Author author = new Author();
                    author.setFirstName(firstName);
                    author.setLastName(lastName);
                    author.setDateOfBirth(CustomUtil.setDate(dateOfBirth));
                    author.setDateOfDeath(CustomUtil.setDate(dateOfDeath));
                    authorsService.create(author);
                } else {
                    messages("dateBad");
                }
            }
        } catch (IOException exception) {
            System.out.println("problem: " + exception.getMessage());
        }
    }
}
