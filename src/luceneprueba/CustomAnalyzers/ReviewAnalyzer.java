/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba.CustomAnalyzers;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;

/**
 *
 * @author Gonzalo
 */

/**
 * Filtros: StandardTokenizer, StandardFilter, LowerCaseFilter y StopFilter
 * usando una lista de stop words en ingles.
 */
public class ReviewAnalyzer extends StopwordAnalyzerBase {
 
    public static final int DEFAULT_MAX_TOKEN_LENGTH = 50;
    public static final CharArraySet STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
    private int maxTokenLength = DEFAULT_MAX_TOKEN_LENGTH;

 /** 
  * Construye un analizador con una lista por default de stop words.
  * */
  public ReviewAnalyzer() {
    this(STOP_WORDS_SET);
  }
    
  /** Constructor del analizar con una lista de stopwords.
   * @param stopWords lista de stop words stop words */
    public ReviewAnalyzer(CharArraySet stopWords) {
        super(stopWords);
    }
 /** Construir un analyzer con una lista de palabras obtenidas de un stop words.
   * @param stopwords Leer palabras de una lista de stopwords */   
    public ReviewAnalyzer(Reader stopwords) throws IOException {
        this(loadStopwordSet(stopwords));
    }
  
    public void setMaxTokenLength(int length) {
        maxTokenLength = length;
    }
  
    public int getMaxTokenLength() {
        return maxTokenLength;
    }
    
    @Override
    protected TokenStreamComponents createComponents(final String fieldName) {
        final StandardTokenizer src = new StandardTokenizer();
        src.setMaxTokenLength(maxTokenLength);
        TokenStream tokenizer = new StandardFilter(src);
        tokenizer = new LowerCaseFilter(tokenizer);
        tokenizer = new StopFilter(tokenizer, stopwords);
        return new TokenStreamComponents(src, tokenizer) {
        @Override
        protected void setReader(final Reader reader) {
            src.setMaxTokenLength(ReviewAnalyzer.this.maxTokenLength);
            super.setReader(reader);
      }
    };
  }

}
