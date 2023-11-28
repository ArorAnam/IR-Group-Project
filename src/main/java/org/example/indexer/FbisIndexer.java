package org.example.indexer;

import java.util.ArrayList;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import org.example.model.FbisModel;
import org.example.parser.FbisParser;

public class FbisIndexer {
    private static ArrayList<Document> fbisDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        FbisParser data = new FbisParser();
        ArrayList<FbisModel> parsedData;
        try {
            parsedData = data.getData();
            for (int i = 0; i < parsedData.size(); i++) {
                fbisDocuments.add(createDocument(parsedData.get(i)));
            }
        } catch (Exception e) {
        }

        return fbisDocuments;
    }

    private Document createDocument(FbisModel fbisData) {

        Document doc = new Document();
        // Important
        doc.add(new StringField("id", fbisData.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", fbisData.getHeader(), Field.Store.YES));
        doc.add(new TextField("content", fbisData.getText(), Field.Store.YES));
        doc.add(new TextField("contentExtended", fbisData.getTxt5(), Field.Store.YES));
        doc.add(new TextField("date", fbisData.getDate1(), Field.Store.YES));
        // Important and Unique
        doc.add(new TextField("f", fbisData.getF(), Field.Store.YES)); // Seems to be subject?
        doc.add(new TextField("header", fbisData.getHeader(), Field.Store.YES));

        doc.add(new TextField("h1", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h2", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h3", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h4", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h5", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h6", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h7", fbisData.getH1(), Field.Store.YES));
        doc.add(new TextField("h8", fbisData.getH1(), Field.Store.YES));

        // Not Important
        doc.add(new TextField("fig", fbisData.getFig(), Field.Store.YES));

        String allContent = String.join(" ", fbisData.getText(), fbisData.getTxt5(), fbisData.getF(),
                fbisData.getHeader(), fbisData.getH1(), fbisData.getH2(), fbisData.getH3(), fbisData.getH4(),
                fbisData.getH5(), fbisData.getH6(), fbisData.getH7(), fbisData.getH8(), fbisData.getFig());
        doc.add(new TextField("allContent", allContent, Field.Store.YES));
        return doc;
    }
}