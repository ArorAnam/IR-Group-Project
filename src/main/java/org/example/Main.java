package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.similarities.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, java.text.ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===CS7IS3 Group Assignment-2 Group-16===");
        System.out.println("Choose Analyzer");
        System.out.println("1 - Custom");
        System.out.println("2 - Standard");
        System.out.println("3 - English");
        System.out.println("4 - Simple");
        System.out.println("5 - Whitespace");
        System.out.println("Default - Standard");

        String analyzer_input = scanner.nextLine();
        Analyzer analyzer;
        String analyzerName;
        switch (analyzer_input) {
            case "1":
                analyzer = new CustomAnalyzer();
                analyzerName = "Custom";
                break;
            case "3":
                analyzer = new EnglishAnalyzer();
                analyzerName = "English";
                break;
            case "4":
                analyzer = new SimpleAnalyzer();
                analyzerName = "Simple";
                break;
            case "5":
                analyzer = new WhitespaceAnalyzer();
                analyzerName = "Whitespace";
                break;
            default:
                analyzer = new StandardAnalyzer();
                analyzerName = "Standard";
                break;
        }

        System.out.println("Choose Similarity");
        System.out.println("1 - BM25");
        System.out.println("2 - Boolean");
        System.out.println("3 - Classic");
        System.out.println("4 - Multi-Similarity");
        System.out.println("Default - Classic");

        Similarity similarity;
        String similarityName;
        String similarity_input = scanner.nextLine();
        switch (similarity_input) {
            case "1":
                similarity = new BM25Similarity();
                similarityName = "BM25";
                break;
            case "2":
                similarity = new BooleanSimilarity();
                similarityName = "Boolean";
                break;
            case "4":
                similarity = new MultiSimilarity(new MultiSimilarity[]{getSimilarity()});
                similarityName = "Multi";
                break;
            default:
                similarity = new ClassicSimilarity();
                similarityName = "Classic";
                break;
        }

        Searcher.executeQueries(analyzer, similarity, analyzerName, similarityName);

    }

    private static MultiSimilarity getSimilarity() {
        Similarity[] similarities = {
                new ClassicSimilarity(),
                new BM25Similarity(),
                new BooleanSimilarity(),
        };
        return new MultiSimilarity(similarities);
    }

}