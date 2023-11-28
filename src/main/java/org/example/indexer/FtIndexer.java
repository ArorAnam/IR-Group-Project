package org.example.indexer;
import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.example.parser.FtParser;
import org.example.model.FtModel;
public class FtIndexer {
    private static ArrayList<Document> ftDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        FtParser data = new FtParser();
        ArrayList<FtModel> parsedData;
        try {
            parsedData = data.getData();
            for (int i = 0; i < parsedData.size(); i++)
                ftDocuments.add(createDocument(parsedData.get(i)));
        } catch (Exception e) {
        }

        return ftDocuments;
    }

    private Document createDocument(FtModel ftData) {
        Document doc = new Document();
        // Important
        doc.add(new StringField("id", ftData.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", ftData.getHeadline(), Field.Store.YES));
        doc.add(new TextField("content", ftData.getText(), Field.Store.YES));
        doc.add(new TextField("date", ftData.getDate(), Field.Store.YES));

        // Important and Unique
        doc.add(new TextField("author", ftData.getByline(), Field.Store.YES));
        doc.add(new TextField("pub", ftData.getPub(), Field.Store.YES));

        // Not Important
        doc.add(new TextField("profile", ftData.getProfile(), Field.Store.YES));
        doc.add(new TextField("page", ftData.getPage(), Field.Store.YES));
        doc.add(new TextField("dateline", ftData.getDateline(), Field.Store.YES));

        String allContent = String.join(" ", ftData.getText(), ftData.getDate(), ftData.getByline(),
                ftData.getPub(), ftData.getProfile(), ftData.getPage(), ftData.getDateline());
        doc.add(new TextField("allContent", allContent, Field.Store.YES));
        return doc;
    }
}
