/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import luceneprueba.CustomAnalyzers.ReviewAnalyzer;
import luceneprueba.utils.Review;
/**
 *
 * @author JAno
 */
public class Index {
    private String path;
    private String indexName;
    private String jsonFilePath;
    private final Directory directory;
    private final Analyzer analyzer = new ReviewAnalyzer();

    
    public Index(String path, String indexName) throws IOException{
        this.path = path;
        this.indexName = indexName;
        this.directory = new SimpleFSDirectory(FileSystems.getDefault().getPath(path, indexName));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Directory getDirectory() {
        return directory;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }
    
    public void create(){
        try{
            // Storing index in disk
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            try (IndexWriter iwriter = new IndexWriter(directory, config)) {
                Document doc;
                File dir = new File("files/input");
                File[] files = dir.listFiles();
                
                if(files == null){
                    //System.out.println("No existe la carpeta \'input\' dentro de la carpeta files.");
                    return;
                }
                
                if(files.length == 0){
                    //System.out.println("No hay ningun archivo en la carpeta \'input\' para ser indexado");
                    return;
                }
                
                BufferedReader br;
                String fileContent;
                JSONArray jsonArray;
                JSONParser jsonParser = new JSONParser();
                for(File file : files){
                    if(file.isFile() && file.canRead() && file.getName().endsWith(".txt")){
                        //System.out.println("Indexando el archivo: "+file.getName());
                        
                        br = new BufferedReader(new FileReader(file));
                        fileContent = br.readLine();
                        jsonArray = (JSONArray) jsonParser.parse(fileContent);
                        Iterator i = jsonArray.iterator();
                        while(i.hasNext()){
                            JSONObject json = (JSONObject) i.next();
                            doc = new Document();
                            doc.add(new TextField("movieId", file.getName().split(".txt")[0], Field.Store.YES));
                            doc.add(new TextField("path", file.toString(), Field.Store.YES));
                            doc.add(new TextField("title", (String) json.get("Title"), Field.Store.YES));
                            doc.add(new TextField("score", (String) json.get("Score"), Field.Store.YES));
                            doc.add(new TextField("review", (String) json.get("Review"), Field.Store.YES));
                            doc.add(new TextField("date", (String) json.get("Date"), Field.Store.YES));
                            doc.add(new TextField("genre", (String) json.get("Genre"), Field.Store.YES));
                            doc.add(new TextField("score", (String) json.get("Score"), Field.Store.YES));
                            iwriter.addDocument(doc);
                        }
                        br.close();
                    }
                }
                iwriter.close();
            }
        }
        catch(IOException | ParseException e){
            e.printStackTrace();
        }
    }
    
    public void create(List<Review> reviews){
        try{
            // Storing index in disk
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            try (IndexWriter iwriter = new IndexWriter(directory, config)) {
                
                Document doc;
                for(Review review : reviews){
                    doc = new Document();
                    doc.add(new TextField("score",  review.getScore()+"", Field.Store.YES));
                    doc.add(new TextField("date", review.getFecha()+"", Field.Store.YES));
                    doc.add(new TextField("review", review.getReview(), Field.Store.YES));
                    doc.add(new TextField("genre", Arrays.toString(review.getTopicos()).replace("[", "").replace("]", ""), Field.Store.YES));
                    doc.add(new TextField("clasif1", Arrays.toString(review.getClasificador1()).replace("[", "").replace("]", ""), Field.Store.YES));
                    doc.add(new TextField("clasif2", Arrays.toString(review.getClasificador2()).replace("[", "").replace("]", ""), Field.Store.YES));
                    iwriter.addDocument(doc);
                }
                
                iwriter.close();
            }
        }
        catch(IOException e){
            e.printStackTrace(System.out);
        }
    }
}
