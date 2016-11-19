package com.epam.xml.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.sf.saxon.xqj.SaxonXQDataSource;

public class XQueryManager {
    public String findBooksByPrice(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            XQDataSource ds = new SaxonXQDataSource();
            XQConnection conn = ds.getConnection();
            XQPreparedExpression exp = conn.prepareExpression(inputStream);
            XQResultSequence result = exp.executeQuery();

            StringBuilder builder = new StringBuilder();
            while (result.next()) {
                builder.append(result.getItemAsString(null));
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
