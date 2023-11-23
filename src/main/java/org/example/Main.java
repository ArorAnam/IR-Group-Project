package org.example;

import org.apache.lucene.document.Document;
import org.example.indexer.Fr94Indexer;
import org.example.model.TopicModel;
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
        //Fr94Parser temp = new Fr94Parser();
        //System.out.println(temp.getData());

        // Get Fr94 documents
        //Fr94Indexer temp = new Fr94Indexer();
        //ArrayList<Document> docs = temp.getDocuments();
        //System.out.println(docs.get(1));
    }
}