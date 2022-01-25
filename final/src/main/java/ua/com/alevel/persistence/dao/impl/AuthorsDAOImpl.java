package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.AuthorsDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Author;

import java.sql.Date;
import java.sql.*;
import java.time.Instant;
import java.util.*;

@Service
public class AuthorsDAOImpl extends BaseDaoImpl implements AuthorsDAO {

    private final JpaConfig jpaConfig;

    public AuthorsDAOImpl(JpaConfig jpaConfig) {
        super(jpaConfig);
        this.jpaConfig = jpaConfig;
    }

    private static final String CREATE_AUTHOR_QUERY = "insert into authors values (default,?,?,?,?,?,?,?)";
    private static final String FIND_ALL_AUTHORS_QUERY =
            "select id, created, updated, visible, " +
                    "first_name, last_name, birth_date, " +
                    "death_date, count(ab.book_isbn) " +
                    "as books from authors as au " +
                    "left join author_book as ab on" +
                    " au.id = ab.author_id ";

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
    public void update(Author entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public boolean existsById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select count(*) as number from authors where id =" + id)) {
            if (resultSet.next()) {
                return resultSet.getInt("number") == 1;
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        return false;
    }

    @Override
    public Author findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_AUTHORS_QUERY + "where id ="+ id)) {
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
        return count("authors");
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

    private Author convertResultSetToAuthorShort(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return author;
    }

    @Override
    public void deleteAllByForeignId(String s) {

    }

    @Override
    public List<Author> findAllByForeignId(String isbn) {
        String query = "select a.id as id, first_name, last_name " +
                "from author_book as ab inner join authors as a on a.id = ab.author_id " +
                "where book_isbn = '" + isbn + "'";
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            List<Author> returnValue = new ArrayList<>();
            while (resultSet.next()) {
                returnValue.add(convertResultSetToAuthorShort(resultSet));
            }
            return returnValue;
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
            return Collections.emptyList();
        }
    }
}
