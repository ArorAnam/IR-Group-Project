package org.example.parser;

import java.io.File;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsoup.nodes.Element;

import org.example.model.Fr94Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Fr94Parser {
    private final static File FR94_DIR = new File("dataset/fr94");

    private static ArrayList<Fr94Model> fr94DataList = new ArrayList<>();

    private static void parseFile(String parseDoc) throws IOException {
        Fr94Model fr94Model = new Fr94Model();
        File file = new File(parseDoc);
        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        for (Element e : doc.select("DOC")) {
            fr94Model = new Fr94Model();
            fr94Model.setDocno(e.select("DOCNO").text());
            fr94Model.setText(e.select("TEXT").text());
            fr94Model.setDoctitle(e.select("DOCTITLE").text());
            fr94DataList.add(fr94Model);
        }
    }

    public static void parseAllFiles(String path) throws IOException {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list != null) {
            for (File file : list) {
                if (file.isDirectory()) {
                    parseAllFiles(file.getAbsolutePath());
                } else {
                    if (!file.getName().contains("read")
                            && !file.getName().contains("Zone.Identifier")) {
                        parseFile(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    public ArrayList<Fr94Model> getData() throws IOException {
        parseAllFiles(FR94_DIR.getAbsolutePath());
        return fr94DataList;
    }
}
