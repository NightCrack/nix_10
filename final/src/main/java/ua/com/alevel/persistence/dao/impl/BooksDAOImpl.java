package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BooksDAOImpl extends BaseDaoImpl implements BooksDAO {

    private final JpaConfig jpaConfig;

    public BooksDAOImpl(JpaConfig jpaConfig) {
        super(jpaConfig);
        this.jpaConfig = jpaConfig;
    }

    private static final String CREATE_BOOK_QUERY = "insert into books values (?,?,?,?,?,?,?,?,?)";
    private static final String FIND_BOOK_QUERY = "select * from books where isbn = '";
    private static final String FIND_ALL_BOOKS_QUERY =
            "select isbn, b.created, b.updated, b.visible, " +
            "image_url, title, publication_date, pages_number, " +
            "summary, count(distinct author_id) as authors, " +
            "count(distinct genre_id) as genres, " +
            "count(distinct bi.id) as instances from " +
            "(books as b left join author_book as ab on " +
            "(b.isbn = ab.book_isbn) left join genre_book " +
            "as gb on (b.isbn = gb.book_isbn)) " +
            "left join book_instances as bi on " +
            "(b.isbn = bi.book_id or bi.book_id is null) ";


    @Override
    public void create(CustomResultSet<Book> customResultSet) {
        List<Long> authorsId = (List<Long>) customResultSet.getParams().get(0);
        List<Long> genresId = (List<Long>) customResultSet.getParams().get(1);
        Book book = customResultSet.getEntity();
        String authorBookQuery = "";
        if (authorsId.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("insert into author_book values ");
            for (Long id : authorsId) {
                stringBuilder
                        .append("(")
                        .append(id)
                        .append(",'")
                        .append(book.getIsbn())
                        .append("')");
                if (authorsId.indexOf(id) < authorsId.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            authorBookQuery = stringBuilder.toString();
        }
        String genreBookQuery = "";
        if (genresId.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("insert into genre_book values ");
            for (Long id : genresId) {
                stringBuilder
                        .append("(")
                        .append(id)
                        .append(",'")
                        .append(book.getIsbn())
                        .append("')");
                if (genresId.indexOf(id) < genresId.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            genreBookQuery = stringBuilder.toString();
        }
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_BOOK_QUERY)) {
            int index = 0;
            preparedStatement.setString(++index, book.getIsbn());
            preparedStatement.setTimestamp(++index, Timestamp.from(book.getCreated()));
            preparedStatement.setTimestamp(++index, Timestamp.from(book.getUpdated()));
            preparedStatement.setBoolean(++index, book.getVisible());
            preparedStatement.setString(++index, book.getImageUrl());
            preparedStatement.setString(++index, book.getTitle());
            preparedStatement.setLong(++index, book.getPublicationDate().getTime());
            preparedStatement.setInt(++index, book.getPagesNumber());
            preparedStatement.setString(++index, book.getSummary());
            preparedStatement.executeUpdate();
            jpaConfig.getStatement().executeUpdate(authorBookQuery);
            jpaConfig.getStatement().executeUpdate(genreBookQuery);
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Override
    public void update(Book entity) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Book findById(String isbn) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_BOOK_QUERY + isbn + "'")) {
            if (resultSet.next()) {
                return convertResultSetToBookShort(resultSet);
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        return null;
    }

    @Override
    public DataTableResponse<Book> findAll(DataTableRequest request) {
        List<Book> books = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_BOOKS_QUERY +
                "group by isbn " +
                "order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                CustomResultSet<Book> customResultSet = convertResultSetToBook(resultSet);
                books.add(customResultSet.getEntity());
                otherParamMap.put(customResultSet.getEntity().getIsbn(), (List<Integer>) customResultSet.getParams());
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Book> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(books);
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public int count() {
        return count("books");
    }

    @Override
    public void deleteAllByForeignId(Long aLong) {

    }

    @Override
    public List<Book> findAllByForeignId(Long authorId) {
        String query = FIND_ALL_BOOKS_QUERY +
                "where ab.author_id = " +
                authorId +
                " group by isbn ";
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            List<Book> returnValue = new ArrayList<>();
            while (resultSet.next()) {
                returnValue.add(convertResultSetToBook(resultSet).getEntity());
            }
            return returnValue;
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
            return null;
        }
    }

    @Override
    public void deleteAllBySecondForeignId(Long genreId) {

    }

    @Override
    public List<Book> findAllBySecondForeignId(Long genreId) {
        String query = FIND_ALL_BOOKS_QUERY +
                "where gb.genre_id = " +
                genreId +
                " group by isbn ";
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            List<Book> returnValue = new ArrayList<>();
            while (resultSet.next()) {
                returnValue.add(convertResultSetToBook(resultSet).getEntity());
            }
            return returnValue;
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
            return null;
        }
    }

    private CustomResultSet<Book> convertResultSetToBook(ResultSet resultSet) throws SQLException {
        String isbn = resultSet.getString("isbn");
        String imgUrl = resultSet.getString("image_url");
        String title = resultSet.getString("title");
        Long publicationDate = resultSet.getLong("publication_date");
        Integer pagesNumber = resultSet.getInt("pages_number");
        String summary = resultSet.getString("summary");
        Instant created = resultSet.getTimestamp("created").toInstant();
        Instant updated = resultSet.getTimestamp("updated").toInstant();
        Boolean visible = resultSet.getBoolean("visible");
        Integer authorsCount = resultSet.getInt("authors");
        Integer genresCount = resultSet.getInt("genres");
        Integer bookInstancesCount = resultSet.getInt("instances");
        Book book = new Book();
        book.setIsbn(isbn);
        book.setImageUrl(imgUrl);
        book.setTitle(title);
        book.setPublicationDate(new Date(publicationDate));
        book.setPagesNumber(pagesNumber);
        book.setSummary(summary);
        book.setCreated(created);
        book.setUpdated(updated);
        book.setVisible(visible);
        List<Integer> values = new ArrayList<>();
        values.add(authorsCount);
        values.add(genresCount);
        values.add(bookInstancesCount);
        return new CustomResultSet<>(book, values);
    }

    private Book convertResultSetToBookShort(ResultSet resultSet) throws SQLException {
        String isbn = resultSet.getString("isbn");
        String imgUrl = resultSet.getString("image_url");
        String title = resultSet.getString("title");
        Long publicationDate = resultSet.getLong("publication_date");
        Integer pagesNumber = resultSet.getInt("pages_number");
        String summary = resultSet.getString("summary");
        Instant created = resultSet.getTimestamp("created").toInstant();
        Instant updated = resultSet.getTimestamp("updated").toInstant();
        Boolean visible = resultSet.getBoolean("visible");
        Book book = new Book();
        book.setIsbn(isbn);
        book.setImageUrl(imgUrl);
        book.setTitle(title);
        book.setPublicationDate(new Date(publicationDate));
        book.setPagesNumber(pagesNumber);
        book.setSummary(summary);
        book.setCreated(created);
        book.setUpdated(updated);
        book.setVisible(visible);
        return book;
    }
}
