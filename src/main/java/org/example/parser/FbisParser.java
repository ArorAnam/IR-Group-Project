package org.example.parser;

import org.example.model.FbisModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FbisParser {
    private final static File FBIS_DIR = new File("dataset/fbis");

    private static ArrayList<FbisModel> fbisDataList = new ArrayList<>();

    private static void parseFile(String parseDoc) throws IOException {
        FbisModel fbisModel = null;
        File file = new File(parseDoc);
        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        for (Element e : doc.select("DOC")) {
            fbisModel = new FbisModel();
            for (int i = 1; i <= 8; i ++ ) {
                String hString = e.select("H" + i).text();
                if (!hString.isEmpty()) {
                    fbisModel.setH(hString, i-1);
                }
            }
            fbisModel.setDocno(e.select("DOCNO").text());
            fbisModel.setDate1(e.select("DATE1").text());
            fbisModel.setHeader(e.select("HEADER").text());
            fbisModel.setFig(e.select("FIG").text());
            fbisModel.setF(e.select("F").text());
            fbisModel.setTxt5(e.select("TXT5").text());
            fbisModel.setText(e.select("TXT").text());
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