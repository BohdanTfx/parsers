package com.epam.xml.model;

import java.util.Date;

public class Book {
    private String id;
    private String author;
    private String title;
    private Genre genre;
    private Double price;
    private Date publishDate;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nBook: \n\tid=").append(id).append(", \n\tauthor=")
                .append(author).append(", \n\ttitle=").append(title)
                .append(", \n\tgenre=").append(genre.getTitle())
                .append(", \n\tprice=").append(price)
                .append(", \n\tpublishDate=").append(publishDate)
                .append(", \n\tdescription=").append(description);
        return builder.toString();
    }
}
