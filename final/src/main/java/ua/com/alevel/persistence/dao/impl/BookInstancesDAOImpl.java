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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookInstancesDAOImpl extends BaseDaoImpl implements BookInstancesDAO {

    private final JpaConfig jpaConfig;

    public BookInstancesDAOImpl(JpaConfig jpaConfig) {
        super(jpaConfig);
        this.jpaConfig = jpaConfig;
    }

    private final String FIND_ALL_BOOK_INSTANCES_QUERY =
            "select id, bi.created, bi.updated, bi.visible, " +
                    "imprint, publishing_date, country_code, due_back, " +
                    "status, book_id, isbn, b.created, b.updated, b.visible, " +
                    "image_url, title, publication_date, pages_number, " +
                    "summary from book_instances as bi " +
                    "inner join books as b on bi.book_id = b.isbn ";

    @Override
    public void create(CustomResultSet<BookInstance> entity) {

    }

    @Override
    public void update(BookInstance entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
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
        return count("book_instances");
    }

    @Override
    public void deleteAllByForeignId(String s) {

    }

    @Override
    public List<BookInstance> findAllByForeignId(String isbn) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_BOOK_INSTANCES_QUERY + "where b.isbn = '" + isbn + "'")) {
            List<BookInstance> returnValue = new ArrayList<>();
            while (resultSet.next()) {
                returnValue.add(convertResultSetToBookInstance(resultSet));
            }
            return returnValue;
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
            return Collections.emptyList();
        }
    }

    private BookInstance convertResultSetToBookInstance(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("bi.id");
        String imprint = resultSet.getString("bi.imprint");
        String publishingDate = resultSet.getString("bi.publishing_date");
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
        bookInstance.setPublishingDate(publishingDate);
        bookInstance.setCountryCode(countryCode);
        bookInstance.setImprint(imprint);
        bookInstance.setStatus(statusType);
        bookInstance.setDueBack(dueBack);

        return bookInstance;
    }
}
