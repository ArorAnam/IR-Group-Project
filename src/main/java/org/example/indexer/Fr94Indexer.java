package org.example.indexer;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.example.parser.Fr94Parser;
import org.example.model.Fr94Model;

public class Fr94Indexer {
    private static final ArrayList<Document> fr94Documents = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        Fr94Parser data = new Fr94Parser();
        ArrayList<Fr94Model> parsedData;
        try {
            parsedData = data.getData();
            for (Fr94Model parsedDatum : parsedData) {
                fr94Documents.add(createDocument(parsedDatum));
            }
        } catch (Exception ignored) {
        }
        return fr94Documents;
    }

    private Document createDocument(Fr94Model fr94Data) {
        Document doc = new Document();
        doc.add(new StringField("docno", fr94Data.getDocno(), Field.Store.YES));
        doc.add(new TextField("text", fr94Data.getText(), Field.Store.YES));
        doc.add(new TextField("headline", fr94Data.getDoctitle(), Field.Store.YES));
        return doc;
    }
}