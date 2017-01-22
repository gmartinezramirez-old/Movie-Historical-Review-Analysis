/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import luceneprueba.utils.Review;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author JAno
 */
public class Reader {
    private final DirectoryReader indexReader;
    private final IndexSearcher indexSearcher;
    //private Analyzer analyzer;
    private final Analyzer analyzer;
    
    public Reader(Directory directory, Analyzer analyzer) throws IOException{
        this.indexReader = DirectoryReader.open(directory);
        this.indexSearcher = new IndexSearcher(this.indexReader);
        this.analyzer = analyzer;
    }

    public DirectoryReader getIndexReader() {
        return indexReader;
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }
    
    public String cleanInput(String input){
        String clean = input.replace("/", " ");
        clean = clean.replace("(", "");
        clean = clean.replace(")", "");
        clean = clean.replace("'", "");
        clean = clean.replace("!", "");
        clean = clean.replace("*", "");
        clean = clean.replace("?", "");
        clean = clean.replace("-", "");
        clean = clean.replace("^", "");
        clean = clean.replace("~", "");
        clean = clean.replace("\\u001a", "");
        return clean;
    }
    
    public void searchOnIndex(String wordQuery, String date){
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("review", analyzer);
        Query query;
        try {
            query = parser.parse("date: \"+" + date + "\" AND " + cleanInput(wordQuery));
            ScoreDoc[] hits = indexSearcher.search(query, 60).scoreDocs;
            
            if (hits.length == 0){
                 //System.out.println("[Search] No se han encontrado coincidencias.");
            } 
            else {
                //System.out.println("[Search] Se han encontrado: " + hits.length + " coincidencias.");
                JSONArray reviews = new JSONArray();
                for (ScoreDoc hit : hits) {
                    Document hitDoc = indexSearcher.doc(hit.doc);
                    //System.out.println(i+".- Score: " + hit.score + ", Doc: " + hitDoc.get("movieId") + ", path: " + hitDoc.get("path") + ", Tile review: " + hitDoc.get("title"));
                    JSONObject json = new JSONObject();
                    json.put("date", hitDoc.get("date"));
                    json.put("genre", hitDoc.get("genre"));
                    json.put("review", hitDoc.get("review"));
                    json.put("score", hitDoc.get("score"));
                    reviews.add(json);
                }
                try (FileWriter file = new FileWriter("files/output/top-k/reviews_" + date.replace(" ", "") + ".json")) {
                    file.write(reviews.toJSONString());
                    file.flush();
                    //indexReader.close();
                }
            }

        } catch (ParseException | IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
        }
        
    }
    
    public List<Review> getListBySearchOnIndex(String wordQuery, String date){
        List<Review> reviews = new ArrayList();
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("review", analyzer);
        Query query;
        Review nullReview = new Review();
        try {
            //If para parchar el error de la fecha 21 Enero 2015
            if(date.equals("20150122")){
                //wordQuery = wordQuery.substring(7, wordQuery.length());
                System.out.println(">>>>>>>>WORD QUERY: "+wordQuery);
            }
            query = parser.parse("date: \"+" + date + "\" AND " + cleanInput(wordQuery));
            ScoreDoc[] hits = indexSearcher.search(query, 60).scoreDocs;
            
            if (hits.length == 0){
                 //System.out.println("[Search] No se han encontrado coincidencias.");
            } 
            else {
                System.out.println("[Search] Dia " + date + ": Se han encontrado: " + hits.length + " coincidencias.");
                for (ScoreDoc hit : hits) {
                    Document hitDoc = indexSearcher.doc(hit.doc);
                    
                    String [] c1 = hitDoc.get("clasif1").split(", ");
                    String [] c2 = hitDoc.get("clasif2").split(", ");
                    
                    double [] clasif1 = {Double.parseDouble(c1[0]), Double.parseDouble(c1[1]), Double.parseDouble(c1[2])};
                    double [] clasif2 = {Double.parseDouble(c2[0]), Double.parseDouble(c2[1]), Double.parseDouble(c2[2])};
                    
                    reviews.add(new Review(Integer.parseInt(hitDoc.get("date")),
                                           Double.parseDouble(hitDoc.get("score")), 
                                           hitDoc.get("genre").split(", "), 
                                           clasif1, 
                                           clasif2)
                                );
                }                
            }

        } catch (ParseException | IOException ex) {
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
        }
        return reviews;
    }
    
    public void retriveReviewsByDate(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("files/output/review_date.json"));
            JSONParser parser = new JSONParser();
            JSONArray datesReviews = (JSONArray) parser.parse(br.readLine());
            Iterator i = datesReviews.iterator();
            while(i.hasNext()){
                JSONObject json = (JSONObject) i.next();
                System.out.println("Recuperando los top-60 reviews del día " + (String) json.get("date"));
                searchOnIndex((String) json.get("review"), (String) json.get("date"));
            }
            br.close();
            //indexReader.close();
            
        } catch (FileNotFoundException | org.json.simple.parser.ParseException ex) {
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public List<List<Review>> retriveListReviewsByDate(){
        List<List<Review>> reviews = new ArrayList();
        List<Review> reviewsByDate = getListFromSearchOnIndexByDate();
        //System.out.println("\n\n---- Cantidad de reviews recuperados por fecha: " + reviewsByDate.size() + "\n\n");
        for(Review review : reviewsByDate){
            //System.out.println("Recuperando los top-60 reviews del día " + review.getFecha());
            reviews.add(getListBySearchOnIndex(review.getReview(), review.getFecha()+""));
        }
        
        return reviews;
    }
    
    public void searchOnIndexByDate(){
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("review", analyzer);
        Query query;
        try {
            BufferedReader br = new BufferedReader(new FileReader("files/dates_reviews"));
            String date;
            
            JSONArray reviews = new JSONArray();
            
            while ((date = br.readLine()) != null) {
                query = parser.parse("date: \"+" + date + "\"");
                ScoreDoc[] hits = indexSearcher.search(query, 1).scoreDocs;

                if (hits.length == 0){
                    System.out.println("No se encontraron reviews para la fecha " + date);
                }
                else{
                    System.out.println("Guardando review de la fecha " + date);
                    Document hitDoc = indexSearcher.doc(hits[0].doc);

                    JSONObject json = new JSONObject();
                    json.put("date", hitDoc.get("date"));
                    //json.put("genre", hitDoc.get("genre"));
                    json.put("review", hitDoc.get("review"));
                    //json.put("score", hitDoc.get("score"));
                    reviews.add(json);

                    
                }
            }
            
            try (FileWriter file = new FileWriter("files/output/review_date.json")) {
                file.write(reviews.toJSONString());
                file.flush();
            }
            //indexReader.close();
            

        } catch (ParseException | IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
        }
        
    }
    
    public List<Review> getListFromSearchOnIndexByDate(){
        List<Review> reviews = new ArrayList();
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("review", analyzer);
        Query query;
        Review nullReview = new Review();
        try {
            BufferedReader br = new BufferedReader(new FileReader("files/dates_reviews"));
            String date;
            
            
            while ((date = br.readLine()) != null) {
                query = parser.parse("date: \"+" + nullReview.convertDate(date) + "\"");
                ScoreDoc[] hits = indexSearcher.search(query, 1).scoreDocs;

                if (hits.length == 0){
                    System.out.println("No se encontraron reviews para la fecha " + date);
                }
                else{
                    //System.out.println("Guardando review de la fecha " + date);
                    Document hitDoc = indexSearcher.doc(hits[0].doc);

                    String [] c1 = hitDoc.get("clasif1").split(", ");
                    String [] c2 = hitDoc.get("clasif2").split(", ");
                    
                    double [] clasif1 = {Double.parseDouble(c1[0]), Double.parseDouble(c1[1]), Double.parseDouble(c1[2])};
                    double [] clasif2 = {Double.parseDouble(c2[0]), Double.parseDouble(c2[1]), Double.parseDouble(c2[2])};
                    
                    reviews.add(new Review(Integer.parseInt(hitDoc.get("date")),
                                           hitDoc.get("review"),
                                           Double.parseDouble(hitDoc.get("score")), 
                                           hitDoc.get("genre").split(", "), 
                                           clasif1, 
                                           clasif2)
                                );

                    /*
                    if(nullReview.convertDate(date) == 20150121){
                        if(hitDoc.get("review").substring(0, 1).equals(" ")){
                            System.out.println(">>>>>>>> EL error es un espacio");
                        }
                        System.out.println(">>>>>>>REVIEW CON ERROR: "+Integer.parseInt(hitDoc.get("date")) + 
                                           hitDoc.get("review") +
                                           Double.parseDouble(hitDoc.get("score")) + 
                                           Arrays.toString(hitDoc.get("genre").split(", ")) + 
                                           Arrays.toString(clasif1) + 
                                           Arrays.toString(clasif2));
                    }
                    */
                }
            }
            
            //indexReader.close();
            
        } 
        catch (ParseException | IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
            
        }
        return reviews;
    }
}
