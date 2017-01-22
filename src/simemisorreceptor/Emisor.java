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
import cz.zcu.fav.kiv.jsim.ipc.JSimAssymetricMessage;
import cz.zcu.fav.kiv.jsim.ipc.JSimIndirectMessage;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageForReceiver;
import java.util.List;
import luceneprueba.utils.Review;
import sun.java2d.cmm.ColorTransform;

/**
 *
 * @author Jano
 */
public class Emisor  extends JSimProcess
{
	public static final double LAMBDA = 1.0;

	private JSimMessageBox box;
        
        Action receiverT1;
        Adventure receiverT2;
        Animation receiverT3;
        Biography receiverT4;
        Comedy receiverT5;
        Crime receiverT6;
        Documentary receiverT7;
        Drama receiverT8;
        Family receiverT9;
        Fantasy receiverT10;
        FilmNoir receiverT11;
        History receiverT12;
        Horror receiverT13;
        Music receiverT14;
        Musical receiverT15;
        Mystery receiverT16;
        Romance receiverT17;
        SciFi receiverT18;
        Sport receiverT19;
        Thriller receiverT20;
        War receiverT21;
        Western receiverT22;

	// ------------------------------------------------------------------------------------------------------------------------------------

        List<List<Review>> reviews;
        
	public Emisor(String name, JSimSimulation parent, JSimMessageBox messageBox, List<List<Review>> reviews) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException
	{
		super(name, parent);
                
		box = messageBox;
                this.reviews = reviews;
	} // constructor

    public Action getReceiverT1() {
        return receiverT1;
    }

    public void setReceiverT1(Action receiverT1) {
        this.receiverT1 = receiverT1;
    }

    public Adventure getReceiverT2() {
        return receiverT2;
    }

    public void setReceiverT2(Adventure receiverT2) {
        this.receiverT2 = receiverT2;
    }

    public Animation getReceiverT3() {
        return receiverT3;
    }

    public void setReceiverT3(Animation receiverT3) {
        this.receiverT3 = receiverT3;
    }

    public Biography getReceiverT4() {
        return receiverT4;
    }

    public void setReceiverT4(Biography receiverT4) {
        this.receiverT4 = receiverT4;
    }

    public Comedy getReceiverT5() {
        return receiverT5;
    }

    public void setReceiverT5(Comedy receiverT5) {
        this.receiverT5 = receiverT5;
    }

    public Crime getReceiverT6() {
        return receiverT6;
    }

    public void setReceiverT6(Crime receiverT6) {
        this.receiverT6 = receiverT6;
    }

    public Documentary getReceiverT7() {
        return receiverT7;
    }

    public void setReceiverT7(Documentary receiverT7) {
        this.receiverT7 = receiverT7;
    }

    public Drama getReceiverT8() {
        return receiverT8;
    }

    public void setReceiverT8(Drama receiverT8) {
        this.receiverT8 = receiverT8;
    }

    public Family getReceiverT9() {
        return receiverT9;
    }

    public void setReceiverT9(Family receiverT9) {
        this.receiverT9 = receiverT9;
    }

    public Fantasy getReceiverT10() {
        return receiverT10;
    }

    public void setReceiverT10(Fantasy receiverT10) {
        this.receiverT10 = receiverT10;
    }

    public FilmNoir getReceiverT11() {
        return receiverT11;
    }

    public void setReceiverT11(FilmNoir receiverT11) {
        this.receiverT11 = receiverT11;
    }

    public History getReceiverT12() {
        return receiverT12;
    }

    public void setReceiverT12(History receiverT12) {
        this.receiverT12 = receiverT12;
    }

    public Horror getReceiverT13() {
        return receiverT13;
    }

    public void setReceiverT13(Horror receiverT13) {
        this.receiverT13 = receiverT13;
    }

    public Music getReceiverT14() {
        return receiverT14;
    }

    public void setReceiverT14(Music receiverT14) {
        this.receiverT14 = receiverT14;
    }

    public Musical getReceiverT15() {
        return receiverT15;
    }

    public void setReceiverT15(Musical receiverT15) {
        this.receiverT15 = receiverT15;
    }

    public Mystery getReceiverT16() {
        return receiverT16;
    }

    public void setReceiverT16(Mystery receiverT16) {
        this.receiverT16 = receiverT16;
    }

    public Romance getReceiverT17() {
        return receiverT17;
    }

    public void setReceiverT17(Romance receiverT17) {
        this.receiverT17 = receiverT17;
    }

    public SciFi getReceiverT18() {
        return receiverT18;
    }

    public void setReceiverT18(SciFi receiverT18) {
        this.receiverT18 = receiverT18;
    }

    public Sport getReceiverT19() {
        return receiverT19;
    }

    public void setReceiverT19(Sport receiverT19) {
        this.receiverT19 = receiverT19;
    }

    public Thriller getReceiverT20() {
        return receiverT20;
    }

    public void setReceiverT20(Thriller receiverT20) {
        this.receiverT20 = receiverT20;
    }

    public War getReceiverT21() {
        return receiverT21;
    }

    public void setReceiverT21(War receiverT21) {
        this.receiverT21 = receiverT21;
    }

    public Western getReceiverT22() {
        return receiverT22;
    }

    public void setReceiverT22(Western receiverT22) {
        this.receiverT22 = receiverT22;
    }

    public List<List<Review>> getReviews() {
        return reviews;
    }

    public void setReviews(List<List<Review>> reviews) {
        this.reviews = reviews;
    }
    
    
        
        @Override
	protected void life()
	{
            JSimAssymetricMessage mensaje;
            //int tiempo = 19990419;
            double sleepTime;
            int i = 0;
            mensaje = null;
            try
            {
                
                System.out.println(this.reviews.size() + " total de reviews para esta fecha");
                int cuenta = this.reviews.size();
                int siguiente = 0;
                
                //leo todos los reviews de esta fecha
                while (true)
                {
                    int dias = this.reviews.size();
                    
                    for(List<Review> r1Hoydia : this.reviews){
                        dias--;
                        
                        if(dias-1 == -1){
                            SimEmisorReceptor.setFin(true);
                        }
                        
                        SimEmisorReceptor.setTiempo(r1Hoydia.get(0).getFecha());
                        
                        for(Review rHoydia : r1Hoydia){

                            SimEmisorReceptor.setTiempo(rHoydia.getFecha());

                            //n-p-u >> clasificador 1 y 2
                            //score
                            for(String rHoy : rHoydia.getTopicos()){
                                //mensaje para parsear
                                String parsear;

                                //System.out.println(rHoydia.getClasificador1().toString());

                                //System.out.println(">>>"+rHoydia.getScore()+" "+rHoydia.getClasificador1()[0]+" "+rHoydia.getClasificador1()[1]+" "+rHoydia.getClasificador1()[2]+" "+rHoydia.getClasificador2()[0]+" "+rHoydia.getClasificador2()[1]+" "+rHoydia.getClasificador2()[2]);

                                parsear = rHoydia.getScore()+" "+rHoydia.getClasificador1()[0]+" "+rHoydia.getClasificador1()[1]+" "+rHoydia.getClasificador1()[2]+" "+rHoydia.getClasificador2()[0]+" "+rHoydia.getClasificador2()[1]+" "+rHoydia.getClasificador2()[2];

                                if(rHoy.equals("Action")){
                                    mensaje = new JSimMessageForReceiver(receiverT1, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Adventure")){
                                    mensaje = new JSimMessageForReceiver(receiverT2, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Animation")){
                                    mensaje = new JSimMessageForReceiver(receiverT3, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Biography")){
                                    mensaje = new JSimMessageForReceiver(receiverT4, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Comedy")){
                                    mensaje = new JSimMessageForReceiver(receiverT5, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Crime")){
                                    mensaje = new JSimMessageForReceiver(receiverT6, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Documentary")){
                                    mensaje = new JSimMessageForReceiver(receiverT7, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Drama")){
                                    mensaje = new JSimMessageForReceiver(receiverT8, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Family")){
                                    mensaje = new JSimMessageForReceiver(receiverT9, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Fantasy")){
                                    mensaje = new JSimMessageForReceiver(receiverT10, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("FilmNoir")){
                                    mensaje = new JSimMessageForReceiver(receiverT11, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("History")){
                                    mensaje = new JSimMessageForReceiver(receiverT12, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Horror")){
                                    mensaje = new JSimMessageForReceiver(receiverT13, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Music")){
                                    mensaje = new JSimMessageForReceiver(receiverT14, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Musical")){
                                    mensaje = new JSimMessageForReceiver(receiverT15, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Mystery")){
                                    mensaje = new JSimMessageForReceiver(receiverT16, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Romance")){
                                    mensaje = new JSimMessageForReceiver(receiverT17, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("SciFi")){
                                    mensaje = new JSimMessageForReceiver(receiverT18, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Sport")){
                                    mensaje = new JSimMessageForReceiver(receiverT19, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Thriller")){
                                    mensaje = new JSimMessageForReceiver(receiverT20, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("War")){
                                    mensaje = new JSimMessageForReceiver(receiverT21, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }
                                if(rHoy.equals("Western")){
                                    mensaje = new JSimMessageForReceiver(receiverT22, parsear);
                                    sendMessageWithoutBlocking(mensaje, box);
                                }

                                //mensaje = new JSimIndirectMessage(new String("(0.0,topico1,[1,0,0],[1,0,0])"));
                                message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "> Enviando mensaje '" + mensaje.getData().toString() + "' a la caja de mensajes compartidos.");

                                //sendMessageWithBlocking(mensaje, box);

                                message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + "> Mensaje enviado.");
                            }
                        message(SimEmisorReceptor.FormatoFecha(SimEmisorReceptor.getTiempo()) + " - " + getName() + ": hibernando por " + 1 + " día.");

                        sleepTime = SimEmisorReceptor.getTiempo()+1;
                        hold(1);
                        i++;
                        siguiente++;    

                        }
                    //tiempo++;
                    //sleepTime = JSimSystem.negExp(LAMBDA);
                    } 
                } // while
            } // try
            catch (JSimException e)
            {
                    e.printStackTrace();
                    e.printComment(System.err);
                    System.out.println(e.getLocalizedMessage());
            } // catch
	} // life

} // class SendingProcess
