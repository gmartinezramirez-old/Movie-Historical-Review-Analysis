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
public class Sport extends JSimProcess
{
    public static final double LAMBDA = 1.0;

    private JSimMessageBox box19;

    // ------------------------------------------------------------------------------------------------------------------------------------

    public Sport(String name, JSimSimulation parent, JSimMessageBox messageBox, JSimProcess Emisor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException
    {
            super(name, parent);
            box19 = messageBox;
    } // constructor

    protected void life()
    {
            JSimMessage mensaje;
            double sleepTime;

            try
            {
                int inicio = SimEmisorReceptor.getTiempo();
                myReview Sport = SimEmisorReceptor.objetoFecha(19, inicio);
                
                FileWriter file = new FileWriter("files/output/ranking/sport.csv");
                while (true)
                {                 
                    inicio = Sport.getFecha();
                    message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< leyendo bandeja mensajes de entrada <<");
                    mensaje = receiveMessageWithoutBlocking(box19);
                    if(inicio != SimEmisorReceptor.getTiempo()){
                        
                        /****************************************
                           LLAMAR FUNCION DE RANKING Y ESCRIBIR
                        *****************************************/
                        if(!Double.isNaN(SimEmisorReceptor.formulaRanking(Sport))){
                            message(inicio+" Ranking acumulado Adventure: "+SimEmisorReceptor.getT19());
                            file.write(SimEmisorReceptor.FormatoFecha(inicio)+","+SimEmisorReceptor.getT19()+"\n");
                        }

                        double ranking = SimEmisorReceptor.formulaRanking(Sport)+SimEmisorReceptor.getT19()-SimEmisorReceptor.enfria;
                        if(ranking > 0.0){
                            SimEmisorReceptor.setT19(ranking);
                        }
                        else{
                            SimEmisorReceptor.setT19(0.0);
                        }

                        Sport = SimEmisorReceptor.objetoFecha(19, SimEmisorReceptor.getTiempo());

                        Sport.setRanking(SimEmisorReceptor.getT19());
                    }
                    if (mensaje == null){
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< bandeja vacia <<");
                    }
                    else{
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< Recibiendo mensaje: " + mensaje.getData().toString());
                        Sport.setScore(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 0).toString()));
                        Sport.setC11(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 1).toString()));
                        Sport.setC12(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 2).toString()));
                        Sport.setC13(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 3).toString()));
                        Sport.setC21(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 4).toString()));
                        Sport.setC22(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 5).toString()));
                        Sport.setC23(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 6).toString()));
                        Sport.setParcial((Sport.getC11()*Sport.getScore()+Sport.getC21())/SimEmisorReceptor.alpha+SimEmisorReceptor.alpha*(Sport.getC12()*Sport.getScore()+Sport.getC22())+Sport.getC13()*Sport.getScore()+Sport.getC23());
                        Sport.setNumeroReviews(Sport.getNumeroReviews()+1);
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
            Logger.getLogger(Sport.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
    } // life

} // class ReceivingProcess
