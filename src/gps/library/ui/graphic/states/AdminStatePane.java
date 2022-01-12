package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import gps.library.ui.graphic.MyProgressBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AdminStatePane extends BorderPane {
    Background btnBkg = new Background(new BackgroundFill(Color.rgb(201, 201, 201), new CornerRadii(10), Insets.EMPTY));

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

    private MyLabel date;
    private MyLabel cancel;
    private MyLabel confirm;

    private GridPane outGrid;

    private GridPane topTable;
    private GridPane gridPane;
    private StackPane stackPane;
    private ScrollPane scrollPane;

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
        slider.setValue(libObs.getCapacity());

        percent = new MyLabel(String.valueOf((int) slider.getValue()) + " %", title);

        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> percent.textProperty().setValue(
                newValue.intValue() + "%"));

        date = new MyLabel("Data e hora", minor);
        cancel = new MyLabel("Cancelar", minor);
        confirm = new MyLabel("Confirmar", minor);

        /* OUTTER GRID */

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(30);
        column1.setHalignment(HPos.CENTER);
        column2.setPercentWidth(70);
        column2.setHalignment(HPos.CENTER);

        outGrid = new GridPane();
        outGrid.getColumnConstraints().addAll(column1, column2);
        GridPane.setVgrow(outGrid, Priority.NEVER);
        outGrid.setVgap(25);
        outGrid.setHgap(25);
        outGrid.setPadding(new Insets(25, 25, 25, 25));

        /** TODO DECIDIR V1 ou V2 */

        /* LEFT SIDE V1 */
        /*
        VBox titleUpdate = new VBox(10);
        titleUpdate.setAlignment(Pos.TOP_LEFT);
        titleUpdate.getChildren().add(update);
        outGrid.add(titleUpdate, 0, 2);

        /* SLIDER AND PERCENTAGE */
        /*
        VBox mid = new VBox(10);
        mid.setAlignment(Pos.CENTER);
        mid.getChildren().addAll(slider, percent);
        outGrid.add(mid, 0, 3);

        /* UPDATE BUTTON */
        /*
        outGrid.add(updateCapacityBtn, 0, 4);

        */
        /* LEFT SIDE V2 */

        VBox left = new VBox(10);
        left.setAlignment(Pos.CENTER);
        left.getChildren().addAll(update, slider, percent, updateCapacityBtn);
        outGrid.add(left, 0, 3);

        /* RIGHT SIDE */

        /* LOGOUT */
        VBox logoutBtn = new VBox(10);
        logoutBtn.setAlignment(Pos.TOP_RIGHT);
        logoutBtn.getChildren().add(logout);
        outGrid.add(logoutBtn, 1, 1);

        /* RESERVES TITLE */

        VBox reservesBox = new VBox(10);
        reservesBox.setAlignment(Pos.TOP_LEFT);
        reservesBox.getChildren().add(reserves);
        outGrid.add(reservesBox, 1, 2);


        /* Table GridPane */
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHalignment(HPos.LEFT);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setHalignment(HPos.LEFT);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setHalignment(HPos.LEFT);
        column3.setPercentWidth(50);
        column4.setPercentWidth(25);
        column5.setPercentWidth(25);

        topTable = new GridPane();
        topTable.getColumnConstraints().addAll(column3, column4, column5);
        topTable.setAlignment(Pos.TOP_LEFT);
        topTable.setHgap(10);
        topTable.setVgap(10);
        topTable.setPadding(new Insets(0, 30, 0, 30));
        topTable.add(date, 0, 0);
        topTable.add(cancel, 1, 0);
        topTable.add(confirm, 2, 0);

        topTable.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

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
        scrollPane.setFitToHeight(true);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox table = new VBox(topTable, scrollPane);
        table.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        outGrid.add(table, 1,3);

        /* UPDATE BUTTON */
        VBox updateReservesBox = new VBox(10);
        updateReservesBox.setAlignment(Pos.BOTTOM_RIGHT);
        updateReservesBox.getChildren().add(updateReservesBtn);
        outGrid.add(updateReservesBox, 1, 4);

     //   updateCapacityBtn.setOnAction(e -> libObs.updateCapacity((int) slider.getValue()));
        updateCapacityBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                libObs.updateCapacity((int) slider.getValue());
                lotationupdate();
            }
        });

        updateReservesBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                update();
            }
        });

        updateReservesBtn.setOnAction(e -> {
            libObs.getAdminReserves();
            update();
        });

        logout.setOnAction(e -> libObs.logout());

        setCenter(outGrid);
    }

    private ContextMenu createContextMenu(Label label, String[] reserve) {
        ContextMenu menu = new ContextMenu();
        MenuItem details = new MenuItem("Detalhes");
        menu.getItems().addAll(details);
        details.setOnAction(e -> {
            Alert msgBox = new Alert(Alert.AlertType.INFORMATION);
            msgBox.setTitle(reserve[0]);
            msgBox.setHeaderText("Reserva efetuada para o dia: " + reserve[0] + "\nGabinete: " + reserve[3] +
                    "\nEstudantes: " + reserve[2]);
            msgBox.showAndWait();
        });
        return menu;
    }


    private void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void lotationupdate()
    {
        boolean worked = libObs.getItworked();

        if (!worked)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.setTitle("Alerta");
            alert.setHeaderText("Erro ao fazer a conexão à base de dados!\nMuito em breve deverá estar resolvido. Se não estiver contacte o administrador do sistema!");

            Optional<ButtonType> result = alert.showAndWait();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "");
            alert.setTitle("Confirmação");
            alert.setHeaderText("Lotação atualizado com sucesso!");

            Optional<ButtonType> result = alert.showAndWait();

        }
    }


    private void update(){
        setVisible(libObs.getAtualState() == States.ADMIN);
        gridPane.getChildren().clear();
        /** TODO VER ESTA CHECKBOX OU SE É PARA POR MESMO UMA CHECKBOX*/
        URL url = getClass().getClassLoader().getResource("gps/library/resources/images/checkbox.png");
        Image icon = new Image(String.valueOf(url));

        URL urlempty = getClass().getClassLoader().getResource("gps/library/resources/images/checkboxempty.png");
        Image iconempty = new Image(String.valueOf(urlempty));


        int id = 0;
        HashMap<Integer, String[]> reserves = libObs.getAdminReserves();
        for(Integer i : reserves.keySet()) {
            ImageView view = new ImageView(icon);
            ImageView empty = new ImageView(iconempty);

            StackPane fillLeft = new StackPane();
            StackPane fillMid = new StackPane();
            StackPane fillRight = new StackPane();
            MyButton cancel = new MyButton("X");
            MyButton confirm = new MyButton();

            cancel.setBackground(null);
            confirm.setBackground(null);
            confirm.setGraphic(view);

            MyLabel reserve = new MyLabel(reserves.get(i)[0], minor);
            reserve.setContextMenu(createContextMenu(reserve, reserves.get(i)));

            fillLeft.getChildren().add(reserve);
            fillMid.getChildren().add(cancel);
            if (reserves.get(i)[1].equals("1")) {
                confirm.setGraphic(view);
                confirm.setDisable(true);
            } else {
                confirm.setGraphic(empty);
            }

            fillRight.getChildren().add(confirm);
            if (id % 2 == 0) {
                fillLeft.setBackground(btnBkg);
                fillMid.setBackground(btnBkg);
                fillRight.setBackground(btnBkg);
            }

            gridPane.add(fillLeft, 0, id + 1);
            gridPane.add(fillMid, 1, id + 1);
            gridPane.add(fillRight, 2, id + 1);
            GridPane.setFillWidth(fillLeft, true);
            GridPane.setFillHeight(fillLeft, true);

            cancel.setOnAction(e -> {
                ButtonType yes = new ButtonType("Sim", ButtonBar.ButtonData.YES);
                ButtonType no = new ButtonType("Não", ButtonBar.ButtonData.NO);
                Alert alert = new Alert(Alert.AlertType.ERROR, "", yes, no);
                alert.setTitle("Alerta");
                alert.setHeaderText("Deseja mesmo cancelar a reserva?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == yes) {
                        libObs.cancelReserve(i);
                    }
                }
            });
            confirm.setOnAction(e -> {
                ButtonType yes = new ButtonType("Sim", ButtonBar.ButtonData.YES);
                ButtonType no = new ButtonType("Não", ButtonBar.ButtonData.NO);
                Alert alert = new Alert(Alert.AlertType.ERROR, "", yes, no);
                alert.setTitle("Alerta");
                alert.setHeaderText("Deseja mesmo confirmar a reserva?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == yes) {
                        libObs.confirmReserve(i);
                    }
                }
            });
            id++;
        }
    }


}
