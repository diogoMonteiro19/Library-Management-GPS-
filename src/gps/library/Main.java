package gps.library;

import gps.library.logic.Library;
import gps.library.logic.LibraryObservable;
import gps.library.ui.graphic.LibraryUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Library lib = new Library();
        LibraryObservable libObs = new LibraryObservable(lib);
        LibraryUI libUI = new LibraryUI(libObs);

        InputStream url = getClass().getResourceAsStream("resources/images/icon.png");
        Image icon = new Image(url);

        Scene scene = new Scene(libUI, 800, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Library Management");
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.getIcons().add(icon);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
