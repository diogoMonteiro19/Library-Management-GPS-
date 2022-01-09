package gps.library.ui.graphic;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MyButton extends Button {

    Background btnBkg = new Background(new BackgroundFill(Color.rgb(201, 201, 201), new CornerRadii(10), Insets.EMPTY));
    Font minor = new Font(18);

    public MyButton(){
        super();
        setBackground(btnBkg);
        setFont(minor);
    }

    public MyButton(String s){
        super(s);
        setBackground(btnBkg);
        setFont(minor);
    }
}
