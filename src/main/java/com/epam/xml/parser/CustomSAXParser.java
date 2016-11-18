package com.epam.xml.parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.xml.model.Book;
import com.epam.xml.model.Catalog;
import com.epam.xml.model.Genre;

public class CustomSAXParser extends DefaultHandler implements Parser {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<Book> booksList;
    private Map<String, Boolean> elementMap = new HashMap<String, Boolean>();
    private Catalog catalog = new Catalog();
    private Book book;
    private boolean catalogTitleSet = false;
    private StringBuilder descBuilder;

    @Override
    public void processDocument(File file)
            throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(file, this);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        switch (qName) {
        case "book":
            book = new Book();
            book.setId(attributes.getValue("id"));
            break;
        case "booksList":
            booksList = new ArrayList<>();
            break;
        case "description":
            descBuilder = new StringBuilder();
            elementMap.put(qName, true);
            break;
        default:
            elementMap.put(qName, true);
            break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        switch (qName) {
        case "book":
            booksList.add(book);
            book = null;
            break;
        case "booksList":
            catalog.setBooksList(booksList);
            booksList = null;
            break;
        case "description":
            book.setDescription(new String(descBuilder));
            descBuilder = null;
            elementMap.put(qName, false);
            break;
        default:
            elementMap.put(qName, false);
            break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String textValue = new String(
                Arrays.copyOfRange(ch, start, start + length));
        if (!textValue.matches("\\s*")) {
            if (elementMap.get("title") != null && elementMap.get("title")) {
                if (!catalogTitleSet) {
                    catalog.setTitle(textValue);
                    catalogTitleSet = true;
                } else {
                    book.setTitle(textValue);
                }
            }
            if (elementMap.get("author") != null && elementMap.get("author")) {
                book.setAuthor(textValue);
            }
            if (elementMap.get("publish_date") != null
                    && elementMap.get("publish_date")) {
                try {
                    book.setPublishDate(dateFormat.parse(textValue));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (elementMap.get("description") != null
                    && elementMap.get("description")) {
                descBuilder.append(textValue);
            }
            if (elementMap.get("price") != null && elementMap.get("price")) {
                book.setPrice(Double.parseDouble(textValue));
            }
            if (elementMap.get("genre") != null && elementMap.get("genre")) {
                book.setGenre(Genre.getGenre(textValue));
            }
        }
    }

    @Override
    public Catalog getCatalog() {
        return catalog;
    }

}
