package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.BooksDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.util.CustomResultSet;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BooksDAOImpl extends BaseDaoImpl implements BooksDAO {

    private final JpaConfig jpaConfig;
    private final String CREATE_BOOK_QUERY = "insert into books values (?,?,?,?,?,?,?,?,?)";
    private final String FIND_BOOK_QUERY = "select * from books where isbn = '";
    private final String UPDATE_BOOK_QUERY = "update books set " +
            "updated = ?, visible = ?, image_url = ?, title = ?, " +
            "publication_date = ?, pages_number = ?, summary = ? where isbn = '";
    private final String FIND_ALL_BOOKS_QUERY_SELECT = "select isbn, b.created, b.updated, b.visible, " +
            "image_url, title, publication_date, pages_number, " +
            "summary, count(distinct ab.author_id) as authors, " +
            "count(distinct gb.genre_id) as genres, " +
            "count(distinct bi.id) as instances from ";
    private final String FIND_ALL_BOOKS_QUERY_BODY = "(books as b left join author_book as ab on " +
            "(b.isbn = ab.book_isbn) left join genre_book " +
            "as gb on (b.isbn = gb.book_isbn)) " +
            "left join book_instances as bi on " +
            "(b.isbn = bi.book_id or bi.book_id is null) ";
    private final String FIND_ALL_BOOKS_QUERY = FIND_ALL_BOOKS_QUERY_SELECT + FIND_ALL_BOOKS_QUERY_BODY;

    public BooksDAOImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

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
    public void update(CustomResultSet<Book> customResultSet) {
        Book book = customResultSet.getEntity();
        List<Long> newAuthorsRelations = (List<Long>) customResultSet.getParams().get(0);
        List<Long> newGenresRelations = (List<Long>) customResultSet.getParams().get(1);
        List<Long> existingAuthorsRelations = new ArrayList<>();
        List<Long> existingGenresRelations = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select * from author_book where book_isbn = '" + book.getIsbn() + "'")) {
            while (resultSet.next()) {
                existingAuthorsRelations.add(resultSet.getLong("author_id"));
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select * from genre_book where book_isbn = '" + book.getIsbn() + "'")) {
            while (resultSet.next()) {
                existingGenresRelations.add(resultSet.getLong("genre_id"));
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        String createWithAuthorRelationsQuery = "";
        List<Long> toCreate = newAuthorsRelations.stream().filter(author_id -> !existingAuthorsRelations.contains(author_id)).toList();
        if (toCreate.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("insert into author_book values ");
            for (Long author_id : toCreate) {
                stringBuilder
                        .append("(")
                        .append(author_id)
                        .append(",'")
                        .append(book.getIsbn())
                        .append("')");
                if (toCreate.indexOf(author_id) < toCreate.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            createWithAuthorRelationsQuery = stringBuilder.toString();
        }
        String deleteWithAuthorRelationsQuery = "";
        List<Long> toDelete = existingAuthorsRelations.stream().filter(author_id -> !newAuthorsRelations.contains(author_id)).toList();
        if (toDelete.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("delete from author_book where book_isbn = '" + book.getIsbn() + "' and (");
            for (Long author_id : toDelete) {
                stringBuilder
                        .append("author_id = ")
                        .append(author_id);
                if (toDelete.indexOf(author_id) < toDelete.size() - 1) {
                    stringBuilder.append(" or ");
                }
            }
            stringBuilder.append(")");
            deleteWithAuthorRelationsQuery = stringBuilder.toString();
        }
        String createWithGenreRelationsQuery = "";
        toCreate = newGenresRelations.stream().filter(genre_id -> !existingAuthorsRelations.contains(genre_id)).toList();
        if (toCreate.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("insert into genre_book values ");
            for (Long genre_id : toCreate) {
                stringBuilder
                        .append("(")
                        .append(genre_id)
                        .append(",'")
                        .append(book.getIsbn())
                        .append("')");
                if (toCreate.indexOf(genre_id) < toCreate.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            createWithGenreRelationsQuery = stringBuilder.toString();
        }
        String deleteWithGenreRelationsQuery = "";
        toDelete = existingGenresRelations.stream().filter(genre_id -> !newAuthorsRelations.contains(genre_id)).toList();
        if (toDelete.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("delete from genre_book where book_isbn = '" + book.getIsbn() + "' and (");
            for (Long genre_id : toDelete) {
                stringBuilder
                        .append("genre_id = ")
                        .append(genre_id);
                if (toDelete.indexOf(genre_id) < toDelete.size() - 1) {
                    stringBuilder.append(" or ");
                }
            }
            stringBuilder.append(")");
            deleteWithGenreRelationsQuery = stringBuilder.toString();
        }
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_BOOK_QUERY + book.getIsbn() + "'")) {
            int index = 0;
            preparedStatement.setTimestamp(++index, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(++index, book.getVisible());
            preparedStatement.setString(++index, book.getImageUrl());
            preparedStatement.setString(++index, book.getTitle());
            preparedStatement.setLong(++index, book.getPublicationDate().getTime());
            preparedStatement.setInt(++index, book.getPagesNumber());
            preparedStatement.setString(++index, book.getSummary());
            preparedStatement.addBatch();
            if (!(createWithAuthorRelationsQuery.isBlank())) {
                if (!(deleteWithAuthorRelationsQuery.isBlank())) {
                    preparedStatement.addBatch(deleteWithAuthorRelationsQuery);
                }
                preparedStatement.addBatch(createWithAuthorRelationsQuery);
            }
            if (!(deleteWithGenreRelationsQuery.isBlank())) {
                preparedStatement.addBatch(deleteWithGenreRelationsQuery);
            }
            if (!(createWithGenreRelationsQuery.isBlank())) {
                preparedStatement.addBatch(createWithGenreRelationsQuery);
            }
            preparedStatement.executeBatch();
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Override
    public void delete(String isbn) {
        deleteByCriteria(jpaConfig, "books", "isbn", isbn);
    }

    @Override
    public boolean existsById(String isbn) {
        return super.existsById(jpaConfig, "books", "isbn", isbn);
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
    public int count() {
        return count(jpaConfig, "books");
    }

    @Override
    public int foreignCount(Long authorId) {
        String filterOption = " left join author_book as ab " +
                "on books.isbn = ab.book_isbn where ab.author_id = " +
                authorId + " group by author_id";
        return count(jpaConfig, "books", filterOption);
    }

    @Override
    public int secondForeignCount(Long genreId) {
        String filterOption = " left join genre_book as gb " +
                "on books.isbn = gb.book_isbn where gb.genre_id = " +
                genreId + " group by genre_id";
        return count(jpaConfig, "books", filterOption);
    }

    @Override
    public DataTableResponse<Book> findAll(DataTableRequest request) {
        List<Book> books = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_BOOKS_QUERY +
                "group by b.isbn " +
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
    public DataTableResponse<Book> findAllByForeignId(DataTableRequest request, Long authorId) {
        List<Book> books = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_BOOKS_QUERY_SELECT + "((" +
                FIND_ALL_BOOKS_QUERY_BODY +
                ") left join author_book as ab1 on b.isbn = ab1.book_isbn)" +
                "where ab1.author_id = " +
                authorId +
                " group by b.isbn " +
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
    public DataTableResponse<Book> findAllBySecondForeignId(DataTableRequest request, Long genreId) {
        List<Book> books = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_BOOKS_QUERY_SELECT + "((" +
                FIND_ALL_BOOKS_QUERY_BODY +
                ") left join genre_book as gb1 on b.isbn = gb1.book_isbn)" +
                "where gb1.genre_id = " +
                genreId +
                " group by b.isbn " +
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
