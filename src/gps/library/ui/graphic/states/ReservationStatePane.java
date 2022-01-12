package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import gps.library.ui.graphic.MyTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationStatePane extends BorderPane {
    Background btnBkg = new Background(new BackgroundFill(Color.rgb(201, 201, 201), new CornerRadii(10), Insets.EMPTY));

    private LibraryObservable libObs;

    Font title = new Font(25);
    Font minor = new Font(18);

    private MyLabel date;
    private MyButton cancel;
    private MyButton next;

    /* HOME WINDOW */
    HBox buttons;
    HBox dates;
    DatePicker datePicker;
    VBox content;

    /* HOURS WINDOW */
    MyLabel lbl;
    ScrollPane scrollPane;
    MyButton cancelHours;
    MyButton nextHours;
    VBox hours;
    VBox contentHours;

    /* MEMBERS WINDOW */
    MyLabel description;
    List<TextField> students;
    MyButton add;
    MyButton remove;
    MyButton cancelMembers;
    MyButton nextMembers;
    MyLabel error;
    VBox contentMembers;

    public ReservationStatePane(LibraryObservable libObs){
        this.libObs = libObs;
        createWindow();
        registerObservers();
        update();
    }

    private void createWindow(){
        /* CRIAR A JANELA */
        cancel = new MyButton("Cancelar");
        next = new MyButton("Seguinte");

        buttons = new HBox(10, cancel, next);
        buttons.setAlignment(Pos.CENTER);

        date = new MyLabel("Data: ", title);

        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.getEditor().setFont(minor);

        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()+2);
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});

        dates = new HBox(10, date, datePicker);
        dates.setAlignment(Pos.CENTER);

        content = new VBox(10, dates, buttons);

        content.setAlignment(Pos.CENTER);

        setCenter(content);

        /** TODO DECIDIR O QUE MANDAR AQUI */

//        next.setOnAction(e -> libObs.selectedDay(datePicker.getValue().getClass()));
        next.setOnAction(e -> {
            if(libObs.selectedDay(datePicker.getEditor().getText())){
                updateHours();
            }
            else{
                ButtonType cancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType tryAgain = new ButtonType("Tentar de novo", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR, "", tryAgain, cancel);
                alert.setTitle("Alerta");
                alert.setHeaderText("Para esse dia já não existem gabinetes livres.");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    if(result.get() == cancel){
                        libObs.backToUser();
                    }
                }
            }
        });
        cancel.setOnAction(e -> libObs.backToUser());
    }

    private void registerObservers(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void update(){
        setVisible(libObs.getAtualState() == States.RESERVATION);
        getChildren().clear();

        createWindow();
    }

    private void updateHours(){

        List<Integer> available = (List<Integer>) libObs.getHours();

        final String[] selected = new String[1];
        getChildren().clear();

        final int[] office_id = {0};
        /* TOP */

        lbl = new MyLabel("Para esse dia existem as seguintes" +
                " horas disponíveis: ", minor);

        ToggleGroup tg = new ToggleGroup();
        /* HOURS */
        hours = new VBox(10);
        for(int i = 9; i <= 22; i++){
            final int finalI = i;
            HBox line = new HBox(10);
            RadioButton rb = new RadioButton(new String( i + ":00"));
//            CheckBox cb = new CheckBox(new String( i + ":00"));
            rb.setDisable(true);
            if(available.get(i-9) <= 4){
                rb.setDisable(false);
            }

            rb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasSelected, Boolean isSelected) {
                    if(isSelected){
                        selected[0] = rb.getText();
                        office_id[0] = available.get(finalI-9);
                        nextHours.setDisable(false);
                    }
//                    for(int i = 0; i < selected[0].size(); i++){
//                        System.out.println(selected[0].get(i));
//                    }
                }
            });
            rb.setToggleGroup(tg);
            line.getChildren().add(rb);
            line.setAlignment(Pos.CENTER);
            hours.getChildren().addAll(line);
        }
        hours.setAlignment(Pos.CENTER);

        /* BOT */

        HBox bot = new HBox(25);
        cancelHours = new MyButton("Cancelar");
        nextHours = new MyButton("Seguinte");
        nextHours.setDisable(true);
        bot.setAlignment(Pos.CENTER);
        bot.getChildren().addAll(cancelHours, nextHours);

        /* CREATE VIEW */

        contentHours = new VBox(25);
        contentHours.setAlignment(Pos.CENTER);
        contentHours.getChildren().addAll(lbl, hours, bot);
        setCenter(contentHours);
        nextHours.setOnAction(e -> {
            if(libObs.selectedHours(selected[0], office_id[0]+1)) {
                updateMembers();
            }
        });

        cancelHours.setOnAction(e -> libObs.backToUser());
    }

    private void updateMembers(){

        /** TODO regex nos textfield */

        getChildren().clear();
        AtomicInteger nStudents = new AtomicInteger();
        students = new ArrayList<>(3);
        /* TOP */
        HBox top = new HBox(10);
        description = new MyLabel("Associar pessoa pelo numero de aluno: ", minor);
        error = new MyLabel("Número de estudante inválido.", new Font(10));
        error.setTextFill(Color.RED);
        add = new MyButton("+");
        top.setAlignment(Pos.CENTER);
        top.getChildren().addAll(description, add);

        /* TEXTS */
        HBox mid = new HBox(10);
        MyTextField student = new MyTextField(minor);
        students.add(student);
        remove = new MyButton("X");
        mid.setAlignment(Pos.CENTER);
        mid.getChildren().addAll(student, remove);

        VBox allStudents = new VBox(10);
        allStudents.setAlignment(Pos.CENTER);
        allStudents.getChildren().addAll(mid, error);

        remove.setOnAction(e -> {
            allStudents.getChildren().removeAll(mid, error);
            nStudents.getAndIncrement();
        });

        student.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));

        student.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (student.getText().length() >= 10) {
                        student.setText(student.getText().substring(0, 10));
                        error.setVisible(false);
//                        registerBtn.setDisable(false);
                    }
                    if(student.getText().length() < 10){
//                        registerBtn.setDisable(true);
                    }
                }
            }
        });

        /* BUTTONS */
        HBox bot = new HBox(25);
        cancelMembers = new MyButton("Cancelar");
        nextMembers = new MyButton("Seguinte");
        bot.setAlignment(Pos.CENTER);
        bot.getChildren().addAll(cancelMembers, nextMembers);

        contentMembers = new VBox(10);
        contentMembers.setAlignment(Pos.CENTER);
        contentMembers.getChildren().addAll(top, allStudents, bot);

        setCenter(contentMembers);

        add.setOnAction(e -> {
            if(nStudents.get() < 2){
                HBox newMid = new HBox(10);
                MyTextField newStudent = new MyTextField(minor);
                students.add(newStudent);
                remove = new MyButton("X");
                newMid.setAlignment(Pos.CENTER);
                MyLabel error = new MyLabel("Número de estudante inválido.", new Font(10));
                error.setTextFill(Color.RED);
                newMid.getChildren().addAll(newStudent, remove);
                allStudents.getChildren().addAll(newMid, error);
                nStudents.getAndIncrement();
                remove.setOnAction(event -> {
                    newMid.setVisible(false);
                    allStudents.getChildren().removeAll(newMid, error);
                    nStudents.getAndDecrement();
                });
                newStudent.lengthProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                                        Number oldValue, Number newValue) {
                        if (newValue.intValue() > oldValue.intValue()) {
                            if (newStudent.getText().length() >= 10) {
                                newStudent.setText(newStudent.getText().substring(0, 10));
                                error.setVisible(false);
//                        registerBtn.setDisable(false);
                            }
                            if(newStudent.getText().length() < 10){
//                        registerBtn.setDisable(true);
                            }
                        }
                    }
                });
            }else{
                Popup popup = new Popup();
                MyLabel text = new MyLabel("Máximo pessoas atingido!", minor);
                text.setBackground(btnBkg);
                text.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
                popup.getContent().addAll(text);
                Bounds bounds = add.localToScreen(add.getBoundsInLocal());
                popup.setX(bounds.getCenterX()+(bounds.getWidth()/2));
                popup.setY(bounds.getCenterY()-bounds.getHeight());
                popup.setAutoHide(true);
                popup.show(this.getScene().getWindow());
            }
        });

        nextMembers.setOnAction(e -> {
            /** TODO DECIDIR O QUE MANDAR AQUI */
            List<Integer> studentsNumber = new ArrayList<>();
            for(TextField s : students){
                if(!s.getText().isBlank() || !s.getText().isEmpty()){
                    studentsNumber.add(Integer.parseInt(s.getText()));
                }
            }
            if(libObs.newReserve(studentsNumber)){
                ButtonType cont = new ButtonType("Continuar");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", cont);
                alert.setTitle("Alerta");
                alert.setHeaderText("Reserva confirmada.");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    if(result.get() == cont){
                        libObs.backToUser();
                    }
                }
            }
            boolean worked = libObs.getItworked();
            if (!worked)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "");
                alert.setTitle("Alerta");
                alert.setHeaderText("Não existe nenhum estudante com algum desses números.");

                Optional<ButtonType> result = alert.showAndWait();

            }
        });

        cancelMembers.setOnAction(e -> libObs.backToUser());
    }
}
