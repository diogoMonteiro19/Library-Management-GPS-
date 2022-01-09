package gps.library.ui.graphic;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MyProgressBar extends ProgressBar {

    public MyProgressBar(){
        super();
    }

    public MyProgressBar(double prog){
        super(prog);
        int progress = (int) (prog * 100);
        if(progress > 0 && progress <= 25){
            super.setStyle("-fx-accent: green");
        }
        if(progress > 25 && progress <= 50) {
            super.setStyle("-fx-accent: orange");
        }
        if(progress > 50 && progress <= 75){
            super.setStyle("-fx-accent: orangered");
        }
        if(progress > 75 && progress <= 100){
            super.setStyle("-fx-accent: darkred");
        }
    }
}
