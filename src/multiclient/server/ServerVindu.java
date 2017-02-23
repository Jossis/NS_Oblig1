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
package multiclient.server;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.control.TextArea;

public class ServerVindu extends Application {
    
    public static Slider slider, slider2, slider3;
    private Button btn;
    private Label label1;
    private Image image, image1, image2, image3;
    private ImageView iv;
    public static ToggleGroup group;
    public static RadioButton off, red, yellow, green;
    private Label opacityValue, opacityValue2, opacityValue3;
    private ListView list;
    public static TextArea klienter;
    private GridPane root;
    private ServerTing serverTing;
    
    @Override
    public void start(Stage primaryStage) {
        
        slider = new Slider();
        slider.setMin(1);
        slider.setMax(50);
        slider.setValue(2);
        
        slider2 = new Slider();
        slider2.setMin(1);
        slider2.setMax(50);
        slider2.setValue(2);
        
        slider3 = new Slider();
        slider3.setMin(1);
        slider3.setMax(50);
        slider3.setValue(2);
        
        label1 = new Label("Clients");
        
        image = new Image(getClass().getResourceAsStream("tom.png"));
        image1 = new Image(getClass().getResourceAsStream("rødt.png"));
        image2 = new Image(getClass().getResourceAsStream("gult.png"));
        image3 = new Image(getClass().getResourceAsStream("grønt.png"));
        
        iv = new ImageView(image);
        iv.setFitWidth(200);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        
        group = new ToggleGroup();

        off = new RadioButton("Off");
        off.setToggleGroup(group);
        off.setSelected(true);
        
        red = new RadioButton("Red");
        red.setToggleGroup(group);

        yellow = new RadioButton("Yellow");
        yellow.setToggleGroup(group);

        green = new RadioButton("Green");
        green.setToggleGroup(group);
        
        opacityValue = new Label(Integer.toString((int) Math.round(slider.getValue())));
        opacityValue2 = new Label(Integer.toString((int) Math.round(slider2.getValue())));
        opacityValue3 = new Label(Integer.toString((int) Math.round(slider3.getValue())));
        
        klienter = new TextArea();
        
        list = new ListView();
        
        root = new GridPane();
        
        root.add(off, 0, 2);
        GridPane.setMargin(off, new Insets(20, 5, 10, 10));
        root.add(red, 0, 3);
        GridPane.setMargin(red, new Insets(10, 5, 10, 10));
        root.add(slider, 1, 3);
        root.add(opacityValue, 2, 3);
        GridPane.setMargin(opacityValue, new Insets(5,0,0,10));
        
        root.add(label1, 5, 1);
        GridPane.setMargin(label1, new Insets(10, 0, 0, -60));
        GridPane.setRowSpan(label1, 3);
        
        root.add(klienter, 5, 2);
        klienter.setMaxHeight(225);
        klienter.setMaxWidth(100);
        klienter.setEditable(false);
        GridPane.setRowSpan(klienter, 7);
        
        root.add(yellow, 0, 4);
        GridPane.setMargin(yellow, new Insets(10, 5, 10, 10));
        root.add(slider2, 1, 4); 
        root.add(opacityValue2, 2, 4);
        GridPane.setMargin(opacityValue2, new Insets(5,0,0,10));
        root.add(green, 0, 5);
        GridPane.setMargin(green, new Insets(10, 5, 10, 10));
        root.add(slider3, 1, 5);
        root.add(opacityValue3, 2, 5 );
        GridPane.setMargin(opacityValue3, new Insets(5,0,0,10));
        
        root.add(list, 0, 7);
        GridPane.setMargin(list, new Insets(10, 5, 10, 10));
        GridPane.setColumnSpan(list, 5);
        GridPane.setRowSpan(iv, 6);
        GridPane.setMargin(iv, new Insets(50, 40, 10, -40));
        GridPane.setMargin(klienter, new Insets(55, 0, 10, -60));
        
        list.setMaxHeight(100);
        list.setMaxWidth(350);
        
        //changing values of sliders label
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            opacityValue.setText(String.format("%.0f", new_val));
        });
        
        slider2.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            opacityValue2.setText(String.format("%.0f", new_val));
        });
        
         slider3.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
             opacityValue3.setText(String.format("%.0f", new_val));
        });
        
        root.add(iv, 4, 0);

        red.setUserData("rødt");
        yellow.setUserData("gult");
        green.setUserData("grønt");
        
        //changing values of radiobuttons
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (group.getSelectedToggle() == off) {
                iv.setImage(image);
            } else if (group.getSelectedToggle() == red) {
                iv.setImage(image1);
            } else if (group.getSelectedToggle() == yellow) {
                iv.setImage(image2);
            } else if (group.getSelectedToggle() == green) {
                iv.setImage(image3);
            }
        });
        
        serverTing = new ServerTing(5555);
        serverTing.start();
        
        Scene scene = new Scene(root, 500, 300);
        
        primaryStage.setTitle("Traffic Light Simulator - Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void stop()
    {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }    
}




