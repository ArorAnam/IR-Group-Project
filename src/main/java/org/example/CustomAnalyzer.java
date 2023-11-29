package org.example;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.analysis.TokenStream;

public class CustomAnalyzer extends StopwordAnalyzerBase {

    private final Path currentRelativePath = Paths.get("").toAbsolutePath();
    private CharArraySet stopwords;
    private SynonymMap geoSynonyms = null;

    public CustomAnalyzer() throws IOException, ParseException
    {
        this.stopwords = getStopwords();

        this.geoSynonyms = getGeoSynonyms();
    }


    @Override
    protected TokenStreamComponents createComponents(String s) {
        StandardTokenizer tokenizer = new StandardTokenizer();
        TokenStream stream;
        stream = new LowerCaseFilter(tokenizer);
        stream = new TrimFilter(stream);
        stream = new EnglishPossessiveFilter(stream);
        stream = new StopFilter(stream, this.stopwords);
        if (this.geoSynonyms != null)
        {
            stream = new FlattenGraphFilter(new SynonymGraphFilter(stream, this.geoSynonyms, true));
        }
        stream = new PorterStemFilter(stream);

        return new TokenStreamComponents(tokenizer, stream);
    }

    private CharArraySet getStopwords() throws IOException
    {
        CharArraySet stopwords = new CharArraySet(Files.readAllLines(Paths.get("dataset/stopwords.txt")), true);
        return stopwords;
    }

    private SynonymMap getGeoSynonyms() throws IOException, ParseException
    {
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        List<String> entries = Files.readAllLines(Paths.get("dataset/countries.txt"));

        for (String entry : entries)
        {
            String[] split = entry.split(":");
            String[] keys = split[0].split(",");
            String[] values = split[1].split(",");

            for (String key : keys)
            {
                for (String value : values)
                {
                    builder.add(new CharsRef(key), new CharsRef(value), true);
                }
            }
        }

        return builder.build();
    }

    private SynonymMap createSynonymMap() {
        SynonymMap synMap = new SynonymMap(null, null, 0);
        try {
            BufferedReader countries = new BufferedReader(new FileReader(currentRelativePath + "/dataset/countries.txt"));

            final SynonymMap.Builder builder = new SynonymMap.Builder(true);
            String country = countries.readLine();

            while(country != null) {
                builder.add(new CharsRef("country"), new CharsRef(country), true);
                builder.add(new CharsRef("countries"), new CharsRef(country), true);
                country = countries.readLine();
            }

            synMap = builder.build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage() + "occurred when trying to create synonym map");
        }
        return synMap;
    }

    private List<String> createStopWordList()
    {
        ArrayList<String> stopWordList = new ArrayList<>();
        try {
            BufferedReader stopwords = new BufferedReader(new FileReader(currentRelativePath + "/dataset/stopwords.txt"));
            String word = stopwords.readLine();
            while(word != null) {
                stopWordList.add(word);
                word = stopwords.readLine();
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage() + "occurred when trying to create stopword list");
        }
        return stopWordList;
    }
}