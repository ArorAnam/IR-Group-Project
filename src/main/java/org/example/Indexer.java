package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.example.indexer.FbisIndexer;
import org.example.indexer.Fr94Indexer;
import org.example.indexer.FtIndexer;
import org.example.indexer.LatimesIndexer;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Indexer {
    FbisIndexer fbisIndexer = new FbisIndexer();
    Fr94Indexer fr94Indexer = new Fr94Indexer();
    FtIndexer ftIndexer = new FtIndexer();
    LatimesIndexer latimesIndexer = new LatimesIndexer();

    public void Indexing(Analyzer analyzer, Similarity similarity) throws IOException {
        ArrayList<Document> fbisDocument = fbisIndexer.getDocuments();
        ArrayList<Document> fr94Document = fr94Indexer.getDocuments();
        ArrayList<Document> ftDocument = ftIndexer.getDocuments();
        ArrayList<Document> latimesDocument = latimesIndexer.getDocuments();

        Directory directory;
        directory = FSDirectory.open(Paths.get("index/"));
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        iwc.setSimilarity(similarity);

        IndexWriter indexWriter = new IndexWriter(directory, iwc);
        indexWriter.addDocuments(fbisDocument);
        System.out.println("Fbis is done");
        indexWriter.addDocuments(fr94Document);
        System.out.println("Fr94 is done");
        indexWriter.addDocuments(ftDocument);
        System.out.println("Ft is done");
        indexWriter.addDocuments(latimesDocument);
        System.out.println("Latimes is done");
        indexWriter.close();

    }
}
