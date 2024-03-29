package org.example.indexer;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.example.model.FbisModel;
import org.example.parser.FbisParser;

public class FbisIndexer {
    private static final ArrayList<Document> fbisDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        FbisParser data = new FbisParser();
        ArrayList<FbisModel> parsedData;
        try {
            parsedData = data.getData();
            for (FbisModel parsedDatum : parsedData) {
                fbisDocuments.add(createDocument(parsedDatum));
            }
        } catch (Exception ignored) {
        }

        return fbisDocuments;
    }

    private Document createDocument(FbisModel fbisData) {
        Document doc = new Document();
        doc.add(new StringField("docno", fbisData.getDocno(), Field.Store.YES));
        doc.add(new StringField("ht", fbisData.getHt(), Field.Store.YES));
        doc.add(new StringField("h2", fbisData.getH2(), Field.Store.YES));
        doc.add(new StringField("date", fbisData.getDate1(), Field.Store.YES));
        doc.add(new StringField("h3", fbisData.getH3(), Field.Store.YES));
        doc.add(new TextField("headline", fbisData.getTi(), Field.Store.YES));
        doc.add(new TextField("text", fbisData.getText(), Field.Store.YES));
        return doc;
    }
}