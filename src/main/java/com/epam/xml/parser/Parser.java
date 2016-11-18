package com.epam.xml.parser;

import java.io.File;

import com.epam.xml.model.Catalog;

public interface Parser {
    void processDocument(File file) throws Exception;

    Catalog getCatalog();
}

