package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.GenresDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Genre;
import ua.com.alevel.type.GenreType;
import ua.com.alevel.util.CustomResultSet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class GenresDAOImpl extends BaseDaoImpl implements GenresDAO {

    private final String CREATE_GENRE_QUERY = "insert into genres values(default, ?, ?, ?, ?)";
    private final String UPDATE_GENRE_QUERY = "update genres set updated = ?, visible = ?, genre_type = ? where id = ";
    private final String FIND_ALL_GENRES_QUERY_SELECT = "select id, created, updated, visible, " +
            "genre_type, count(gb.book_isbn) as books from ";
    private final String FIND_ALL_GENRES_QUERY_BODY = "genres as g " +
            "left join genre_book as gb on " +
            "g.id = gb.genre_id ";
    private final String FIND_ALL_GENRES_QUERY =
            FIND_ALL_GENRES_QUERY_SELECT +
                    FIND_ALL_GENRES_QUERY_BODY;
    private final JpaConfig jpaConfig;

    public GenresDAOImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(CustomResultSet<Genre> customResultSet) {
        Genre genre = customResultSet.getEntity();
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_GENRE_QUERY)) {
            int index = 0;
            preparedStatement.setTimestamp(++index, Timestamp.from(genre.getCreated()));
            preparedStatement.setTimestamp(++index, Timestamp.from(genre.getUpdated()));
            preparedStatement.setBoolean(++index, genre.getVisible());
            preparedStatement.setString(++index, genre.getGenreType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(CustomResultSet<Genre> customResultSet) {
        Genre genre = customResultSet.getEntity();
        List<String> newBooksRelations = (List<String>) customResultSet.getParams().get(0);
        List<String> existingBooksRelations = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select * from genre_book where genre_id = " + genre.getId())) {
            while (resultSet.next()) {
                existingBooksRelations.add(resultSet.getString("book_isbn"));
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        String createRelationsQuery = "";
        List<String> toCreate = newBooksRelations.stream().filter(isbn -> !existingBooksRelations.contains(isbn)).toList();
        if (toCreate.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("insert into genre_book values ");
            for (String isbn : toCreate) {
                stringBuilder
                        .append("(")
                        .append(genre.getId())
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
            StringBuilder stringBuilder = new StringBuilder("delete from genre_book where genre_id = " + genre.getId() + " and (");
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
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_GENRE_QUERY + genre.getId())) {
            int index = 0;
            preparedStatement.setTimestamp(++index, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(++index, genre.getVisible());
            preparedStatement.setString(++index, genre.getGenreType().name());
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
        deleteByCriteria(jpaConfig, "genres", "id", id);
    }

    @Override
    public boolean existsById(Long id) {
        return super.existsById(jpaConfig, "genres", "id", id);
    }

    @Override
    public Genre findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_GENRES_QUERY + "where id = " + id)) {
            if (resultSet.next()) {
                return convertResultSetToGenre(resultSet).getEntity();
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        return null;
    }

    @Override
    public DataTableResponse<Genre> findAll(DataTableRequest request) {
        List<Genre> genres = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_GENRES_QUERY +
                "group by g.id " +
                "order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                CustomResultSet<Genre> customResultSet = convertResultSetToGenre(resultSet);
                genres.add(customResultSet.getEntity());
                otherParamMap.put(customResultSet.getEntity().getId(), (List<Integer>) customResultSet.getParams());
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Genre> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(genres);
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public int count() {
        return count(jpaConfig, "genres");
    }

    @Override
    public int foreignCount(String isbn) {
        String filterOption = " left join genre_book " +
                "as gb on genres.id = gb.genre_id " +
                "where gb.book_isbn = '" + isbn +
                "' group by gb.book_isbn";
        return count(jpaConfig, "genres", filterOption);
    }

    private CustomResultSet<Genre> convertResultSetToGenre(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String typeName = resultSet.getString("genre_type");
        Instant created = resultSet.getTimestamp("created").toInstant();
        Instant updated = resultSet.getTimestamp("updated").toInstant();
        Boolean visible = resultSet.getBoolean("visible");
        GenreType genreType = GenreType.valueOf(typeName);
        Integer booksCount = resultSet.getInt("books");
        Genre genre = new Genre();
        genre.setId(id);
        genre.setGenreType(genreType);
        genre.setCreated(created);
        genre.setUpdated(updated);
        genre.setVisible(visible);
        return new CustomResultSet<>(genre, Collections.singletonList(booksCount));
    }

    @Override
    public DataTableResponse<Genre> findAllByForeignId(DataTableRequest request, String isbn) {
        List<Genre> genres = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_GENRES_QUERY_SELECT + "((" +
                FIND_ALL_GENRES_QUERY_BODY +
                ") left join genre_book as gb1 on g.id = gb1.genre_id)" +
                "where gb1.book_isbn = '" +
                isbn + "' " +
                "group by g.id " +
                "order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                CustomResultSet<Genre> customResultSet = convertResultSetToGenre(resultSet);
                genres.add(customResultSet.getEntity());
                otherParamMap.put(customResultSet.getEntity().getId(), (List<Integer>) customResultSet.getParams());
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Genre> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(genres);
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }
}
