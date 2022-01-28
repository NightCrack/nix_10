package ua.com.alevel.persistence.entity;


import ua.com.alevel.view.dto.request.BookRequestDto;

import java.sql.Date;

public final class Book extends BaseEntity {

    private String isbn;
    private String imageUrl;
    private String title;
    private Date publicationDate;
    private Integer pagesNumber;
    private String summary;

    public Book() {
    }

    public Book(BookRequestDto bookRequestDto) {
        isbn = bookRequestDto.getIsbn();
//        imageUrl = bookRequestDto.getBookImage().getOriginalFilename();
        imageUrl = "";
        title = bookRequestDto.getTitle();
        publicationDate = bookRequestDto.getPublicationDate();
        pagesNumber = bookRequestDto.getPagesNumber();
        summary = bookRequestDto.getSummary();
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
}
