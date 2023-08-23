package ua.com.alevel.persistence.dao.impl;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.BookInstancesDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Book;
import ua.com.alevel.persistence.entity.BookInstance;
import ua.com.alevel.type.StatusType;
import ua.com.alevel.util.CustomResultSet;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookInstancesDAOImpl extends BaseDaoImpl implements BookInstancesDAO {

    private final JpaConfig jpaConfig;
    private final String CREATE_BOOK_INSTANCE_QUERY = "insert into book_instances values (default,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE_BOOK_INSTANCE_QUERY = "update book_instances set " +
            "updated = ?, visible = ?, imprint = ?, publishing_date = ?, " +
            "country_code = ?, due_back = ?, status = ?, book_id = ? where id = ";
    private final String FIND_ALL_BOOK_INSTANCES_QUERY_SELECT = "select id, bi.created, bi.updated, bi.visible, " +
            "imprint, publishing_date, country_code, due_back, " +
            "status, book_id, isbn, b.created, b.updated, b.visible, " +
            "image_url, title, publication_date, pages_number, " +
            "summary from ";
    private final String FIND_ALL_BOOK_INSTANCES_QUERY_BODY = "book_instances as bi " +
            "inner join books as b on bi.book_id = b.isbn ";
    private final String FIND_ALL_BOOK_INSTANCES_QUERY =
            FIND_ALL_BOOK_INSTANCES_QUERY_SELECT +
                    FIND_ALL_BOOK_INSTANCES_QUERY_BODY;


    public BookInstancesDAOImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(CustomResultSet<BookInstance> customResultSet) {
        BookInstance bookInstance = customResultSet.getEntity();
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_BOOK_INSTANCE_QUERY)) {
            int index = 0;
            preparedStatement.setTimestamp(++index, Timestamp.from(bookInstance.getCreated()));
            preparedStatement.setTimestamp(++index, Timestamp.from(bookInstance.getUpdated()));
            preparedStatement.setBoolean(++index, bookInstance.getVisible());
            preparedStatement.setString(++index, bookInstance.getImprint());
            preparedStatement.setLong(++index, bookInstance.getPublishingDate().getTime());
            preparedStatement.setString(++index, bookInstance.getCountryCode().name());
            preparedStatement.setTimestamp(++index, Timestamp.from(bookInstance.getDueBack()));
            preparedStatement.setString(++index, bookInstance.getStatus().name());
            preparedStatement.setString(++index, bookInstance.getBook().getIsbn());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Override
    public void update(CustomResultSet<BookInstance> customResultSet) {
        BookInstance bookInstance = customResultSet.getEntity();
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_BOOK_INSTANCE_QUERY + bookInstance.getId())) {
            int index = 0;
            preparedStatement.setTimestamp(++index, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(++index, bookInstance.getVisible());
            preparedStatement.setString(++index, bookInstance.getImprint());
            preparedStatement.setLong(++index, bookInstance.getPublishingDate().getTime());
            preparedStatement.setString(++index, bookInstance.getCountryCode().name());
            preparedStatement.setTimestamp(++index, Timestamp.from(bookInstance.getDueBack()));
            preparedStatement.setString(++index, bookInstance.getStatus().name());
            preparedStatement.setString(++index, bookInstance.getBook().getIsbn());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Override
    public void delete(Long id) {
        deleteByCriteria(jpaConfig, "book_instances", "id", id);
    }

    @Override
    public boolean existsById(Long id) {
        return super.existsById(jpaConfig, "book_instances", "id", id);
    }

    @Override
    public BookInstance findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_BOOK_INSTANCES_QUERY + "where id =" + id)) {
            if (resultSet.next()) {
                return convertResultSetToBookInstance(resultSet);
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        return null;
    }

    @Override
    public DataTableResponse<BookInstance> findAll(DataTableRequest request) {
        List<BookInstance> bookInstances = new ArrayList<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_BOOK_INSTANCES_QUERY +
                "order by bi." +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                BookInstance authorResultSet = convertResultSetToBookInstance(resultSet);
                bookInstances.add(authorResultSet);
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<BookInstance> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(bookInstances);
        return dataTableResponse;
    }

    @Override
    public int count() {
        return count(jpaConfig, "book_instances");
    }

    @Override
    public int foreignCount(String isbn) {
        String filterOption = " where book_id = '" + isbn + "'";
        return count(jpaConfig, "book_instances", filterOption);
    }

    @Override
    public DataTableResponse<BookInstance> findAllByForeignId(DataTableRequest request, String isbn) {
        List<BookInstance> bookInstances = new ArrayList<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = FIND_ALL_BOOK_INSTANCES_QUERY +
                "where b.isbn = '" + isbn + "'" +
                "order by bi." +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                BookInstance authorResultSet = convertResultSetToBookInstance(resultSet);
                bookInstances.add(authorResultSet);
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<BookInstance> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(bookInstances);
        return dataTableResponse;
    }

    private BookInstance convertResultSetToBookInstance(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("bi.id");
        String imprint = resultSet.getString("bi.imprint");
        Long publishingDate = resultSet.getLong("bi.publishing_date");
        String countryCodeName = resultSet.getString("bi.country_code");
        Instant dueBack = resultSet.getTimestamp("bi.due_back").toInstant();
        String status = resultSet.getString("bi.status");
        String bookIsbn = resultSet.getString("bi.book_id");
        Instant instanceCreated = resultSet.getTimestamp("bi.created").toInstant();
        Instant instanceUpdated = resultSet.getTimestamp("bi.updated").toInstant();
        Boolean instanceVisible = resultSet.getBoolean("bi.visible");
        CountryCode countryCode = CountryCode.getByCode(countryCodeName);
        StatusType statusType = StatusType.valueOf(status);

        Instant bookCreated = resultSet.getTimestamp("b.created").toInstant();
        Instant bookUpdated = resultSet.getTimestamp("b.updated").toInstant();
        Boolean bookVisible = resultSet.getBoolean("b.visible");
        String imgUrl = resultSet.getString("b.image_url");
        String title = resultSet.getString("b.title");
        Long publicationDate = resultSet.getLong("b.publication_date");
        Integer pagesNumber = resultSet.getInt("b.pages_number");
        String summary = resultSet.getString("b.summary");

        Book book = new Book();
        book.setCreated(bookCreated);
        book.setUpdated(bookUpdated);
        book.setVisible(bookVisible);
        book.setIsbn(bookIsbn);
        book.setTitle(title);
        book.setImageUrl(imgUrl);
        book.setSummary(summary);
        book.setPagesNumber(pagesNumber);
        book.setPublicationDate(new Date(publicationDate));

        BookInstance bookInstance = new BookInstance();
        bookInstance.setId(id);
        bookInstance.setCreated(instanceCreated);
        bookInstance.setUpdated(instanceUpdated);
        bookInstance.setVisible(instanceVisible);
        bookInstance.setBook(book);
        bookInstance.setPublishingDate(new Date(publishingDate));
        bookInstance.setCountryCode(countryCode);
        bookInstance.setImprint(imprint);
        bookInstance.setStatus(statusType);
        bookInstance.setDueBack(dueBack);

        return bookInstance;
    }
}
