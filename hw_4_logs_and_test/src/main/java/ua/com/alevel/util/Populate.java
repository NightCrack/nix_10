package ua.com.alevel.util;

import ua.com.alevel.data.CustomData;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.service.impl.AuthorsServiceImpl;
import ua.com.alevel.service.impl.BookInstancesServiceImpl;
import ua.com.alevel.service.impl.BooksServiceImpl;
import ua.com.alevel.service.impl.GenresServiceImpl;

import java.util.Arrays;
import java.util.Date;

public final class Populate {

    private static String firstName;
    private static String lastName;
    private static String title;
    private static Integer authorId;
    private static String summary;
    private static String isbn;
    private static Integer genreId;
    private static String imprint;
    private static String status;
    private static String dueBack;
    private static String genreName;

    private Populate() { }

    public static void PopulateBase(AuthorsServiceImpl authorsService, GenresServiceImpl genresService, BooksServiceImpl booksService, BookInstancesServiceImpl bookInstancesService, int amountOfEntries) {
        autoGenre(amountOfEntries, genresService);
        autoAuthor(amountOfEntries, authorsService);
        autoBook(amountOfEntries, authorsService, genresService, booksService);
        autoBookInstance(amountOfEntries, bookInstancesService, booksService, authorsService);
    }

    public static void autoAuthor(int amountOfEntries, AuthorsServiceImpl authorsService) {
        for (int indexA = 0; indexA < amountOfEntries; indexA++) {
            String[] existedFirstNames = new String[0];
            String[] existedLastNames = new String[0];
            for (int indexB = 0; indexB < authorsService.findAll().length; indexB++) {
                if (authorsService.findAll()[indexB] != null) {
                    existedFirstNames = Arrays.copyOf(existedFirstNames, existedFirstNames.length + 1);
                    existedLastNames = Arrays.copyOf(existedLastNames, existedLastNames.length + 1);
                    existedFirstNames[existedFirstNames.length - 1] = authorsService.findAll()[indexB].getFirstName();
                    existedLastNames[existedLastNames.length - 1] = authorsService.findAll()[indexB].getLastName();
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

    public static void autoGenre(int amountOfEntries, GenresServiceImpl genresService) {
        for (int indexA = 0; indexA < amountOfEntries; indexA++) {
            String[] existedNames = new String[0];
            for (int indexB = 0; indexB < genresService.findAll().length; indexB++) {
                if (genresService.findAll()[indexB] != null) {
                    existedNames = Arrays.copyOf(existedNames, existedNames.length + 1);
                    existedNames[existedNames.length - 1] = genresService.findAll()[indexB].getName();
                }
            }
            genreName = CustomUtil.createUniqueName(existedNames, CustomData.genreNamePool());
            Genre genre = new Genre();
            genre.setName(genreName);
            genresService.create(genre);
        }
    }

    public static void autoBook(int amountOfEntries, AuthorsServiceImpl authorsService, GenresServiceImpl genresService, BooksServiceImpl booksService) {
        for (int index = 0; index < amountOfEntries; index++) {
            int entriesAmount = 0;
            for (Author author : authorsService.findAll()) {
                if (author != null) {
                    ++entriesAmount;
                }
            }
            authorId = authorsService.findAll()[(int) (Math.round(Math.random() * (entriesAmount - 1)))].getId();
            String[] existingNames = new String[0];
            String[] existingIsbns = new String[0];
            for (int indexB = 0; indexB < booksService.findAll().length; indexB++) {
                if (booksService.findAll()[indexB] != null) {
                    existingNames = Arrays.copyOf(existingNames, existingNames.length + 1);
                    existingIsbns = Arrays.copyOf(existingIsbns, existingIsbns.length + 1);
                    existingNames[existingNames.length - 1] = booksService.findAll()[indexB].getTitle();
                    existingIsbns[existingIsbns.length - 1] = booksService.findAll()[indexB].getIsbn();
                }
            }
            title = CustomUtil.createUniqueName(existingNames, CustomData.bookTitlePool());
            isbn = CustomUtil.createUniqueName(existingIsbns, CustomData.isbnPool());
            entriesAmount = 0;
            for (Genre genre : genresService.findAll()) {
                if (genre != null) {
                    ++entriesAmount;
                }
            }
            genreId = genresService.findAll()[(int) (Math.round(Math.random() * (entriesAmount - 1)))].getId();
            summary = "Book's summary.";
            Book book = new Book();
            book.setAuthorId(authorId);
            book.setTitle(title);
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

    public static void autoBookInstance(int amountOfEntries, BookInstancesServiceImpl bookInstancesService, BooksServiceImpl booksService, AuthorsServiceImpl authorsService) {
        for (int index = 0; index < amountOfEntries; index++) {
            int entriesAmount = 0;
            for (Book book : booksService.findAll()) {
                if (book != null) {
                    ++entriesAmount;
                }
            }
            isbn = booksService.findAll()[(int) (Math.round(Math.random() * (entriesAmount - 1)))].getIsbn();
            imprint = CustomUtil.nameOpt(CustomData.publisherPool()) + ", " + CustomUtil.year.format(CustomUtil.generateDate(authorsService.findById(booksService.findByIsbn(isbn).getAuthorId()).getDateOfBirth()));
            status = CustomUtil.nameOpt(CustomData.bookStatusPool());
            if (status.equals("Available")) {
                dueBack = null;
            } else {
                Date now = new Date();
                dueBack = CustomUtil.dateToString(new Date(now.getTime() + (long) (Math.random() * now.getTime())));
            }
            BookInstance bookInstance = new BookInstance();
            bookInstance.setBookIsbn(isbn);
            bookInstance.setImprint(imprint);
            bookInstance.setStatus(status);
            bookInstance.setDueBack(CustomUtil.setDate(dueBack));
            bookInstancesService.create(bookInstance);
            booksService.findByIsbn(isbn).addInstance(bookInstance.getId());
        }
    }


}
