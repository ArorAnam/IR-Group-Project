package org.example;

import org.apache.lucene.document.Document;
import org.example.indexer.FbisIndexer;
import org.example.indexer.Fr94Indexer;
import org.example.model.FbisModel;
import org.example.model.TopicModel;
import org.example.parser.FbisParser;
import org.example.parser.Fr94Parser;
import org.example.parser.TopicParser;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        // Get Topics
        //TopicParser temp1 = new TopicParser();
        //System.out.println(temp1.loadTopics());

        // Get Fr94 Models
        //Fr94Parser frParser = new Fr94Parser();
        //System.out.println(frParser.getData());

        // Get Fr94 documents
        //Fr94Indexer fr9rdocs = new Fr94Indexer();
        //ArrayList<Document> docs = fr9rdocs.getDocuments();
        //System.out.println(docs.get(1));

        // Get fbis models
        //FbisParser temp2 = new FbisParser();
        //System.out.println(temp2.getData());

        //get fbis documents
        //FbisIndexer temp3 = new FbisIndexer();
        //ArrayList<Document> fbisdocs = temp3.getDocuments();
        //System.out.println(fbisdocs.get(1));
    }
}