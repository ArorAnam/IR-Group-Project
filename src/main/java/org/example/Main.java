package org.example;

import org.apache.lucene.document.Document;

import org.example.indexer.LatimesIndexer;
import org.example.indexer.FbisIndexer;
import org.example.indexer.Fr94Indexer;
import org.example.model.FbisModel;
import org.example.model.TopicModel;
import org.example.parser.FbisParser;
import org.example.parser.Fr94Parser;
import org.example.parser.LatimesParser;
import org.example.parser.TopicParser;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        LatimesIndexer latimes = new LatimesIndexer();
        System.out.println(latimes.getDocuments().get(0));
    }
}