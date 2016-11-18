package com.epam.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
        name = "catalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog {
    private String title;
    @XmlElement(
            name = "book")
    @XmlElementWrapper(
            name = "booksList")
    private List<Book> booksList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Catalog \ntitle=").append(title)
                .append(", \nbooksList=").append(booksList);
        return builder.toString();
    }
}
