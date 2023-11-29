package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.example.model.TopicModel;
import org.example.parser.TopicParser;

import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchEngine {
    Analyzer analyzer;
    Similarity similarity;

    //private HashMap<String, Float> boostMap;
    //private List<String> stopwords;

    public SearchEngine(Analyzer analyzer, Similarity similarity) throws IOException {
        this.analyzer = analyzer;
        this.similarity = similarity;
        //this.boostMap = boostMap;
        //this.stopwords = stopwords;
    }

    public void search() throws ParseException, IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get("index/"));
        System.out.println("Generating index....");
        Indexer indexer = new Indexer();
        indexer.Indexing(analyzer, similarity);

        System.out.println("Loading Topics");
        TopicParser tp = new TopicParser();
        ArrayList<TopicModel> topics = tp.loadTopics();

        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        isearcher.setSimilarity(similarity);

        List<String> stopwords = Files.readAllLines(Path.of("./stopwords.txt"));

        HashMap<String, Float> boostMap = new HashMap<String, Float>();
        boostMap.put("title", 0.08f);
        boostMap.put("content", 0.92f);

        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "content"}, analyzer, boostMap);
        new File("output/").mkdirs();
        File outputFile = new File("output/", "result.txt");
        PrintWriter writer = new PrintWriter(outputFile, String.valueOf(StandardCharsets.UTF_8));
        System.out.println("Running Queries");
        for(TopicModel topic : topics) {
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

            Query titleQuery = queryParser.parse(QueryParser.escape(topic.getTitle().trim()));
            booleanQuery.add(new BoostQuery(titleQuery, 5f), BooleanClause.Occur.SHOULD);

            Query descriptionQuery = queryParser.parse(QueryParser.escape(removeStopWords(topic.getDescription().trim(), stopwords)));
            booleanQuery.add(new BoostQuery(descriptionQuery, 4f), BooleanClause.Occur.SHOULD);

            // Can contain multiple relevant or irrelevant statements
            BreakIterator iterator = BreakIterator.getSentenceInstance();
            String narrative = topic.getNarrative().trim();
            iterator.setText(narrative);
            int index = 0;
            while (iterator.next() != BreakIterator.DONE) {
                String sentence = narrative.substring(index, iterator.current());
                if (sentence.length() > 0) {
                    sentence = removeStopWords(sentence, stopwords);
                    Query narrativeQuery = queryParser.parse(QueryParser.escape(sentence));
                    if (!sentence.contains("not relevant") && !sentence.contains("irrelevant")) {
                        booleanQuery.add(new BoostQuery(narrativeQuery, 1.4f), BooleanClause.Occur.SHOULD);
                    } else {
                        booleanQuery.add(new BoostQuery(narrativeQuery, 2f), BooleanClause.Occur.FILTER);
                    }
                }
                index = iterator.current();
            }

            ArrayList<Query> expandedQueries = expandQuery(isearcher, analyzer, ireader, booleanQuery.build());

            for (Query expandedQuery : expandedQueries) {
                booleanQuery.add(expandedQuery, BooleanClause.Occur.SHOULD);
            }

            ScoreDoc[] hits = isearcher.search(booleanQuery.build(), 1000).scoreDocs;
            for (ScoreDoc hit : hits) {
                Document hitDoc = isearcher.doc(hit.doc);
                // query-id 0 document-id rank score STANDARD
                writer.println(topic.getNumber() + " 0 " + hitDoc.get("id") + " 0 " + hit.score + " STANDARD");
            }
        }

        System.out.println("Saving Results");
        writer.close();
        ireader.close();
        indexDirectory.close();
    }

    private static ArrayList<Query> expandQuery(IndexSearcher isearcher, Analyzer analyzer, DirectoryReader ireader,
                                                Query query) throws IOException {
        ArrayList<Query> expandedQueries = new ArrayList<>();
        ScoreDoc[] hits = isearcher.search(query, 12).scoreDocs;
        for (ScoreDoc hit : hits) {
            Document hitDoc = ireader.document(hit.doc);
            String field = hitDoc.getField("content").stringValue();
            MoreLikeThisQuery mltqExpanded = new MoreLikeThisQuery(field, new String[]{"content"}, analyzer, "content");
            Query expandedQuery = mltqExpanded.rewrite(ireader);
            expandedQueries.add(expandedQuery);
        }
        return expandedQueries;
    }

    private static String removeStopWords(String s, List<String> stopwords) {
        ArrayList<String> allWords = Stream.of(s.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);
        return String.join(" ", allWords);
    }
}
