package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Author;
import ua.com.alevel.util.CustomResultSet;

import java.sql.Date;
import java.sql.*;
import java.time.Instant;
import java.util.*;

@Service
public class AuthorsDAOImpl extends BaseDaoImpl implements AuthorsDAO {

    private final JpaConfig jpaConfig;
    private final String CREATE_AUTHOR_QUERY = "insert into authors values (default,?,?,?,?,?,?,?)";
    private final String UPDATE_AUTHOR_QUERY = "update authors set updated = ?, visible = ?, first_name = ?, last_name = ?, birth_date = ?, death_date = ? where id = ";
    private final String FIND_ALL_AUTHORS_QUERY_SELECT = "select id, created, updated, visible, " +
            "first_name, last_name, birth_date, " +
            "death_date, count(ab.book_isbn) " +
            "as books from ";
    private final String FIND_ALL_AUTHORS_QUERY_BODY = "authors as au " +
            "left join author_book as ab on" +
            " au.id = ab.author_id ";
    private final String FIND_ALL_AUTHORS_QUERY =
            FIND_ALL_AUTHORS_QUERY_SELECT +
                    FIND_ALL_AUTHORS_QUERY_BODY;

    public AuthorsDAOImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(CustomResultSet<Author> customResultSet) {
        Author author = customResultSet.getEntity();
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_AUTHOR_QUERY)) {
            int index = 0;
            preparedStatement.setTimestamp(++index, Timestamp.from(author.getCreated()));
            preparedStatement.setTimestamp(++index, Timestamp.from(author.getUpdated()));
            preparedStatement.setBoolean(++index, author.getVisible());
            preparedStatement.setString(++index, author.getFirstName());
            preparedStatement.setString(++index, author.getLastName());
            preparedStatement.setLong(++index, author.getBirthDate().getTime());
            preparedStatement.setLong(++index, author.getDeathDate().getTime());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Override
    public void update(CustomResultSet<Author> customResultSet) {
        Author author = customResultSet.getEntity();
        List<String> newBooksRelations = (List<String>) customResultSet.getParams().get(0);
        List<String> existingBooksRelations = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select * from author_book where author_id = " + author.getId())) {
            while (resultSet.next()) {
                existingBooksRelations.add(resultSet.getString("book_isbn"));
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        String createRelationsQuery = "";
        List<String> toCreate = newBooksRelations.stream().filter(isbn -> !existingBooksRelations.contains(isbn)).toList();
        if (toCreate.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("insert into author_book values ");
            for (String isbn : toCreate) {
                stringBuilder
                        .append("(")
                        .append(author.getId())
                        .append(",'")
                        .append(isbn)
                        .append("')");
                if (toCreate.indexOf(isbn) < toCreate.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            createRelationsQuery = stringBuilder.toString();
        }
        String deleteRelationsQuery = "";
        List<String> toDelete = existingBooksRelations.stream().filter(isbn -> !newBooksRelations.contains(isbn)).toList();
        if (toDelete.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("delete from author_book where author_id = " + author.getId() + " and (");
            for (String isbn : toDelete) {
                stringBuilder
                        .append("book_isbn = '")
                        .append(isbn)
                        .append("'");
                if (toDelete.indexOf(isbn) < toDelete.size() - 1) {
                    stringBuilder.append(" or ");
                }
            }
            stringBuilder.append(")");
            deleteRelationsQuery = stringBuilder.toString();
        }
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_AUTHOR_QUERY + author.getId())) {
            int index = 0;
            preparedStatement.setTimestamp(++index, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(++index, author.getVisible());
            preparedStatement.setString(++index, author.getFirstName());
            preparedStatement.setString(++index, author.getLastName());
            preparedStatement.setLong(++index, author.getBirthDate().getTime());
            preparedStatement.setLong(++index, author.getDeathDate().getTime());
            preparedStatement.addBatch();
            if (!deleteRelationsQuery.isBlank()) {
                preparedStatement.addBatch(deleteRelationsQuery);
            }
            if (!createRelationsQuery.isBlank()) {
                preparedStatement.addBatch(createRelationsQuery);
            }
            preparedStatement.executeBatch();
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Override
    public void delete(Long id) {
        deleteByCriteria(jpaConfig, "authors", "id", id);
    }

    @Override
    public boolean existsById(Long id) {
        return super.existsById(jpaConfig, "authors", "id", id);
    }

    @Override
    public Author findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_AUTHORS_QUERY + "where id =" + id)) {
            if (resultSet.next()) {
                return convertResultSetToAuthor(resultSet).getEntity();
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        return null;
    }

    @Override
    public DataTableResponse<Author> findAll(DataTableRequest request) {
        List<Author> authors = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_AUTHORS_QUERY +
                "group by au.id " +
                "order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                CustomResultSet<Author> authorResultSet = convertResultSetToAuthor(resultSet);
                authors.add(authorResultSet.getEntity());
                otherParamMap.put(authorResultSet.getEntity().getId(), (List<Integer>) authorResultSet.getParams());
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Author> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(authors);
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public int count() {
        return count(jpaConfig, "authors");
    }

    @Override
    public int foreignCount(String isbn) {
        String filterOption = " left join author_book as ab " +
                "on authors.id = ab.author_id where ab.book_isbn = '" +
                isbn + "' group by ab.book_isbn";
        return count(jpaConfig, "authors", filterOption);
    }

    private CustomResultSet<Author> convertResultSetToAuthor(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Long birthDate = resultSet.getLong("birth_date");
        Long deathDate = resultSet.getLong("death_date");
        Instant created = resultSet.getTimestamp("created").toInstant();
        Instant updated = resultSet.getTimestamp("updated").toInstant();
        Boolean visible = resultSet.getBoolean("visible");
        Integer bookCount = resultSet.getInt("books");
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setBirthDate(new Date(birthDate));
        author.setDeathDate(new Date(deathDate));
        author.setCreated(created);
        author.setUpdated(updated);
        author.setVisible(visible);
        return new CustomResultSet<>(author, Collections.singletonList(bookCount));
    }

//    private Author convertResultSetToAuthorShort(ResultSet resultSet) throws SQLException {
//        Long id = resultSet.getLong("id");
//        String firstName = resultSet.getString("first_name");
//        String lastName = resultSet.getString("last_name");
//        Author author = new Author();
//        author.setId(id);
//        author.setFirstName(firstName);
//        author.setLastName(lastName);
//        return author;
//    }

    @Override
    public DataTableResponse<Author> findAllByForeignId(DataTableRequest request, String isbn) {
        List<Author> authors = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_AUTHORS_QUERY_SELECT + "((" +
                FIND_ALL_AUTHORS_QUERY_BODY +
                ") left join author_book as ab1 on au.id = ab1.author_id)" +
                "where ab1.book_isbn = '" +
                isbn + "' " +
                "group by au.id " +
                "order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                CustomResultSet<Author> authorResultSet = convertResultSetToAuthor(resultSet);
                authors.add(authorResultSet.getEntity());
                otherParamMap.put(authorResultSet.getEntity().getId(), (List<Integer>) authorResultSet.getParams());
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Author> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(authors);
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }
}
