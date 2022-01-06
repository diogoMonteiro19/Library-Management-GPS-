package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class AdminStatePane extends BorderPane {

    private LibraryObservable libObs;

    Font title = new Font(25);
    Font minor = new Font(18);

    private MyLabel update;
    private MyLabel reserves;
    private MyLabel percent;
    private Slider slider;
    private MyButton logout;
    private MyButton updateReservesBtn;
    private MyButton updateCapacityBtn;

    private GridPane outGrid;
    private GridPane reservesGrid;

    public AdminStatePane(LibraryObservable libObs){
        this.libObs = libObs;
        createWindow();
        registerObserver();
        update();
    }

    private void createWindow(){
        logout = new MyButton("Terminar Sessão");
        update = new MyLabel("Atualizar Lotação", title);
        reserves = new MyLabel("Reservas", title);

        updateCapacityBtn = new MyButton("Atualizar");

        updateReservesBtn = new MyButton("Atualizar");

        slider = new Slider();

        percent = new MyLabel(String.valueOf(slider.getValue()), title);

        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> percent.textProperty().setValue(
                newValue.intValue() + "%"));

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.CENTER);
        column2.setPercentWidth(50);
        column2.setHalignment(HPos.CENTER);

        outGrid = new GridPane();
        outGrid.getColumnConstraints().addAll(column1, column2);

        outGrid.setVgap(50);
        outGrid.setPadding(new Insets(25, 25, 25, 25));

        outGrid.add(logout, 1, 1);
        outGrid.setGridLinesVisible(true);

        VBox titleUpdate = new VBox(10);
        titleUpdate.setAlignment(Pos.TOP_LEFT);
        titleUpdate.getChildren().add(update);
        outGrid.add(titleUpdate, 0, 2);
        outGrid.add(reserves, 1, 2);

        outGrid.add(slider, 0, 3);
        outGrid.add(percent, 0, 4);

        outGrid.add(updateCapacityBtn, 0, 6);
        outGrid.add(updateReservesBtn, 1, 6);

        setCenter(outGrid);
    }

    private void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void update(){
        setVisible(libObs.getAtualState() == States.ADMIN);


    }
}
