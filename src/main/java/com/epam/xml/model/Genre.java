package com.epam.xml.model;

public enum Genre {
    COMPUTER("Computer"), SCIENCE_FICTION("Science Fiction"), HORROR(
            "Horror"), ROMANCE("Romance"), FANTASY("Fantasy");

    private String title;

    private Genre(String title) {
        this.title = title;
    }

    public static Genre getGenre(String textValue) {
        for (Genre genre : Genre.values()) {
            if (genre.getTitle().equalsIgnoreCase(textValue)) {
                return genre;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }
}
