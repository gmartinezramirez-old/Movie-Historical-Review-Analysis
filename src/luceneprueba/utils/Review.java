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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author JAno
 */
public class Review {
    
    private int id;
    private int fecha;
    private String review;
    private double score;
    private String [] topicos;
    private double [] clasificador1;
    private double [] clasificador2;
    
    public Review(){
        this.id = 0;
        this.fecha = 0;
        this.score = 0.0;
        this.topicos = null;
        this.clasificador1 = null;
        this.clasificador2 = null;
    }
    
    public Review(int id, int fecha, double score, String [] topicos){
        this.id = id;
        this.fecha = fecha;
        this.score = score;
        this.topicos = topicos;
        this.clasificador1 = null;
        this.clasificador2 = null;
    }
    
    public Review(int id, int fecha, double score, String [] topicos, double [] clasificador1, double [] clasificador2){
        this.id = id;
        this.fecha = fecha;
        this.score = score;
        this.topicos = topicos;
        this.clasificador1 = clasificador1;
        this.clasificador2 = clasificador2;
    }

    public Review(int fecha, double score, String [] topicos, double [] clasificador1, double [] clasificador2){
        this.id = 0;
        this.fecha = fecha;
        this.score = score;
        this.topicos = topicos;
        this.clasificador1 = clasificador1;
        this.clasificador2 = clasificador2;
    }

    public Review(int fecha, String review, double score, String [] topicos, double [] clasificador1, double [] clasificador2){
        this.id = 0;
        this.fecha = fecha;
        this.review = review;
        this.score = score;
        this.topicos = topicos;
        this.clasificador1 = clasificador1;
        this.clasificador2 = clasificador2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String [] getTopicos() {
        return topicos;
    }

    public void setTopicos(String [] topicos) {
        this.topicos = topicos;
    }

    public double[] getClasificador1() {
        return clasificador1;
    }

    public void setClasificador1(double[] clasificador1) {
        this.clasificador1 = clasificador1;
    }

    public double[] getClasificador2() {
        return clasificador2;
    }

    public void setClasificador2(double[] clasificador2) {
        this.clasificador2 = clasificador2;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    
    public int convertDate(String date){
        String [] dateParser = date.split(" ");
        int newDate = Integer.parseInt(dateParser[2])*10000 + Integer.parseInt(dateParser[0]);
        
        switch(dateParser[1]){
            case "January":
                newDate += 100;
                break;
            case "February":
                newDate += 200;
                break;
            case "March":
                newDate += 300;
                break;
            case "April":
                newDate += 400;
                break;
            case "May":
                newDate += 500;
                break;
            case "June":
                newDate += 600;
                break;
            case "July":
                newDate += 700;
                break;
            case "August":
                newDate += 800;
                break;
            case "September":
                newDate += 900;
                break;
            case "October":
                newDate += 1000;
                break;
            case "November":
                newDate += 1100;
                break;
            case "December":
                newDate += 1200;
                break;
        }
        
        return newDate;
    }
    
    public double convertScore(String score){
        String [] scoreParser = score.split(" ");
        
        return Double.parseDouble(scoreParser[0])/Double.parseDouble(scoreParser[3]);
    }
    
    public List<Review> getListForTxt(){
        List<Review> reviews = new ArrayList();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("files/output/datos_reviews.txt"));
            BufferedReader brActorClassificator = new BufferedReader(new FileReader("files/output/clasificador_actor.txt"));
            BufferedReader brMovieClassificator = new BufferedReader(new FileReader("files/output/clasificador_pelicula.txt"));            
        
            File dir = new File("files/output/clasificador");
            File[] files = dir.listFiles();
            String datos;
            int i = 0;
            
            while ((datos = br.readLine()) != null) {
                System.out.println("Guardando la linea: " + datos);
                String datosClasificadorActor=brActorClassificator.readLine();
                String datosClasificadorMovie=brMovieClassificator.readLine();
                
                BufferedReader brReview = new BufferedReader(new FileReader(files[i]));
                String [] reviewContent = brReview.readLine().split(" ", 2);
                brReview.close();
                
                String [] datosParser = datos.split("_");
                //String [] datosParserClasificadorActor = datosClasificadorActor.split(" ");
               // String [] datoParserClasificadorMovie = datosClasificadorMovie.split(" ");
                              
                System.out.println("Review content: " + reviewContent[1]);
                if(datosParser.length == 4){
                    //System.out.println(datosParser[0]+"_"+convertDate(datosParser[1])+"_"+Arrays.toString(datosParser[2].split(", "))+"_"+convertScore(datosParser[3]));
                    reviews.add(new Review(convertDate(datosParser[1]), 
                                           reviewContent[1],
                                           convertScore(datosParser[3]), 
                                           datosParser[2].split(", "), 
                                           createArrayClasificador(datosClasificadorActor), 
                                           createArrayClasificador(datosClasificadorMovie)
                                           ));
                }
                else{
                    //System.out.println(datosParser[0]+"_"+convertDate(datosParser[1])+"_"+Arrays.toString(datosParser[2].split(", "))+"_"+0.0);
                    reviews.add(new Review(convertDate(datosParser[1]), 
                                           reviewContent[1],
                                           0.0, 
                                           datosParser[2].split(", "), 
                                           createArrayClasificador(datosClasificadorActor), 
                                           createArrayClasificador(datosClasificadorMovie)
                            ));
                }
                
                i++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        System.out.println("Largo de la lista de reviews: "+reviews.size());
        
        // print review object array
        /*
        for (Review review : reviews) {
            System.out.println(review.getId() + " " + review.getReview() + " " + Arrays.toString(review.getClasificador1()));
        }
        */
        
        return reviews;
    }
    
    public List<Review> getListByJson(JSONArray reviews){
        List<Review> reviewsList = new ArrayList();
        Iterator i = reviews.iterator();
        
        JSONObject json;
        int date;
        double puntaje;
        List<String> topics = new ArrayList();
        double [] clas1 = new double[3];
        double [] clas2 = new double[3];
        Iterator j;
        
        while(i.hasNext()){
            json = (JSONObject) i.next();
            date = (int) json.get("fecha");
            puntaje = (double) json.get("score");
            j = (Iterator) json.get("topicos");
            while(j.hasNext()){
                topics.add((String) j.next());
            }
            j = (Iterator) json.get("clasificador1");
            int count = 0;
            while(j.hasNext()){
                clas1[count] = (double) j.next();
                count++;
            }
            j = (Iterator) json.get("clasificador2");
            count = 0;
            while(j.hasNext()){
                clas2[count] = (double) j.next();
                count++;
            }
            //reviewsList.add(new Review(date, puntaje, topics, clas1, clas2));
        }
        
        return reviewsList;
    }
    
    public List<Review> sortByDate(List<Review> reviews){
        
        Collections.sort(reviews, (Review r1, Review r2) -> {
            int date1 = r1.getFecha();
            int date2 = r2.getFecha();

            /*For ascending order*/
            return date1-date2;

            /*For descending order*/
            //rollno2-rollno1;
        });
        return reviews;
    }
    
    public double[] createArrayClasificador(String linea) {
        double[] arrayClasificador = new double[3];
        String [] lineaSplit = linea.split(" ");
        // [0] = neg, [1] = pos, [2]= unsup
        //System.out.println("linea split 2 clasificador" + " " + lineaSplit[2] + " " + lineaSplit[2]);
        arrayClasificador[0] = Double.parseDouble(lineaSplit[2]);
        arrayClasificador[1] = Double.parseDouble(lineaSplit[4]); 
        arrayClasificador[2] = Double.parseDouble(lineaSplit[6]);
        
        return  arrayClasificador;
    }
    
    public double convertStringToDouble(String number) { 
        double value = Double.parseDouble(number);
        return value;
    }
    
}
