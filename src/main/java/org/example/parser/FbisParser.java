package org.example.parser;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import org.example.model.FbisModel;

import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FbisParser {
    private final static File FBIS_DIR = new File("dataset/fbis");

    private static ArrayList<FbisModel> fbisDataList = new ArrayList<>();

    private static void parseFile(String parseDoc) throws IOException {
        FbisModel fbisModel = null;
        File file = new File(parseDoc);
        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        for (Element e : doc.select("DOC")) {
            fbisModel = new FbisModel();
            fbisModel.setDocno(e.select("DOCNO").text());
            fbisModel.setHt(e.select("HT").text());
            fbisModel.setH2(e.select("H2").text());
            fbisModel.setDate1(e.select("DATE1").text());
            fbisModel.setH3(e.select("H3").text());
            fbisModel.setTi(e.select("TI").text());
            fbisModel.setText(e.select("TEXT").text());
            fbisDataList.add(fbisModel);
        }
    }

    public static void parseAllFiles(String path) throws IOException {
        File[] allFiles = new File(path).listFiles();
        for (File file : allFiles) {
            if (!file.getName().contains("read")) {
                parseFile(file.getAbsolutePath());
            }
        }
    }

    public ArrayList<FbisModel> getData() throws IOException {
        parseAllFiles(FBIS_DIR.getAbsolutePath());
        return fbisDataList;
    }
}