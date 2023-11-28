package org.example.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.example.model.LatimesModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LatimesParser {
    private final static File LATIMES_DIR = new File("dataset/latimes");

    private static ArrayList<LatimesModel> latimesDataList = new ArrayList<>();

    private static void parseFile(String parseDoc) throws IOException {
        LatimesModel latimesModel = null;
        File file = new File(parseDoc);
        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        for (Element e : doc.select("DOC")) {
            latimesModel = new LatimesModel();
            latimesModel.setDocno(e.select("DOCNO").text());
            latimesModel.setDocid(e.select("DOCID").text());
            latimesModel.setDate(e.select("DATE").text());
            latimesModel.setSection(e.select("SECTION").text());
            latimesModel.setLength(e.select("LENGTH").text());
            latimesModel.setHeadline(e.select("HEADLINE").text());
            latimesModel.setByline(e.select("BYLINE").text());
            latimesModel.setText(e.select("TEXT").text());
            latimesModel.setGraphic(e.select("GRAPHIC").text());
            latimesModel.setType(e.select("TYPE").text());
            latimesModel.setCorrectionDate(e.select("CORRECTION-DATE").text());
            latimesModel.setCorrection(e.select("CORRECTION").text());
            latimesDataList.add(latimesModel);
        }
    }

    public static void parseAllFiles() throws IOException {
        File[] allFiles = LATIMES_DIR.listFiles();
        for (File file : allFiles) {
            if (!file.getName().contains("read")) {
                parseFile(file.getAbsolutePath());
            }
        }
    }

    public ArrayList<LatimesModel> getData() throws IOException {
        parseAllFiles();
        return latimesDataList;
    }
}
