package gps.library.ui.graphic;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class MyTextField extends TextField {

    public MyTextField(Font font){
        super();
        setFont(font);
    }

    public MyTextField(String s, Font font){
        super(s);
        setFont(font);
    }
}
