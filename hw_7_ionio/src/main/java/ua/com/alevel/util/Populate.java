package ua.com.alevel.util;

import ua.com.alevel.data.CustomData;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.service.AuthorsService;
import ua.com.alevel.service.BookInstancesService;
import ua.com.alevel.service.BooksService;
import ua.com.alevel.service.GenresService;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public final class Populate {

    private static String firstName;
    private static String lastName;
    private static String name;
    private static Long authorId;
    private static String summary;
    private static String isbn;
    private static Long genreId;
    private static String imprint;
    private static String status;
    private static String dueBack;
    private static String genreName;

    private Populate() {
    }

    public static void PopulateBase(AuthorsService<Long> authorsService, GenresService<Long> genresService, BooksService<String> booksService, BookInstancesService<Long> bookInstancesService, int amountOfEntries) {
        autoGenre(amountOfEntries, genresService);
        autoAuthor(amountOfEntries, authorsService);
        autoBook(amountOfEntries, authorsService, genresService, booksService);
        autoBookInstance(amountOfEntries, bookInstancesService, booksService, authorsService);
    }

    public static void autoAuthor(int amountOfEntries, AuthorsService<Long> authorsService) {
        for (int indexA = 0; indexA < amountOfEntries; indexA++) {
            String[] existedFirstNames = new String[0];
            String[] existedLastNames = new String[0];
            for (int indexB = 0; indexB < authorsService.findAll().size(); indexB++) {
                if (((List<Author>) authorsService.findAll()).get(indexB) != null) {
                    existedFirstNames = Arrays.copyOf(existedFirstNames, existedFirstNames.length + 1);
                    existedLastNames = Arrays.copyOf(existedLastNames, existedLastNames.length + 1);
                    existedFirstNames[existedFirstNames.length - 1] = ((List<Author>) authorsService.findAll()).get(indexB).getFirstName();
                    existedLastNames[existedLastNames.length - 1] = ((List<Author>) authorsService.findAll()).get(indexB).getLastName();
                }
            }

            firstName = CustomUtil.createUniqueName(existedFirstNames, CustomData.firstNamePool());
            lastName = CustomUtil.createUniqueName(existedLastNames, CustomData.lastNamePool());
            Date birth = CustomUtil.generateDate();
            Date death = CustomUtil.generateDate(birth);
            Author author = new Author();
            author.setFirstName(firstName);
            author.setLastName(lastName);
            author.setDateOfBirth(birth);
            author.setDateOfDeath(death);
            authorsService.create(author);
        }
    }

    public static void autoGenre(int amountOfEntries, GenresService<Long> genresService) {
        for (int indexA = 0; indexA < amountOfEntries; indexA++) {
            String[] existedNames = new String[0];
            for (int indexB = 0; indexB < genresService.findAll().size(); indexB++) {
                if (((List<Genre>) genresService.findAll()).get(indexB) != null) {
                    existedNames = Arrays.copyOf(existedNames, existedNames.length + 1);
                    existedNames[existedNames.length - 1] = ((List<Genre>) genresService.findAll()).get(indexB).getName();
                }
            }
            genreName = CustomUtil.createUniqueName(existedNames, CustomData.genreNamePool());
            Genre genre = new Genre();
            genre.setName(genreName);
            genresService.create(genre);
        }
    }

    public static void autoBook(int amountOfEntries, AuthorsService<Long> authorsService, GenresService<Long> genresService, BooksService<String> booksService) {
        for (int index = 0; index < amountOfEntries; index++) {
            int entriesAmount = 0;
            for (Author author : authorsService.findAll()) {
                if (author != null) {
                    ++entriesAmount;
                }
            }
            authorId = ((List<Author>) authorsService.findAll()).get((int) (Math.round(Math.random() * (entriesAmount - 1)))).getId();
            String[] existingNames = new String[0];
            String[] existingIsbns = new String[0];
            for (int indexB = 0; indexB < booksService.findAll().size(); indexB++) {
                if (((List<Book>) booksService.findAll()).get(indexB) != null) {
                    existingNames = Arrays.copyOf(existingNames, existingNames.length + 1);
                    existingIsbns = Arrays.copyOf(existingIsbns, existingIsbns.length + 1);
                    existingNames[existingNames.length - 1] = ((List<Book>) booksService.findAll()).get(indexB).getName();
                    existingIsbns[existingIsbns.length - 1] = ((List<Book>) booksService.findAll()).get(indexB).getIsbn();
                }
            }
            name = CustomUtil.createUniqueName(existingNames, CustomData.bookTitlePool());
            isbn = CustomUtil.createUniqueName(existingIsbns, CustomData.isbnPool());
            entriesAmount = 0;
            for (Genre genre : genresService.findAll()) {
                if (genre != null) {
                    ++entriesAmount;
                }
            }
            genreId = ((List<Genre>) genresService.findAll()).get((int) (Math.round(Math.random() * (entriesAmount - 1)))).getId();
            summary = "Book's summary.";
            Book book = new Book();
            book.setAuthorId(authorId);
            book.setName(name);
            book.setIsbn(isbn);
            book.setGenreId(genreId);
            book.setSummary(summary);
            booksService.create(book);
            if (authorId != null) {
                authorsService.findById(authorId).addBook(isbn);
            }
            if (genreId != null) {
                genresService.findById(genreId).addBook(isbn);
            }
        }
    }

    public static void autoBookInstance(int amountOfEntries, BookInstancesService<Long> bookInstancesService, BooksService<String> booksService, AuthorsService<Long> authorsService) {
        List<Book> books = booksService.findAll();
        for (int indexA = 0; indexA < amountOfEntries; indexA++) {
            isbn = books.get((int) (Math.round(Math.random() * (books.size() - 1)))).getIsbn();
            imprint = CustomUtil.nameOpt(CustomData.publisherPool()) + ", " +
                    CustomUtil.year.format(CustomUtil
                            .generateDate(authorsService
                                    .findById(booksService
                                            .findById(isbn)
                                            .getAuthorId())
                                    .getDateOfBirth()));
            status = CustomUtil.nameOpt(CustomData.bookStatusPool());
            if (status.equals("Available")) {
                dueBack = CustomUtil.dateToString(new Date(System.currentTimeMillis()));
            } else {
                Date now = new Date(System.currentTimeMillis());
                dueBack = CustomUtil.dateToString(new Date(now.getTime() + (long) (Math.random() * now.getTime())));
            }
            BookInstance bookInstance = new BookInstance();
            bookInstance.setBookIsbn(isbn);
            bookInstance.setImprint(imprint);
            bookInstance.setStatus(status);
            bookInstance.setDueBack(CustomUtil.setDate(dueBack));
            bookInstancesService.create(bookInstance);
            booksService.findById(isbn).addInstance(bookInstance.getId());
        }
    }


}
