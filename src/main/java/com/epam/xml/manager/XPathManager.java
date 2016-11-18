package com.epam.xml.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.xml.model.Book;
import com.epam.xml.parser.DOMParser;

public class XPathManager {
    public List<Book> findBooksByLastName(File file, String lastName) {

        try {
            return parseBooks("/catalog/booksList/book[starts-with(author,\""
                    + lastName + "\")]", getDocument(file));
        } catch (XPathExpressionException | SAXException | IOException
                | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Book> parseBooks(String expression, Document document)
            throws XPathExpressionException {
        List<Book> books = new ArrayList<>();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(expression);

        NodeList nodeList = (NodeList) expr.evaluate(document,
                XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            books.add(DOMParser.fillBook(node));
        }
        return books;
    }

    private Document getDocument(File file)
            throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }
}
