/**
 * Multiclient socket server with JavaFX: Main class
 * DATS/ITPE2410 Networking and Cloud Computing, Spring 2016
 * HiOA
 * -by Raju Shrestha
 * 2016.02.21
 **/
package multiclient.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MultiClientServerMain extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("multiclientserver.fxml"));
        primaryStage.setTitle("Multi-client Socket - Server");
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop()
    {
        System.exit(0);
    }
}
