package org.example;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;

public class CustomAnalyzer extends StopwordAnalyzerBase {
    @Override
    protected TokenStreamComponents createComponents(String s) {
        StandardTokenizer tokenizer = new StandardTokenizer();
        TokenStream stream;
        stream = new EnglishPossessiveFilter(tokenizer);
        stream = new LowerCaseFilter(stream);
        stream = new PorterStemFilter(stream);
        stream = new LengthFilter(stream, 3, 25);
        stream = new SnowballFilter(stream, new EnglishStemmer());

        return new TokenStreamComponents(tokenizer, stream);
    }
}