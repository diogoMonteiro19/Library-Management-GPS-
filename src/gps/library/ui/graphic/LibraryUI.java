package gps.library.ui.graphic;

import gps.library.logic.LibraryObservable;
import gps.library.logic.states.ReservationState;
import gps.library.ui.graphic.states.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class LibraryUI extends BorderPane {

    private LibraryObservable libObs;

    private InitialStatePane initialStatePane;
    private LoginStatePane loginStatePane;
    private UserStatePane userStatePane;
    private AdminStatePane adminStatePane;
    private ReservationStatePane reservationStatePane;

    public LibraryUI(LibraryObservable libObs){
        this.libObs = libObs;
        createView();
        createMenus();
        registerObserver();
        update();
    }

    private void createView(){
        initialStatePane = new InitialStatePane(libObs);
        loginStatePane = new LoginStatePane(libObs);
        userStatePane = new UserStatePane(libObs);
        adminStatePane = new AdminStatePane(libObs);
        reservationStatePane = new ReservationStatePane(libObs);
    }

    private void createMenus(){
        MenuBar menuBar = new MenuBar();
        setTop(menuBar);

        Menu file = new Menu("_File");
        MenuItem debug = new MenuItem("debug");

        file.getItems().add(debug);

        debug.setOnAction(e -> libObs.forceUpdate());

        menuBar.getMenus().add(file);
    }

    private void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void update(){
        System.out.println(libObs.getAtualState());
        switch(libObs.getAtualState()){
            case INITIAL_LOGOUT, INITIAL_LOGIN -> setCenter(initialStatePane);
            case LOGIN_REGISTER -> setCenter(loginStatePane);
            case USER -> setCenter(userStatePane);
            case ADMIN -> setCenter(adminStatePane);
            case RESERVATION -> setCenter(reservationStatePane);
        }
    }
}
