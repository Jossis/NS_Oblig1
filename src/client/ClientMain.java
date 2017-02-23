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
import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("clientting.fxml"));
        primaryStage.setTitle("Multi-client Socket - Client");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    @Override
    public void stop()
    {
        System.exit(0);
    }
    
    public static void main(String[] args) throws IOException
    {
      launch(args);
    }
}
