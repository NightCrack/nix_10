package ua.com.alevel.view.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class BookRequestDto extends RequestDto {

    private String isbn;
    private MultipartFile bookImage;
    private String title;
    private Date publicationDate;
    private Integer pagesNumber;
    private String summary;
    private List<Long> authors;
    private List<Long> genres;

    public List<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }

    public List<Long> getGenres() {
        return genres;
    }

    public void setGenres(List<Long> genres) {
        this.genres = genres;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public MultipartFile getBookImage() {
        return bookImage;
    }

    public void setBookImage(MultipartFile bookImage) {
        this.bookImage = bookImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = toSqlDate(publicationDate);
    }

    public Integer getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(Integer pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
