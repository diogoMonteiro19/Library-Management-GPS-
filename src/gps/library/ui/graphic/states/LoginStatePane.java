package gps.library.ui.graphic.states;

import gps.library.logic.LibraryObservable;
import gps.library.logic.States;
import gps.library.ui.graphic.MyButton;
import gps.library.ui.graphic.MyLabel;
import gps.library.ui.graphic.MyPasswordField;
import gps.library.ui.graphic.MyTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class LoginStatePane extends BorderPane {
    private LibraryObservable libObs;

    Background btnBkg = new Background(new BackgroundFill(Color.rgb(201, 201, 201), new CornerRadii(10), Insets.EMPTY));
    Font title = new Font(25);
    Font minor = new Font(18);

    GridPane out;
    GridPane left;
    GridPane right;

    MyLabel loginLbl;
    MyLabel mailLbl;
    MyTextField mailFld;
    MyLabel passwordLbl;
    MyPasswordField passwordFld;
    MyLabel registerLbl;
    MyLabel numberLbl;
    MyTextField numberFld;
    MyLabel numberFldError;
    MyLabel mailRegisterLbl;
    MyTextField mailRegisterFld;
    MyLabel passwordRegisterLbl;
    MyPasswordField passwordRegisterFld;
    MyLabel confPasswordRegisterLbl;
    MyPasswordField confPasswordRegisterFld;
    MyButton loginBtn;
    MyButton registerBtn;
    MyButton back;

    public LoginStatePane(LibraryObservable libObs){
        this.libObs = libObs;
        createWindow();
        registerObserver();
        update();
    }

    private void createWindow(){
        loginLbl = new MyLabel("Login", title);

        mailLbl = new MyLabel("Email: ", minor);
        mailFld = new MyTextField(minor);
        passwordLbl = new MyLabel("Password:", minor);
        passwordFld = new MyPasswordField(minor);

        registerLbl = new MyLabel("Register", title);

        numberLbl = new MyLabel("Numero de aluno:", minor);
        numberFld = new MyTextField(minor);
        numberFldError = new MyLabel("Número de estudante inválido.", new Font(10));
        numberFldError.setTextFill(Color.RED);
        numberFld.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));

        numberFld.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (numberFld.getText().length() >= 10) {
                        numberFld.setText(numberFld.getText().substring(0, 10));
                        numberFldError.setVisible(false);
                        registerBtn.setDisable(false);
                    }
                    if(numberFld.getText().length() < 10){
                        System.out.println("HERE");
                        numberFldError.setVisible(true);
                        registerBtn.setDisable(true);
                    }
                }
            }
        });

//        numberFld.setOnKeyPressed(e -> {
//            case KEY_EVENT
//        });
        mailRegisterLbl = new MyLabel("Email:", minor);
        mailRegisterFld = new MyTextField(minor);
        passwordRegisterLbl = new MyLabel("Password:", minor);
        passwordRegisterFld = new MyPasswordField(minor);
        confPasswordRegisterLbl = new MyLabel("Confirmação Password:", minor);
        confPasswordRegisterFld = new MyPasswordField(minor);

        loginBtn = new MyButton("Login");
        registerBtn = new MyButton("Register");
        registerBtn.setDisable(true);

        back = new MyButton("Voltar");

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column2.setPercentWidth(50);
        out = new GridPane();
        out.getColumnConstraints().addAll(column1, column2);

        out.setGridLinesVisible(true);

        // left
        left = new GridPane();
        left.getColumnConstraints().addAll(column1, column2);

        left.setPadding(new Insets(25, 25, 25, 25));
        left.setHgap(25);
        left.setVgap(25);

        left.add(loginLbl, 0, 0);

        left.add(mailLbl, 0, 1);
        left.add(mailFld, 1, 1);

        left.add(passwordLbl, 0, 2);
        left.add(passwordFld, 1, 2);
        left.add(loginBtn, 1, 5);

        left.add(back, 0, 5);

        // right
        right = new GridPane();
        right.getColumnConstraints().addAll(column1, column2);

        right.setPadding(new Insets(25, 25, 25, 25));
        right.setVgap(25);
        right.setHgap(25);

        right.add(registerLbl, 0, 0);

        right.add(numberLbl, 0, 1);
        right.add(numberFld, 1, 1);

        right.add(mailRegisterLbl, 0, 2);
        right.add(mailRegisterFld, 1, 2);

        right.add(passwordRegisterLbl, 0, 3);
        right.add(passwordRegisterFld, 1, 3);

        right.add(confPasswordRegisterLbl, 0, 4);
        right.add(confPasswordRegisterFld, 1, 4);

        right.add(numberFldError, 1, 5);
        right.add(registerBtn, 1, 6);

        out.add(left, 0, 0);
        out.add(right, 1, 0);
        out.setAlignment(Pos.CENTER);
        setCenter(out);

        // register buttons
        loginBtn.setOnAction(e -> {
            libObs.login(mailFld.getText(), passwordFld.getText());
            confirmUpdate();
        });

        registerBtn.setOnAction(e -> {
            libObs.register(numberFld.getText(), mailRegisterFld.getText(), passwordRegisterFld.getText(), confPasswordRegisterFld.getText());
            confirmUpdate();
        });

        back.setOnAction(e -> libObs.capacity());
    }

    private void registerObserver(){
        libObs.addProperty("UPDATE", e -> update());
    }

    private void confirmUpdate(){
        boolean worked = libObs.getItworked();

        if (!worked){
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.setTitle("Alerta");
            alert.setHeaderText("Erro - Confirme novamente os campos e tente novamente.");

            Optional<ButtonType> result = alert.showAndWait();

        }
    }

    private void update(){
        setVisible(libObs.getAtualState() == States.LOGIN_REGISTER);
    }
}
