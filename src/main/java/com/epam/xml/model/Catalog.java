package com.epam.xml.model;

import java.util.List;

public class Catalog {
    private String title;
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
        builder.append("Catalog \ntitle=").append(title).append(", \nbooksList=")
                .append(booksList);
        return builder.toString();
    }
}
