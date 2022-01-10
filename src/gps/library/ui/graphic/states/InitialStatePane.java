package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import gps.library.ui.graphic.MyProgressBar;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class InitialStatePane extends BorderPane {
    private LibraryObservable libObs;

    Font title = new Font(25);
    Font minor = new Font(18);

    MyLabel welcomeTxt;
    MyLabel noteTxt;
    MyLabel percent;
    MyButton logout;
    MyButton login;
    MyButton reserves;
    MyProgressBar progress;

    VBox center;
    VBox top, bottom;

    public InitialStatePane(LibraryObservable libObs){
        this.libObs = libObs;
        createWindow();
        registerObserver();
        update();
    }

    private void createWindow(){
        welcomeTxt = new MyLabel("Bem-vindo à biblioteca do ISEC!", title);
        noteTxt = new MyLabel("Estimativa da lotação da biblioteca:", minor);
        progress = new MyProgressBar();
        percent = new MyLabel(title);

        logout = new MyButton("Terminar sessão");
        login = new MyButton("Login/Registo");
        reserves = new MyButton("Reservas");

        setPadding(new Insets(25, 25, 25, 25));

        // containers
        center = new VBox(30, welcomeTxt, noteTxt, progress, percent);
        center.setAlignment(Pos.CENTER);

        top = new VBox(30, logout);
        top.setAlignment(Pos.BOTTOM_RIGHT);

        bottom = new VBox(30, reserves, login);
        bottom.setAlignment(Pos.TOP_RIGHT);

        setTop(top);
        setCenter(center);
        setBottom(bottom);

        // definir ações dos buttons
        logout.setOnAction(e -> libObs.logout()); // mudar para o estado inicial logged out

        login.setOnAction(e -> libObs.login()); // ir para o estado de login

        reserves.setOnAction(e -> libObs.reserves()); // ir para o estado do painel de usuario

    }

    public void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    public void update(){
        int capacity = libObs.getCapacity();
        progress = new MyProgressBar((double) libObs.getCapacity() / 100);

        if (capacity == -1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.setTitle("Alerta");
            alert.setHeaderText("Erro ao fazer a conexão à base de dados!\nMuito em breve deverá estar resolvido. Se não estiver contacte o administrador do sistema!");

            Optional<ButtonType> result = alert.showAndWait();
            center = new VBox(30, welcomeTxt, noteTxt, progress);

        }
        else{
            center = new VBox(30, welcomeTxt, noteTxt, progress, percent);

        }


        progress.prefWidthProperty().bind(center.widthProperty().subtract(200));
        progress.setPrefHeight(25);
        center.setAlignment(Pos.CENTER);
        setVisible(libObs.getAtualState() == States.INITIAL_LOGIN || libObs.getAtualState() == States.INITIAL_LOGOUT);
        if(libObs.getAtualState() == States.INITIAL_LOGIN){
            logout.setVisible(true);
            bottom = new VBox(30, reserves);
        }
        else{
            logout.setVisible(false);
            bottom = new VBox(30, login);
        }
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        setCenter(center);
        setBottom(bottom);
        System.out.println(center.getWidth());

        percent.setText(String.valueOf(libObs.getCapacity())+ " %");
    }
}
