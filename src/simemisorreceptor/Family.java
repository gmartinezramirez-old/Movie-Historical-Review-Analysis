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
public class Family extends JSimProcess
{
    public static final double LAMBDA = 1.0;

    private JSimMessageBox box9;

    // ------------------------------------------------------------------------------------------------------------------------------------

    public Family(String name, JSimSimulation parent, JSimMessageBox messageBox, JSimProcess Emisor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException
    {
            super(name, parent);
            box9 = messageBox;
    } // constructor

    protected void life()
    {
            JSimMessage mensaje;
            double sleepTime;

            try
            {
                int inicio = SimEmisorReceptor.getTiempo();
                myReview Family = SimEmisorReceptor.objetoFecha(9, inicio);
                
                FileWriter file = new FileWriter("files/output/ranking/family.csv");
                while (true)
                {
                    inicio = Family.getFecha();
                    
                    message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< leyendo bandeja mensajes de entrada <<");
                    mensaje = receiveMessageWithoutBlocking(box9);
                    
                    if(inicio != SimEmisorReceptor.getTiempo()){
                        
                        /****************************************
                           LLAMAR FUNCION DE RANKING Y ESCRIBIR
                        *****************************************/
                        if(!Double.isNaN(SimEmisorReceptor.formulaRanking(Family))){
                            message(inicio+" Ranking acumulado Biography: "+SimEmisorReceptor.getT9());
                            file.write(SimEmisorReceptor.FormatoFecha(inicio)+","+SimEmisorReceptor.getT9()+"\n");
                        }

                        double ranking = SimEmisorReceptor.formulaRanking(Family)+SimEmisorReceptor.getT9()-SimEmisorReceptor.enfria;
                        if(ranking > 0.0){
                            SimEmisorReceptor.setT9(ranking);
                        }
                        else{
                            SimEmisorReceptor.setT9(0.0);
                        }

                        Family = SimEmisorReceptor.objetoFecha(9, SimEmisorReceptor.getTiempo());

                        Family.setRanking(SimEmisorReceptor.getT9());
                    }
                    
                    if (mensaje == null){
                        message("sin mensajes...");
                    }                    

                    if (mensaje == null){
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< bandeja vacia <<");
                    }
                    else{
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< Recibiendo mensaje: " + mensaje.getData().toString());
                        Family.setScore(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 0).toString()));
                        Family.setC11(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 1).toString()));
                        Family.setC12(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 2).toString()));
                        Family.setC13(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 3).toString()));
                        Family.setC21(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 4).toString()));
                        Family.setC22(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 5).toString()));
                        Family.setC23(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 6).toString()));
                        Family.setParcial((Family.getC11()*Family.getScore()+Family.getC21())/SimEmisorReceptor.alpha+SimEmisorReceptor.alpha*(Family.getC12()*Family.getScore()+Family.getC22())+Family.getC13()*Family.getScore()+Family.getC23());
                        Family.setNumeroReviews(Family.getNumeroReviews()+1);
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
            Logger.getLogger(Family.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
    } // life

} // class ReceivingProcess
