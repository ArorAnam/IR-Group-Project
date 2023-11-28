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
        doc.add(new TextField("date", latimesData.getDate(), Field.Store.YES));

        // Important and Unique
        doc.add(new TextField("author", latimesData.getByline(), Field.Store.YES));
        doc.add(new TextField("section", latimesData.getSection(), Field.Store.YES));
        doc.add(new TextField("graphicDescription", latimesData.getGraphic(), Field.Store.YES));
        doc.add(new TextField("type", latimesData.getType(), Field.Store.YES));
        doc.add(new TextField("correction", latimesData.getCorrection(), Field.Store.YES));
        doc.add(new TextField("correctionDate", latimesData.getCorrectionDate(), Field.Store.YES));

        // Not Important
        doc.add(new TextField("docId", latimesData.getDocid(), Field.Store.YES));
        doc.add(new TextField("wordLength", latimesData.getLength(), Field.Store.YES));

        String allContent = String.join(" ", latimesData.getText(), latimesData.getDate(),
                latimesData.getByline(), latimesData.getSection(), latimesData.getGraphic(), latimesData.getType(),
                latimesData.getCorrection(), latimesData.getCorrectionDate());
        doc.add(new TextField("allContent", allContent, Field.Store.YES));
        return doc;
    }
}
