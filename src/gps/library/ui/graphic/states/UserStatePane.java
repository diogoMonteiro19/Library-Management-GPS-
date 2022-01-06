package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;


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

        topTable = new GridPane();
        topTable.getColumnConstraints().addAll(column1, column2);
        topTable.add(date, 0, 0);
        topTable.add(cancel, 1, 0);

        VBox top = new VBox(10, topGrid, topTable);

        gridPane = new GridPane();
        gridPane.getColumnConstraints().addAll(column1, column2);
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

        logout.setOnAction(e -> libObs.logout());

        capacity.setOnAction(e -> libObs.capacity());

        // TODO a partir daqui
//        reserveOffice.setOnAction(e -> libObs.reserveOffice());
    }

    private void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void update(){
        setVisible(libObs.getAtualState() == States.USER);
        List<?> reserves = libObs.getReserves();
        for(int i = 0; i < reserves.size(); i++){
            final int id = i;
            StackPane fillLeft = new StackPane();
            StackPane fillRight = new StackPane();
            MyButton cancel = new MyButton("X");
            cancel.setBackground(null);
            MyLabel reserve = new MyLabel(reserves.get(i).toString(), minor);

            fillLeft.getChildren().add(reserve);
            fillRight.getChildren().add(cancel);

            if(i%2 == 0) {
                fillLeft.setBackground(btnBkg);
                fillRight.setBackground(btnBkg);
            }
            gridPane.add(fillLeft, 0, i+1);
            gridPane.add(fillRight, 1, i+1);
            GridPane.setFillWidth(fillLeft, true);
            GridPane.setFillHeight(fillLeft, true);
            cancel.setOnAction(e -> libObs.cancelReserve(id));
        }
    }
}
