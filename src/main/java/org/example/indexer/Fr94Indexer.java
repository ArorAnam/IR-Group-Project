package org.example.indexer;

import java.util.ArrayList;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import org.example.parser.Fr94Parser;
import org.example.model.Fr94Model;

public class Fr94Indexer {
    private static ArrayList<Document> fr94Documents = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        Fr94Parser data = new Fr94Parser();
        ArrayList<Fr94Model> parsedData;
        try {
            parsedData = data.getData();
            for (int i = 0; i < parsedData.size(); i++)
                fr94Documents.add(createDocument(parsedData.get(i)));
        } catch (Exception e) {
        }
        return fr94Documents;
    }

    private Document createDocument(Fr94Model fr94Data) {
        Document doc = new Document();
        // Important
        doc.add(new StringField("id", fr94Data.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", fr94Data.getDoctitle(), Field.Store.YES));
        doc.add(new TextField("content", fr94Data.getText(), Field.Store.YES));

        return doc;
    }
}