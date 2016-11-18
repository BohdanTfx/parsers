package com.epam.xml.parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.xml.model.Book;
import com.epam.xml.model.Catalog;
import com.epam.xml.model.Genre;

public class DOMParser implements Parser {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<Book> booksList;
    private Catalog catalog = new Catalog();

    @Override
    public void processDocument(File file)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();

        NodeList booksList = document.getElementsByTagName("book");
        this.booksList = new ArrayList<>();
        for (int i = 0; i < booksList.getLength(); i++) {
            fillBook((Element) booksList.item(i));
        }

        setCatalogTitle(document);
        catalog.setBooksList(this.booksList);
    }

    private void setCatalogTitle(Document document) {
        NodeList titles = document.getElementsByTagName("title");
        Node title = titles.item(0);
        if (title.getParentNode().getNodeName().equals("catalog")) {
            catalog.setTitle(title.getTextContent());
        }
        
    }

    private void fillBook(Element element) {
        Book book = new Book();
        book.setId(element.getAttribute("id"));
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            String nodeName = child.getNodeName();
            switch (nodeName) {
            case "title":
                book.setTitle(child.getTextContent());
                break;
            case "author":
                book.setAuthor(child.getTextContent());
                break;
            case "publish_date":
                try {
                    book.setPublishDate(
                            dateFormat.parse(child.getTextContent()));
                } catch (DOMException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "description":
                book.setDescription(child.getTextContent());
                break;
            case "price":
                book.setPrice(Double.parseDouble(child.getTextContent()));
                break;
            case "genre":
                book.setGenre(Genre.getGenre(child.getTextContent()));
                break;
            default:
                break;
            }
        }

        booksList.add(book);
    }
    @Override
    public Catalog getCatalog() {
        return catalog;
    }

}
