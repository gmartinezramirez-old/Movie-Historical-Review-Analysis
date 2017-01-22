/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import luceneprueba.utils.Menu;
import luceneprueba.utils.FileParser;
import luceneprueba.utils.Review;
import simemisorreceptor.SimEmisorReceptor;

public class LucenePrueba {

    public static void main(String[] args) throws IOException {
        try{           
            int opcion;
            Index index = new Index("files/", "indice_invertido");
            Review nullReview = new Review();
            do{
                Menu.printMainMenu();
                Scanner in = new Scanner(System.in);
                opcion = in.nextInt();
                
                switch(opcion){
                    case 1:
                        index.create(nullReview.getListForTxt());
                        System.out.println("Índice creado con éxito");
                        break;
                    case 2:
                        Reader reader = new Reader(index.getDirectory(), index.getAnalyzer());
                        System.out.print("Ingrese la palabra a buscar: ");
                        Scanner query = new Scanner(System.in);
                        String queryReview = query.nextLine();
                        System.out.print("Ingrese la fecha: ");
                        String queryDate = query.nextLine();
                        reader.searchOnIndex(queryReview, queryDate);
                        break;
                    case 3:
                        Reader reader_date = new Reader(index.getDirectory(), index.getAnalyzer());
                        reader_date.searchOnIndexByDate();
                        break;
                    case 4:    
                        Reader retriveReviews = new Reader(index.getDirectory(), index.getAnalyzer());
                        //retriveReviews.retriveReviewsByDate();
                        System.out.println("El largo de la lista resultante es: " + retriveReviews.retriveListReviewsByDate().size());
                        break;
                    case 5:
                        FileParser.createFilesFromJSONArray();
                        break;
                    case 6:
                        //nullReview.getListForTxt();
                        Reader retriveReviews2 = new Reader(index.getDirectory(), index.getAnalyzer());
                        SimEmisorReceptor.main(retriveReviews2.retriveListReviewsByDate());
                        break;    
                    case 7:
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }
                System.out.print("\n\n\n\n");
            } while(opcion != opcion);
            
            index.getDirectory().close();
            
        }
        catch(Exception ex){
            for(StackTraceElement st : ex.getStackTrace()){
                System.out.println(st.toString());
            }
            System.out.println(ex.getLocalizedMessage());
        }        
    }
    
}
