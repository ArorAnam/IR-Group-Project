package org.example.indexer;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.example.parser.FtParser;
import org.example.model.FtModel;
public class FtIndexer {
    private static final ArrayList<Document> ftDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        FtParser data = new FtParser();
        ArrayList<FtModel> parsedData;
        try {
            parsedData = data.getData();
            for (FtModel parsedDatum : parsedData) {
                ftDocuments.add(createDocument(parsedDatum));
            }
        } catch (Exception ignored) {
        }

        return ftDocuments;
    }

    private Document createDocument(FtModel ftData) {
        Document doc = new Document();
        doc.add(new StringField("docno", ftData.getDocno(), Field.Store.YES));
        doc.add(new StringField("profile", ftData.getProfile(), Field.Store.YES));
        doc.add(new StringField("date", ftData.getDate(), Field.Store.YES));
        doc.add(new TextField("headline", ftData.getHeadline(), Field.Store.YES));
        doc.add(new TextField("pub", ftData.getPub(), Field.Store.YES));
        doc.add(new TextField("page", ftData.getPage(), Field.Store.YES));
        doc.add(new TextField("byline", ftData.getByline(), Field.Store.YES));
        doc.add(new TextField("dateline", ftData.getDateline(), Field.Store.YES));
        doc.add(new TextField("text", ftData.getText(), Field.Store.YES));
        return doc;
    }
}
