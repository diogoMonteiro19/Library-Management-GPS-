package gps.library.ui.graphic;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MyLabel extends Label {

    public MyLabel(Font font){
        super();
        setFont(font);
    }

    public MyLabel(String s, Font font){
        super(s);
        setFont(font);
    }

}
