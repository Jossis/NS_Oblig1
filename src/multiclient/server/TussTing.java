package multiclient.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
//import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

public class TussTing extends Application {

    public static Slider slider, slider2, slider3;
    static TrafikkLysServer s;

    @Override
    public void start(Stage primaryStage) {

        slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(1);
        //slider.setShowTickLabels(true);
        //slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50); // ikke i bruk?
        slider.setMinorTickCount(5); //Ikke i bruk?
        slider.setBlockIncrement(10); // ikke i bruk?
        //slider.setSnapToTicks(true);

        slider2 = new Slider();
        slider2.setMin(0);
        slider2.setMax(100);
        slider2.setValue(1);
        slider2.setMajorTickUnit(50);
        slider2.setMinorTickCount(5);
        slider2.setBlockIncrement(10);

        slider3 = new Slider();
        slider3.setMin(0);
        slider3.setMax(100);
        slider3.setValue(1);
        slider3.setMajorTickUnit(50);
        slider3.setMinorTickCount(5);
        slider3.setBlockIncrement(10);

        Button btn = new Button();
        Button knappen = new Button("Knappen");
        btn.setText("Say 'Hello World'");

        //Bilde Test/
        Label label1 = new Label("Clients");

        Label label2 = new Label(); // tom label, trengs denne?

        Image image = new Image(getClass().getResourceAsStream("rødt.png"));

        ImageView iv = new ImageView(image);
        //label3.set
//        label3.setGraphic(iv);
        iv.setImage(image);
        iv.setFitWidth(200);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        //Bilde Test
        //ScrollBar s1 = new ScrollBar(); 
        ScrollPane sp = new ScrollPane();
        ScrollPane sp2 = new ScrollPane();

        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        final ToggleGroup group = new ToggleGroup();

        RadioButton off = new RadioButton("Off");
        off.setToggleGroup(group);

        RadioButton red = new RadioButton("Red");
        red.setToggleGroup(group);
        red.setSelected(true); // hvilken knapp skal være selected når vi starter programmet

        RadioButton yellow = new RadioButton("Yellow");
        yellow.setToggleGroup(group);

        RadioButton green = new RadioButton("Green");
        green.setToggleGroup(group);

        final Label opacityValue = new Label(
                Integer.toString((int) Math.round(slider.getValue())));

        final Label opacityValue2 = new Label(
                Integer.toString((int) Math.round(slider2.getValue())));
        //Double.toString(slider2.getValue())); // denne brukte vi før

        final Label opacityValue3 = new Label(
                Integer.toString((int) Math.round(slider3.getValue())));

        /*ObservableList<String> translations = FXCollections.observableArrayList();
         translations.add("100dsfsd");
         translations.add("200saf");
         translations.add("300w5346");
         translations.add("400ztkzu");
         translations.add("500a3244tgs");
         translations.add("600a324");
         translations.add("4tgsarawt");
         translations.add("4tgsarawt");
         translations.add("4tgsarawt");*/
        ListView clients = new ListView();

        /*clients.setItems(translations);
         clients.setOrientation(Orientation.HORIZONTAL);
         clients.setMaxHeight(250);*/
        //label1.setGraphic(clients);
        ListView list = new ListView();

        GridPane root = new GridPane();
        //root.setAlignment(Pos.CENTER); skal vi bruke denne?

        root.add(off, 0, 2);
        GridPane.setMargin(off, new Insets(20, 5, 10, 10));

        root.add(red, 0, 3);
        GridPane.setMargin(red, new Insets(10, 5, 10, 10));
        root.add(slider, 1, 3);
        root.add(opacityValue, 2, 3);
        GridPane.setMargin(opacityValue, new Insets(5, 0, 0, 10));
        //GridPane.setConstraints(opacityValue, 2, 1);//Mulig ikke i bruk

        root.add(label1, 5, 1);
        GridPane.setMargin(label1, new Insets(10, 0, 0, -60));
        GridPane.setRowSpan(label1, 3);

        root.add(clients, 5, 2);
        clients.setMaxHeight(225);
        clients.setMaxWidth(100);
        GridPane.setRowSpan(clients, 7);

        root.add(yellow, 0, 4);
        GridPane.setMargin(yellow, new Insets(10, 5, 10, 10));
        root.add(slider2, 1, 4);
        root.add(opacityValue2, 2, 4);
        GridPane.setMargin(opacityValue2, new Insets(5, 0, 0, 10));
        //GridPane.setConstraints(opacityValue2, 2, 1);//Mulig ikke i bruk
        root.add(green, 0, 5);
        GridPane.setMargin(green, new Insets(10, 5, 10, 10));
        root.add(slider3, 1, 5);
        root.add(opacityValue3, 2, 5);
        GridPane.setMargin(opacityValue3, new Insets(5, 0, 0, 10));

        root.add(list, 0, 7);
        GridPane.setMargin(list, new Insets(10, 5, 10, 10));
        GridPane.setColumnSpan(list, 5);
        GridPane.setRowSpan(iv, 6);
        GridPane.setMargin(iv, new Insets(50, 40, 10, -40));
        GridPane.setMargin(clients, new Insets(55, 0, 10, -60));

        list.setMaxHeight(100);
        list.setMaxWidth(350);

        //scrollbar til list
        /*root.add(sp, 0, 7);
         sp.setContent(list);
         sp.setMaxHeight(100);
         sp.setMaxWidth(350);*/
        //scrollbar til clients
        /*root.add(sp2, 5, 2);
         sp2.setContent(clients);
         sp2.setMaxHeight(225);
         sp2.setMaxWidth(100);*/
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                opacityValue.setText(String.format("%.0f", new_val));
            }
        });

        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                opacityValue2.setText(String.format("%.0f", new_val));
            }
        });

        slider3.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                opacityValue3.setText(String.format("%.0f", new_val));
            }
        });

        //Bilde
        root.add(iv, 4, 0);

        red.setUserData("rødt");
        yellow.setUserData("gult");
        green.setUserData("grønt");

//        final ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() == off) {
                    iv.setImage(null);
                } else if (group.getSelectedToggle() != null) {
                    final Image image = new Image(
                            getClass().getResourceAsStream(
                                    group.getSelectedToggle().getUserData().toString()
                                    + ".png"
                            )
                    );
                    iv.setImage(image);
                }
            }
        });

        Scene scene = new Scene(root, 500, 300);

        primaryStage.setTitle("Traffic Light Simulator - Server");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static class TrafikkLysServer extends Task<Void> {

        int portNumber;
        List<ClientHandler> listClients = new ArrayList<ClientHandler>();
        ServerSocket server;
        ClientServer clientServer;

        public TrafikkLysServer(int portNumber) {
            this.portNumber = portNumber;
            start();
        }

        public Void call() throws Exception {
            try (
                    ServerSocket serverSocket = new ServerSocket(portNumber)) {
                setServer(serverSocket);
                while (true) {
                    Socket connect = serverSocket.accept();
                    clientServer = new ClientServer(serverSocket.accept());
                    String client = connect.getInetAddress().getHostAddress();
                    clientServer.start();
                }
            } catch (IOException e) {
                System.out.println("Exception " + e.getMessage());
            }
            return null;
        }

        public void start() {
            Thread t = new Thread(this);
            t.start();
        }

        public void setServer(ServerSocket serverSocket) {
            this.server = serverSocket;
        }

        public ServerSocket getServer() {
            return server;
        }

        public Void closeServer(ServerSocket serverSocket) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error" + e.getMessage());
            }
            return null;
        }

        public void utførGreie() {
            clientServer.createTask();
        }

        public class ClientServer extends Service<Void> {

            Socket connectSocket;
            private ClientHandler client;

            public ClientServer(Socket connectSocket) {
                this.connectSocket = connectSocket;
            }

            @Override
            protected Task<Void> createTask() {
                Task<Void> task = new Task<Void>() {
                    public Void call() throws InterruptedException {

                        try (PrintWriter out = new PrintWriter(connectSocket.getOutputStream(), true);
                                BufferedReader in = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()))) {

                            String tekst;

                            tekst = slider.getValue() + "," + slider2.getValue() + "," + slider3.getValue() + "Rød" + "\n";
                            out.println(tekst);
                        } catch (IOException e) {
                            System.out.println("Exception " + e.getMessage());
                        }
                        return null;
                    }
                };
                return task;
            }
        }

        public class ClientHandler {

            protected Socket client;
            protected PrintWriter out;

            public ClientHandler(Socket client) {
                this.client = client;
                try {
                    out = new PrintWriter(client.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        s = new TrafikkLysServer(5555);
        launch(args);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

}

