package com.epam.xml;

import java.io.File;

import com.epam.xml.parser.Parser;
import com.epam.xml.parser.ParserFactory;

public class Launcher {
    public static void main(String[] args) throws Exception {
        String filePath = "XML_TASK.xml";
        Parser parser = ParserFactory.getParser("stax");

        parser.processDocument(new File(filePath));
        System.out.println(
                "============StAX=============\n" + parser.getCatalog());

        parser = ParserFactory.getParser("dom");

        parser.processDocument(new File(filePath));
        System.out.println(
                "============DOM=============\n" + parser.getCatalog());

        parser = ParserFactory.getParser("sax");

        parser.processDocument(new File(filePath));
        System.out.println(
                "============SAX=============\n" + parser.getCatalog());

        parser = ParserFactory.getParser("jaxb");

        parser.processDocument(new File(filePath));
        System.out.println(
                "============JAXB=============\n" + parser.getCatalog());
    }
}
