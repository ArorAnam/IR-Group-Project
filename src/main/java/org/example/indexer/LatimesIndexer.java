package org.example.indexer;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.example.parser.LatimesParser;
import org.example.model.LatimesModel;

public class LatimesIndexer {
    private static final ArrayList<Document> latimesDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        LatimesParser data = new LatimesParser();
        ArrayList<LatimesModel> parsedData;
        try {
            parsedData = data.getData();
            for (LatimesModel parsedDatum : parsedData) {
                latimesDocuments.add(createDocument(parsedDatum));
            }
        } catch (Exception ignored) {
        }

        return latimesDocuments;
    }

    private Document createDocument(LatimesModel latimesData) {
        Document doc = new Document();
        doc.add(new StringField("docno", latimesData.getDocno(), Field.Store.YES));
        doc.add(new TextField("headline", latimesData.getHeadline(), Field.Store.YES));
        doc.add(new TextField("text", latimesData.getText(), Field.Store.YES));
        return doc;
    }
}
