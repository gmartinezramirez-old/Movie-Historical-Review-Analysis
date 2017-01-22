/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simemisorreceptor;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessage;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jano
 */
public class Adventure extends JSimProcess
{
    public static final double LAMBDA = 1.0;

    private JSimMessageBox box2;

    // ------------------------------------------------------------------------------------------------------------------------------------

    public Adventure(String name, JSimSimulation parent, JSimMessageBox messageBox, JSimProcess Emisor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException
    {
            super(name, parent);
            box2 = messageBox;
    } // constructor

    protected void life()
    {
            JSimMessage mensaje;
            double sleepTime;

            try
            {
                int inicio = SimEmisorReceptor.getTiempo();
                myReview Adventure = SimEmisorReceptor.objetoFecha(2, inicio);
                
                FileWriter file = new FileWriter("files/output/ranking/adventure.csv");
                while (true)
                {
                    inicio = Adventure.getFecha();
                    message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< leyendo bandeja mensajes de entrada <<");
                    mensaje = receiveMessageWithoutBlocking(box2);
                    
                    if(inicio != SimEmisorReceptor.getTiempo()){
                        
                        /****************************************
                           LLAMAR FUNCION DE RANKING Y ESCRIBIR
                        *****************************************/
                        if(!Double.isNaN(SimEmisorReceptor.formulaRanking(Adventure))){
                            message(inicio+" Ranking acumulado Adventure: "+SimEmisorReceptor.getT2());
                            file.write(SimEmisorReceptor.FormatoFecha(inicio)+","+SimEmisorReceptor.getT2()+"\n");
                        }

                        double ranking = SimEmisorReceptor.formulaRanking(Adventure)+SimEmisorReceptor.getT2()-SimEmisorReceptor.enfria;
                        if(ranking > 0.0){
                            SimEmisorReceptor.setT2(ranking);
                        }
                        else{
                            SimEmisorReceptor.setT2(0.0);
                        }

                        Adventure = SimEmisorReceptor.objetoFecha(2, SimEmisorReceptor.getTiempo());

                        Adventure.setRanking(SimEmisorReceptor.getT2());
                    }
                    
                    if (mensaje == null){
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< bandeja vacia <<");
                    }
                    else{
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< Recibiendo mensaje: " + mensaje.getData().toString());
                        Adventure.setScore(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 0).toString()));
                        Adventure.setC11(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 1).toString()));
                        Adventure.setC12(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 2).toString()));
                        Adventure.setC13(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 3).toString()));
                        Adventure.setC21(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 4).toString()));
                        Adventure.setC22(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 5).toString()));
                        Adventure.setC23(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 6).toString()));
                        Adventure.setParcial((Adventure.getC11()*Adventure.getScore()+Adventure.getC21())/SimEmisorReceptor.alpha+SimEmisorReceptor.alpha*(Adventure.getC12()*Adventure.getScore()+Adventure.getC22())+Adventure.getC13()*Adventure.getScore()+Adventure.getC23());
                        Adventure.setNumeroReviews(Adventure.getNumeroReviews()+1);
                    }
                    sleepTime = JSimSystem.negExp(LAMBDA);
                    message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + ": espero el siguiente mensaje");
                    hold(1);
                } // while
            } // try
            catch (JSimException e)
            {
                e.printStackTrace();
                e.printComment(System.err);
            } catch (IOException ex) {
            Logger.getLogger(Adventure.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
    } // life

} // class ReceivingProcess
