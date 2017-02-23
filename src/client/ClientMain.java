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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application
{
    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Loginn.fxml"));
        primaryStage.setTitle("Multi-client Socket - Client");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    @Override
    public void stop()
    {
        System.exit(0);
    }
    
    public void inlogga(String s){
        try {
            replaceSceneContent("clientting.fxml");
        } catch (Exception ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(ClientMain.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 700, 450);
            scene.getStylesheets().add(ClientMain.class.getResource("demo.css").toExternalForm());
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }
    public static void main(String[] args) throws IOException
    {
      launch(args);
    }
}
