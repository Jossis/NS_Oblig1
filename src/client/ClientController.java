/**
 *Navn: Elias Andreassen Thøgersen
 *Studentnr: s236603
 *Klasse: INFORMATIK14HA
 * 
 *Navn: Synne Storhaug
 *Studentnr: s236636
 *Klasse: INFORMATIK14HA 
 * 
 *Navn: Christopher Toussi
 *Studentnr: s163502
 *Klasse: INFORMATIK14HA
 * 
 *Navn: Johannes Strige
 *Studentnr: s236652
 *Klasse: INFORMATIK14HA 
 **/
package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClientController implements Initializable
{
    private final Image imageRod, imageGult, imageGront, imageTom;
    private String farge, fargeFraServer;
    private String[] deler; 
    private int tidRod, tidGronn, tidGul;
    private boolean forrigeVarRod;
    
    @FXML
    public Button knapp;
    public Button logginn;
    public String user;
    public String pass;
    
    @FXML
    private ImageView imageView;

    public ClientController() { 
        forrigeVarRod = false;
        imageRod = new Image(getClass().getResourceAsStream("rod.png"));
        imageGront = new Image(getClass().getResourceAsStream("gront.png"));
        imageGult = new Image(getClass().getResourceAsStream("gult.png"));
        imageTom = new Image(getClass().getResourceAsStream("tom.png"));
        
        Lytter lytter = new Lytter();
        lytter.start();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        imageView.setImage(imageTom);
        farge = "off";
        
        logginn.setOnAction((javafx.event.ActionEvent e) ->{
            if(e.getSource()==logginn){
                multiclient.server.MultiClientServerController.sjekk(user,pass);
            }
        }
                
        })
        knapp.setOnAction((javafx.event.ActionEvent e) -> {
            if(e.getSource() == knapp) {
                if("On".equals(knapp.getText())) {
                    knapp.setText("Off");
                    farge = "";
                    byttBilde(fargeFraServer);
                } else {
                    knapp.setText("On");
                    farge = "off";
                    imageView.setImage(imageTom);
                    //imageView.setImage(imageTom);
                }
            }//end of if //To change body of generated methods, choose Tools | Templates.
        });//end of setOnAction
    }
    
    public void byttBilde(String fargeTing) {
        switch (fargeTing) {
            case "Rød":
                imageView.setImage(imageRod);
                break;
            case "Gul":
                imageView.setImage(imageGult);
                break;
            case "Grønn":
                imageView.setImage(imageGront);
                break;
            case "off":
                imageView.setImage(imageTom);
                break;
        }
    }
    
    private class Lytter implements Runnable {
    
        public void start() {
            Thread t = new Thread(this);
            t.start();
        }
        
        @SuppressWarnings("SleepWhileInLoop")
        public void tingTing(int tidRod, int tidGul, int tidGronn, String farge1) throws InterruptedException, ExecutionException {
            
            farge = farge1;
            
            while(true) {
                
                if(farge.equals("off")) {
                    byttBilde(farge);
                    return;
                }

                switch (farge) {
                    case "Rød":
                        forrigeVarRod = true;
                        farge = "Gul";
                        Thread.sleep(tidRod * 1000);
                        byttBilde(farge);
                        break;
                    case "Gul":
                        if(forrigeVarRod) {
                            farge = "Grønn";
                            Thread.sleep(tidGul * 1000);
                            byttBilde(farge);
                        } else {
                            farge = "Rød";
                            Thread.sleep(tidGul * 1000);
                            byttBilde(farge);
                        }   break;
                    case "Grønn":
                        forrigeVarRod = false;
                        farge = "Gul";
                        Thread.sleep(tidGronn * 1000);
                        byttBilde(farge);
                        break;
                }
            }
        }

        @Override
        public void run() {

            while(true) {
                String hostName = "127.0.0.1"; // Default host, localhost
                int portNumber = 5555; // Default port to use


                System.out.println("Hi, I am EchoUCase TCP client!");

                try
                (
                    // create TCP socket for the given hostName, remote port PortNumber
                    Socket echoSocket = new Socket(hostName, portNumber);

                    // Stream reader from the socket
                    BufferedReader in =
                            new BufferedReader(
                                    new InputStreamReader(echoSocket.getInputStream()));
                )
                {
                    
                    // read from the socket and display
                    String receivedText = in.readLine();
                    System.out.println("Dette kom fra server: " + receivedText);
                    deler = receivedText.split(",");
                    tidRod = Integer.parseInt(deler[0]);
                    tidGul = Integer.parseInt(deler[1]);
                    tidGronn = Integer.parseInt(deler[2]);
                    fargeFraServer = deler[3];
                    
                    if(!"off".equals(fargeFraServer) && !"off".equals(farge))
                        tingTing(tidRod,tidGul,tidGronn,fargeFraServer);
                    
                } catch (UnknownHostException e) {
                    System.err.println("Don't know about host " + hostName);
                    System.exit(1);
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection to " +
                            hostName);
                    System.exit(1);
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
