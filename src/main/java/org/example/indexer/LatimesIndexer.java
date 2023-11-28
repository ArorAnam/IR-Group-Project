package org.example.indexer;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.example.parser.LatimesParser;
import org.example.model.LatimesModel;

public class LatimesIndexer {
    private static ArrayList<Document> latimesDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        LatimesParser data = new LatimesParser();
        ArrayList<LatimesModel> parsedData;
        try {
            parsedData = data.getData();
            for (int i = 0; i < parsedData.size(); i++)
                latimesDocuments.add(createDocument(parsedData.get(i)));
        } catch (Exception e) {
        }

        return latimesDocuments;
    }

    private Document createDocument(LatimesModel latimesData) {
        Document doc = new Document();
        // Important
        doc.add(new StringField("id", latimesData.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", latimesData.getHeadline(), Field.Store.YES));
        doc.add(new TextField("content", latimesData.getText(), Field.Store.YES));
        return doc;
    }
}
