/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simemisorreceptor;

/**
 *
 * @author Daniel
 */
public class myReview {
    
    private int fecha;
    private double score;
    private double c11;
    private double c12;
    private double c13;
    private double c21;
    private double c22;
    private double c23;
    private double parcial;
    private int numeroReviews;
    private double ranking;
    
    public myReview(){
        this.fecha = 0;
        this.score = 0.0;
        this.c11 = 0.0;
        this.c12 = 0.0;
        this.c13 = 0.0;
        this.c21 = 0.0;
        this.c22 = 0.0;
        this.c23 = 0.0;
        this.parcial = 0.0;
        this.numeroReviews = 0;
        this.ranking = 0.0;
    }
    
    public myReview(int fecha){
        this.fecha = fecha;
    }
    
    public myReview(int fecha, double score, double c11, double c12, double c13, double c21, double c22, double c23, double parcial, int numeroReviews, double ranking){
        this.fecha = fecha;
        this.score = score;
        this.c11 = c11;
        this.c12 = c12;
        this.c13 = c13;
        this.c21 = c21;
        this.c22 = c22;
        this.c23 = c23;
        this.parcial = parcial;
        this.numeroReviews = numeroReviews;
        this.ranking = ranking;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    
    
    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public double getParcial() {
        return parcial;
    }

    public void setParcial(double parcial) {
        this.parcial = parcial;
    }

    public int getNumeroReviews() {
        return numeroReviews;
    }

    public void setNumeroReviews(int numeroReviews) {
        this.numeroReviews = numeroReviews;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score += score;
    }

    public double getC11() {
        return c11;
    }

    public void setC11(double c11) {
        this.c11 += c11;
    }

    public double getC12() {
        return c12;
    }

    public void setC12(double c12) {
        this.c12 += c12;
    }

    public double getC13() {
        return c13;
    }

    public void setC13(double c13) {
        this.c13 += c13;
    }

    public double getC21() {
        return c21;
    }

    public void setC21(double c21) {
        this.c21 += c21;
    }

    public double getC22() {
        return c22;
    }

    public void setC22(double c22) {
        this.c22 += c22;
    }

    public double getC23() {
        return c23;
    }

    public void setC23(double c23) {
        this.c23 += c23;
    }
    
    
}
