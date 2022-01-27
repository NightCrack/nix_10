package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Book;

import java.sql.Date;

public class BookResponseDto extends ResponseDto {

    private String isbn;
    private String imageUrl;
    private String title;
    private Date publicationDate;
    private Integer pagesNumber;
    private String summary;
    private Integer bookInstancesCount;
    private Integer authorsCount;
    private Integer genresCount;

    public BookResponseDto(Book book) {
        isbn = book.getIsbn();
        imageUrl = book.getImageUrl();
        title = book.getTitle();
        publicationDate = book.getPublicationDate();
        pagesNumber = book.getPagesNumber();
        summary = book.getSummary();
        this.setCreated(book.getCreated());
        this.setUpdated(book.getUpdated());
        this.setVisible(book.getVisible());
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
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

    public Integer getBookInstancesCount() {
        return bookInstancesCount;
    }

    public void setBookInstancesCount(Integer bookInstancesCount) {
        this.bookInstancesCount = bookInstancesCount;
    }

    public Integer getAuthorsCount() {
        return authorsCount;
    }

    public void setAuthorsCount(Integer authorsCount) {
        this.authorsCount = authorsCount;
    }

    public Integer getGenresCount() {
        return genresCount;
    }

    public void setGenresCount(Integer genresCount) {
        this.genresCount = genresCount;
    }
}