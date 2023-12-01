package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.example.model.TopicModel;
import org.example.parser.TopicParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Searcher {

    private final static Path currentRelativePath = Paths.get("").toAbsolutePath();
    private final static String absPathToSearchResults = String.format("%s/output/", currentRelativePath);

    public static void executeQueries(Analyzer analyzer, Similarity similarity, String analyzerName, String similarityName) throws ParseException {
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get("index/"));
            System.out.println("Generating index");
            Indexer indexer = new Indexer();
            indexer.Indexing(analyzer, similarity);
            IndexReader indexReader = DirectoryReader.open(indexDirectory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            indexSearcher.setSimilarity(similarity);

            Map<String, Float> boost = new HashMap<>();
            boost.put("headline", 0.08f);
            boost.put("text", 0.92f);

            QueryParser queryParser = new MultiFieldQueryParser(new String[]{"headline", "text"}, analyzer, boost);

            String outputPath = absPathToSearchResults + analyzerName + "_" + similarityName;
            PrintWriter writer = new PrintWriter(outputPath, StandardCharsets.UTF_8);

            System.out.println("Loading Topics");
            TopicParser tp = new TopicParser();
            ArrayList<TopicModel> topics = tp.loadTopics();
            System.out.println("All Topics Loaded");
            System.out.println("Executing Queries");
            for (TopicModel queryData : topics) {

                List<String> splitNarrative = splitNarrIntoRelNotRel(queryData.getNarrative());
                String relevantNarr = splitNarrative.get(0).trim();
                String irrelevantNarr = splitNarrative.get(1).trim();
                BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

                if (!queryData.getTitle().isEmpty()) {
                    Query titleQuery = queryParser.parse(QueryParser.escape(queryData.getTitle()));
                    Query descriptionQuery = queryParser.parse(QueryParser.escape(queryData.getDescription()));
                    if(!relevantNarr.isEmpty()) {
                        Query narrativeQuery = queryParser.parse(QueryParser.escape(relevantNarr));
                        if(narrativeQuery != null)
                            booleanQuery.add(new BoostQuery(narrativeQuery, 1.2f), BooleanClause.Occur.SHOULD);
                    } else if(!irrelevantNarr.isEmpty()) {
                        Query irrelevantNarrativeQuery = queryParser.parse(QueryParser.escape(irrelevantNarr));
                        if(irrelevantNarrativeQuery != null)
                            booleanQuery.add(new BoostQuery(irrelevantNarrativeQuery, 2f), BooleanClause.Occur.FILTER);
                    }

                    booleanQuery.add(new BoostQuery(titleQuery, 4f), BooleanClause.Occur.SHOULD);
                    booleanQuery.add(new BoostQuery(descriptionQuery, 1.7f), BooleanClause.Occur.SHOULD);
                    ScoreDoc[] hits = indexSearcher.search(booleanQuery.build(), 1000).scoreDocs;

                    for (int hitIndex = 0; hitIndex < hits.length; hitIndex++) {
                        ScoreDoc hit = hits[hitIndex];
                        writer.println(queryData.getNumber() + " 0 " + indexSearcher.doc(hit.doc).get("docno") +
                                " " + hitIndex + " " + hit.score + " 0 ");
                    }
                }
            }
            indexReader.close();
            writer.flush();
            writer.close();
            System.out.println("queries executed for : " + analyzerName + "-" + similarityName);
            System.out.println("queries saved to location : " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> splitNarrIntoRelNotRel(String narrative) {
        StringBuilder relevantNarr = new StringBuilder();
        StringBuilder irrelevantNarr = new StringBuilder();
        List<String> splitNarrative = new ArrayList<>();

        BreakIterator bi = BreakIterator.getSentenceInstance();
        bi.setText(narrative);
        int index = 0;
        while (bi.next() != BreakIterator.DONE) {
            String sentence = narrative.substring(index, bi.current());
            if (!sentence.contains("not relevant") && !sentence.contains("irrelevant")) {
                relevantNarr.append(sentence.replaceAll(
                        "a relevant document identifies|a relevant document could|a relevant document may|a relevant document must|a relevant document will|a document will|to be relevant|relevant documents|a document must|relevant|will contain|will discuss|will provide|must cite",
                        ""));
            } else {
                irrelevantNarr.append(sentence.replaceAll("are also not relevant|are not relevant|are irrelevant|is not relevant|not|NOT", ""));
            }
            index = bi.current();
        }
        splitNarrative.add(relevantNarr.toString());
        splitNarrative.add(irrelevantNarr.toString());
        return splitNarrative;
    }


}
