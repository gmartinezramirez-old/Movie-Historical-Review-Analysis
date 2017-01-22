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
public class Action extends JSimProcess
{
	public static final double LAMBDA = 1.0;

	private JSimMessageBox box1;

	// ------------------------------------------------------------------------------------------------------------------------------------

	public Action(String name, JSimSimulation parent, JSimMessageBox messageBox, JSimProcess Emisor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException
	{
            super(name, parent);
            box1 = messageBox;
	} // constructor

	protected void life()
	{
            JSimMessage mensaje;
            double sleepTime;

            try
            {
                int inicio = SimEmisorReceptor.getTiempo();
                myReview Action = SimEmisorReceptor.objetoFecha(1, inicio);
                
                FileWriter file = new FileWriter("files/output/ranking/action.csv");
                while (true)
                {             
                    
                    message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + " < leyendo bandeja de entrada <<");
                    mensaje = receiveMessageWithoutBlocking(box1);
                    
                    if(inicio != SimEmisorReceptor.getTiempo()){
                        
                        /****************************************
                           LLAMAR FUNCION DE RANKING Y ESCRIBIR
                        *****************************************/
                        if(!Double.isNaN(SimEmisorReceptor.formulaRanking(Action))){
                            message(inicio+" Ranking acumulado Adventure: "+SimEmisorReceptor.getT1());
                            file.write(SimEmisorReceptor.FormatoFecha(inicio)+","+SimEmisorReceptor.getT1()+"\n");
                        }
                        double ranking = SimEmisorReceptor.formulaRanking(Action)+SimEmisorReceptor.getT1()-SimEmisorReceptor.enfria;
                        if(ranking > 0.0){
                            SimEmisorReceptor.setT1(ranking);
                        }
                        else{
                            SimEmisorReceptor.setT1(0.0);
                        }

                        Action = SimEmisorReceptor.objetoFecha(1, SimEmisorReceptor.getTiempo());

                        Action.setRanking(SimEmisorReceptor.getT1());
                    }
                    
                    if (mensaje == null){
                        message("sin mensajes...");
                    }
                    else{
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "< Recibiendo mensaje: " + mensaje.getData().toString());
                        Action.setScore(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 0).toString()));
                        Action.setC11(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 1).toString()));
                        Action.setC12(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 2).toString()));
                        Action.setC13(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 3).toString()));
                        Action.setC21(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 4).toString()));
                        Action.setC22(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 5).toString()));
                        Action.setC23(Double.parseDouble(SimEmisorReceptor.retornaValor(mensaje.getData().toString(), 6).toString()));
                        Action.setParcial((Action.getC11()*Action.getScore()+Action.getC21())/SimEmisorReceptor.alpha+SimEmisorReceptor.alpha*(Action.getC12()*Action.getScore()+Action.getC22())+Action.getC13()*Action.getScore()+Action.getC23());
                        Action.setNumeroReviews(Action.getNumeroReviews()+1);
                    }
                    
                    sleepTime = JSimSystem.negExp(LAMBDA);
                    //message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + ": espero el siguiente mensaje");
                    hold(1);
                    
                } // while
            } // try
            catch (JSimException e)
            {
                e.printStackTrace();
                e.printComment(System.err);
            } catch (IOException ex) {
                Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
            } // catch
	} // life

} // class ReceivingProcess
