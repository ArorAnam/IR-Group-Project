package org.example;

import org.apache.lucene.document.Document;

import org.example.indexer.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        FtIndexer ft = new FtIndexer();
        System.out.println(ft.getDocuments().get(0));
    }
}