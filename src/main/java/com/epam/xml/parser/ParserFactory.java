package com.epam.xml.parser;

public final class ParserFactory {
    public static Parser getParser(String name) {
        Parser parser = null;
        switch (name.toLowerCase()) {
        case "dom":
            parser = new DOMParser();
            break;
        case "sax":
            parser = new CustomSAXParser();
            break;
        case "stax":
            parser = new StAXParser();
            break;
        default:
            break;
        }

        return parser;
    }
}
