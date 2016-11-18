package com.epam.xml.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.epam.xml.model.Book;
import com.epam.xml.model.Catalog;
import com.epam.xml.model.Genre;

public class StAXParser implements Parser {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<Book> booksList;
    private Map<String, Boolean> elementMap = new HashMap<String, Boolean>();
    private Catalog catalog = new Catalog();
    private Book book;
    private boolean catalogTitleSet = false;
    private StringBuilder descBuilder;

    @Override
    public void processDocument(File file)
            throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory
                .createXMLEventReader(new FileReader(file));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            switch (event.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                elementStart(event.asStartElement());
                break;
            case XMLStreamConstants.CHARACTERS:
                characters(event.asCharacters().getData());
                break;
            case XMLStreamConstants.END_ELEMENT:
                elementEnd(event.asEndElement());
                break;
            }
        }
    }

    private void elementStart(StartElement element) {
        String name = element.getName().getLocalPart();

        switch (name) {
        case "book":
            book = new Book();
            book.setId(element.getAttributeByName(new QName("id")).getValue());
            break;
        case "booksList":
            booksList = new ArrayList<>();
            break;
        case "description":
            descBuilder = new StringBuilder();
            elementMap.put(name, true);
            break;
        default:
            elementMap.put(name, true);
            break;
        }
    }

    private void elementEnd(EndElement element) {
        String name = element.getName().getLocalPart();
        switch (name) {
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
            elementMap.put(name, false);
            break;
        default:
            elementMap.put(name, false);
            break;
        }
    }

    private void characters(String text) {
        if (!text.matches("\\s*")) {
            if (elementMap.get("title") != null && elementMap.get("title")) {
                if (!catalogTitleSet) {
                    catalog.setTitle(text);
                    catalogTitleSet = true;
                } else {
                    book.setTitle(text);
                }
            }
            if (elementMap.get("author") != null && elementMap.get("author")) {
                book.setAuthor(text);
            }
            if (elementMap.get("publish_date") != null
                    && elementMap.get("publish_date")) {
                try {
                    book.setPublishDate(dateFormat.parse(text));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (elementMap.get("description") != null
                    && elementMap.get("description")) {
                descBuilder.append(text);
            }
            if (elementMap.get("price") != null && elementMap.get("price")) {
                book.setPrice(Double.parseDouble(text));
            }
            if (elementMap.get("genre") != null && elementMap.get("genre")) {
                book.setGenre(Genre.getGenre(text));
            }
        }
    }

    @Override
    public Catalog getCatalog() {
        return catalog;
    }
}
