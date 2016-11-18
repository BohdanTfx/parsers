package com.epam.xml.parser;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.epam.xml.model.Catalog;

public class JAXBParser implements Parser {
    private Catalog catalog;

    @Override
    public void processDocument(File file) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        catalog = (Catalog) jaxbUnmarshaller.unmarshal(file);
    }

    @Override
    public Catalog getCatalog() {
        return catalog;
    }

}
