package com.epam.xml;

import java.io.File;
import java.util.List;

import com.epam.xml.manager.XPathManager;
import com.epam.xml.manager.XQueryManager;
import com.epam.xml.model.Book;
import com.epam.xml.parser.Parser;
import com.epam.xml.parser.ParserFactory;

public class Launcher {
    public static void main(String[] args) throws Exception {
        File file = new File("XML_TASK.xml");

        Parser parser = ParserFactory.getParser("stax");
        parser.processDocument(file);
        System.out.println(
                "============StAX=============\n" + parser.getCatalog());

        parser = ParserFactory.getParser("dom");
        parser.processDocument(file);
        System.out.println(
                "============DOM=============\n" + parser.getCatalog());

        parser = ParserFactory.getParser("sax");
        parser.processDocument(file);
        System.out.println(
                "============SAX=============\n" + parser.getCatalog());

        parser = ParserFactory.getParser("jaxb");
        parser.processDocument(file);
        System.out.println(
                "============JAXB=============\n" + parser.getCatalog());

        XPathManager xPathManager = new XPathManager();
        List<Book> books = xPathManager.findBooksByLastName(file, "O'Brien");
        System.out.println("=========XPath============\n" + books);

        XQueryManager xQueryManager = new XQueryManager();
        System.out.println("=========XQuery============\n"
                + xQueryManager.findBooksByPrice(new File("XML_TASK.xquery")));
    }
}
