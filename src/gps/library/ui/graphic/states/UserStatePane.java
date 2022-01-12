package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class UserStatePane extends BorderPane {
    Background btnBkg = new Background(new BackgroundFill(Color.rgb(201, 201, 201), new CornerRadii(10), Insets.EMPTY));

    private LibraryObservable libObs;

    Font title = new Font(25);
    Font minor = new Font(18);


    MyButton logout;
    MyLabel titleTxt;
    MyButton capacity;
    MyButton reserveOffice;
    MyLabel date;
    MyLabel office;
    MyLabel cancel;

    ScrollPane scrollPane;
    GridPane topGrid;
    GridPane botGrid;
    GridPane topTable;
    GridPane gridPane;
    StackPane stackPane;

    public UserStatePane(LibraryObservable libObs){
        this.libObs = libObs;
        createWindow();
        registerObserver();
        update();
    }

    private void createWindow(){
        titleTxt = new MyLabel("Reservas", title);
        logout = new MyButton("Terminar Sessão");

        capacity = new MyButton("Visualizar Capacidade");
        reserveOffice = new MyButton("Reservar Gabinete");

        date = new MyLabel("Data", minor);
        office = new MyLabel("Gabinete", minor);
        cancel = new MyLabel("Cancelar Reserva", minor);

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.CENTER);
        column2.setPercentWidth(50);
        column2.setHalignment(HPos.CENTER);

        topGrid = new GridPane();
        topGrid.getColumnConstraints().addAll(column1, column2);
        topGrid.add(logout, 1, 1);
        topGrid.add(titleTxt, 0, 2);
        topGrid.setPadding(new Insets(25, 25, 25, 25));


        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHalignment(HPos.CENTER);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setHalignment(HPos.CENTER);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setHalignment(HPos.CENTER);
        column3.setPercentWidth(50);
        column4.setPercentWidth(25);
        column5.setPercentWidth(25);

        topTable = new GridPane();
        topTable.getColumnConstraints().addAll(column3, column4, column5);
        topTable.add(date, 0, 0);
        topTable.add(office, 1, 0);
        topTable.add(cancel, 2, 0);

        VBox top = new VBox(10, topGrid, topTable);

        gridPane = new GridPane();
        gridPane.getColumnConstraints().addAll(column3, column4, column5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        stackPane = new StackPane(gridPane);
        stackPane.setAlignment(Pos.CENTER);
        scrollPane = new ScrollPane();
        scrollPane.setContent(stackPane);
        scrollPane.vvalueProperty().bind(stackPane.heightProperty());
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        botGrid = new GridPane();
        botGrid.getColumnConstraints().addAll(column1, column2);
        botGrid.setAlignment(Pos.CENTER);
        botGrid.setPadding(new Insets(25, 25, 25, 25));

        botGrid.add(capacity, 0, 0);
        botGrid.add(reserveOffice, 1, 0);

        setTop(top);
        setCenter(scrollPane);
        setBottom(botGrid);

        logout.setOnAction(e -> {
            libObs.capacity();
            libObs.logout();
        });

        capacity.setOnAction(e -> libObs.capacity());

        reserveOffice.setOnAction(e -> libObs.reserveOffice());
    }

    private void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void update(){
        setVisible(libObs.getAtualState() == States.USER);
        gridPane.getChildren().clear();
        int id = 0;
        HashMap<Integer, String[]> reserves = libObs.getReserves();
        for(Integer i : reserves.keySet()) {
            System.out.println("ID:" + i);
            StackPane fillLeft = new StackPane();
            StackPane fillMid = new StackPane();
            StackPane fillRight = new StackPane();

            MyLabel office = new MyLabel(reserves.get(i)[2], minor);

            MyButton cancel = new MyButton("X");
            cancel.setBackground(null);
            MyLabel reserve = new MyLabel(reserves.get(i)[0], minor);


            fillLeft.getChildren().add(reserve);
            fillMid.getChildren().add(office);
            fillRight.getChildren().add(cancel);

            System.out.println(id);
            if(id % 2 == 0) {
                fillLeft.setBackground(btnBkg);
                fillMid.setBackground(btnBkg);
                fillRight.setBackground(btnBkg);
            }

            gridPane.add(fillLeft, 0, id+1);
            gridPane.add(fillMid, 1, id+1);
            gridPane.add(fillRight, 2, id+1);
            GridPane.setFillWidth(fillLeft, true);
            GridPane.setFillHeight(fillLeft, true);

            cancel.setOnAction(e -> {
                ButtonType yes = new ButtonType("Sim",  ButtonBar.ButtonData.YES);
                ButtonType no = new ButtonType("Não", ButtonBar.ButtonData.NO);
                Alert alert = new Alert(Alert.AlertType.ERROR, "", yes, no);
                alert.setTitle("Alerta");
                alert.setHeaderText("Deseja mesmo cancelar a reserva?");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    if(result.get() == yes){
                        libObs.cancelReserve(i);
                    }
                }
            });
            id++;
        }

        /** TODO DEBATER ESTA PARTE DE IR BUSCAR AS PENALIZAÇÕES */
        if(libObs.getAtualState() == States.USER) {
            int penalties = libObs.getPenalties();
            if (penalties >= 1) {
                ButtonType ok = new ButtonType("Sair", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR, "", ok);
                alert.setTitle("Alerta");
                alert.setHeaderText("Cancelou demasiadas reservas. Durante " + penalties + " horas " +
                        "não consegue efetuar reservas.");
                alert.showAndWait();
                reserveOffice.setDisable(true);
            } else {
                reserveOffice.setDisable(false);
            }
        }
    }
}
