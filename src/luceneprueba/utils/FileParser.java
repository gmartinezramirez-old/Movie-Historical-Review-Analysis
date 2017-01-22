/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author JAno
 */
public class FileParser {
    static public void createFilesFromJSONArray() throws IOException{
        File dir = new File("files/input");
        File[] files = dir.listFiles();

        if(files == null){
            System.out.println("No existe la carpeta \'input\' dentro de la carpeta files.");
            return;
        }

        if(files.length == 0){
            System.out.println("No hay ningun archivo en la carpeta \'input\' para ser indexado");
            return;
        }

        BufferedReader br;
        String fileContent;
        JSONArray jsonArray = null;
        JSONParser jsonParser = new JSONParser();
        int i = 1;
        FileWriter datosReviews = null;
        try {
            datosReviews = new FileWriter("files/output/datos_reviews.txt");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        for(File file : files){
            if(file.isFile() && file.canRead() && file.getName().endsWith(".txt")){
                System.out.println("Leyendo el archivo: "+file.getName());
                FileWriter contentReviews;
                try {               
                    br = new BufferedReader(new FileReader(file));
                    fileContent = br.readLine();
                    jsonArray = (JSONArray) jsonParser.parse(fileContent);
                    Iterator it = jsonArray.iterator();
                    DecimalFormat formato = new DecimalFormat("000000");
                    while(it.hasNext()){
                        JSONObject json = (JSONObject) it.next();
                        if(json.get("Genre") != null && json.get("Date") != null){                          
                            contentReviews = new FileWriter("files/output/clasificador/review_clasificador_"+ formato.format(i) +".txt"); 
                            datosReviews.write(Integer.toString(i)+"_"+ (String) json.get("Date")+"_"+ (String) json.get("Genre")+"_"+ (String) json.get("Score")+"\n");
                            contentReviews.write(Integer.toString(i)+" "+ (String) json.get("Review"));
                            i++;
                            contentReviews.close();
                        }
                    }
                    br.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace(System.out);
                } catch (IOException | ParseException ex) {
                    ex.printStackTrace(System.out);
                }                
            }
        }
        datosReviews.close();
    }
}
