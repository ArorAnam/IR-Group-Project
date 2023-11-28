package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;


import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.example.indexer.*;
import org.example.parser.TopicParser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Analyzer analyzer = new EnglishAnalyzer();
        Similarity similarity = new BM25Similarity();
        Indexer indexer = new Indexer();
        indexer.Indexing(analyzer, similarity);

        SearchEngine se = new SearchEngine(analyzer, similarity);
        se.search();
    }
}