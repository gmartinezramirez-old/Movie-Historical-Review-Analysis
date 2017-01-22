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
public class Biography extends JSimProcess
{
    public static final double LAMBDA = 1.0;

    private JSimMessageBox box4;

    // ------------------------------------------------------------------------------------------------------------------------------------

    public Biography(String name, JSimSimulation parent, JSimMessageBox messageBox, JSimProcess Emisor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException
    {
            super(name, parent);
            box4 = messageBox;
    } // constructor

    protected void life()
    {
            JSimMessage mensaje;
            double sleepTime;

            try
            {
                int inicio = SimEmisorReceptor.getTiempo();
                myReview Biography = SimEmisorReceptor.objetoFecha(4, inicio);
                
                FileWriter file = new FileWriter("files/output/ranking/biography.csv");
                while (true)
                {
                    inicio = Biography.getFecha();
                    
                    message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< leyendo bandeja mensajes de entrada <<");
                    mensaje = receiveMessageWithoutBlocking(box4);
                    
                    if(inicio != SimEmisorReceptor.getTiempo()){
                        
                        /****************************************
                           LLAMAR FUNCION DE RANKING Y ESCRIBIR
                        *****************************************/
                        if(!Double.isNaN(SimEmisorReceptor.formulaRanking(Biography))){
                            message(inicio+" Ranking acumulado Biography: "+SimEmisorReceptor.getT4());
                            file.write(SimEmisorReceptor.FormatoFecha(inicio)+","+SimEmisorReceptor.getT4()+"\n");
                        }
                            
                        double ranking = SimEmisorReceptor.formulaRanking(Biography)+SimEmisorReceptor.getT4()-SimEmisorReceptor.enfria;
                        if(ranking > 0.0){
                            SimEmisorReceptor.setT4(ranking);
                        }
                        else{
                            SimEmisorReceptor.setT4(0.0);
                        }

                        Biography = SimEmisorReceptor.objetoFecha(4, SimEmisorReceptor.getTiempo());

                        Biography.setRanking(SimEmisorReceptor.getT4());
                    }
                    
                    if (mensaje == null){
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< bandeja vacia <<");
                    }
                    else{
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< Recibiendo mensaje: " + mensaje.getData().toString());
                        Biography.setScore(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 0).toString()));
                        Biography.setC11(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 1).toString()));
                        Biography.setC12(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 2).toString()));
                        Biography.setC13(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 3).toString()));
                        Biography.setC21(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 4).toString()));
                        Biography.setC22(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 5).toString()));
                        Biography.setC23(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 6).toString()));
                        Biography.setParcial((Biography.getC11()*Biography.getScore()+Biography.getC21())/SimEmisorReceptor.alpha+SimEmisorReceptor.alpha*(Biography.getC12()*Biography.getScore()+Biography.getC22())+Biography.getC13()*Biography.getScore()+Biography.getC23());
                        Biography.setNumeroReviews(Biography.getNumeroReviews()+1);
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
            Logger.getLogger(Biography.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
    } // life

} // class ReceivingProcess
