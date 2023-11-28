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
        return doc;
    }
}
