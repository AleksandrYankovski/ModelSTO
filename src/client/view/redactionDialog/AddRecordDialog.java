package client.view.redactionDialog;

import client.controler.ClientControler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Александр on 18.09.2017.
 */
public class AddRecordDialog extends Stage {


    private ClientControler clientControler;
    private TilePane root;
    private Scene scene;
    private ArrayList<TextField> textFildCar;
    private String failure;
    private DatePicker date;
    private Spinner<LocalTime> spinner;
    private String foreignKey;
    private ArrayList<Boolean> arrayChekerInput;

    public AddRecordDialog(ClientControler clientControler, String foreignKey) throws ParseException {


        this.clientControler = clientControler;

        this.foreignKey = foreignKey;

        textFildCar = new ArrayList<>();

        arrayChekerInput = new ArrayList<>();

        root = new TilePane();


        root.setOrientation(Orientation.HORIZONTAL);

        root.getChildren().add(new Label());
        root.getChildren().add(car());
        root.getChildren().add(new Label());
        root.getChildren().add(selectTypeMalfunction());
        root.getChildren().add(selectFreeDay());
        root.getChildren().add(selectFreeTime());
        root.getChildren().add(choisePay());
        root.getChildren().add(confirmRecord());

        scene = new Scene(root, 450, 580);

        root.setStyle("-fx-background-color: orange");


        this.setScene(scene);
        this.show();

    }

    // Метод слишком большой
    private GridPane car() {


        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Марка"), 0, 0);
        TextField textFieldMark = new TextField();
        textFieldMark.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "[A-Za-z]+", checkerInput);
            if (arrayChekerInput.size() == 0)
                arrayChekerInput.add(0, checkerInput);
            else
                arrayChekerInput.set(0, checkerInput);

        });
        gridPane.add(textFieldMark, 1, 0);
        textFildCar.add(textFieldMark);

        gridPane.add(new Label("Модель"), 3, 0);
        TextField textFieldModel = new TextField();
        textFieldModel.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "[A-Za-z]+", checkerInput);
            if (arrayChekerInput.size() == 1)
                arrayChekerInput.add(1, checkerInput);
            else
                arrayChekerInput.set(1, checkerInput);
        });
        gridPane.add(textFieldModel, 4, 0);
        textFildCar.add(textFieldModel);

        gridPane.add(new Label("Год"), 0, 1);
        TextField textFieldYear = new TextField();
        textFieldYear.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "[0-9]+", checkerInput);
            if (arrayChekerInput.size() == 2)
                arrayChekerInput.add(2, checkerInput);
            else
                arrayChekerInput.set(2, checkerInput);
        });
        gridPane.add(textFieldYear, 1, 1);
        textFildCar.add(textFieldYear);

        gridPane.add(new Label("Объем"), 3, 1);
        TextField textFieldBurk = new TextField();
        textFieldBurk.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "\\d*\\.?\\d*", checkerInput);
            if (arrayChekerInput.size() == 3)
                arrayChekerInput.add(3, checkerInput);
            else
                arrayChekerInput.set(3, checkerInput);
        });
        gridPane.add(textFieldBurk, 4, 1);
        textFildCar.add(textFieldBurk);

        gridPane.add(new Label("Номер"), 0, 2);
        TextField textFieldNumber = new TextField();
        textFieldNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "[0-9]+", checkerInput);
            if (arrayChekerInput.size() == 4)
                arrayChekerInput.add(4, checkerInput);
            else
                arrayChekerInput.set(4, checkerInput);
            System.out.print(newValue);
        });
        gridPane.add(textFieldNumber, 1, 2);
        textFildCar.add(textFieldNumber);

        gridPane.add(new Label("Цвет"), 3, 2);
        TextField textFieldColor = new TextField();
        textFieldColor.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "[A-Za-z]+", checkerInput);
            if (arrayChekerInput.size() == 5)
                arrayChekerInput.add(5, checkerInput);
            else
                arrayChekerInput.set(5, checkerInput);
        });
        gridPane.add(textFieldColor, 4, 2);
        textFildCar.add(textFieldColor);

        return gridPane;
    }


    private GridPane selectTypeMalfunction() {

        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Тип поломки"), 0, 0);
        TextField textFieldFailure = new TextField();
        textFieldFailure.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean checkerInput = new Boolean(true);
            checkerInput = isCorrectInputToTextField(newValue, "[A-Za-z]+", checkerInput);
            if (arrayChekerInput.size() == 6)
                arrayChekerInput.add(6, checkerInput);
            else
                arrayChekerInput.set(6, checkerInput);
        });
        textFieldFailure.setPrefWidth(280);
        gridPane.add(textFieldFailure, 1, 0);

        failure = textFieldFailure.getText();

        return gridPane;
    }

    private GridPane selectFreeDay() throws ParseException {

        GridPane gridPane = new GridPane();

        date = new DatePicker();
        date.setPrefWidth(280);

        gridPane.add(new Label("День             "), 0, 0);
        gridPane.add(date, 1, 0);


        return gridPane;
    }

    private GridPane selectFreeTime() {
        GridPane gridPane = new GridPane();

        spinner = new Spinner(new SpinnerValueFactory() {

            {
                setConverter(new LocalTimeStringConverter(FormatStyle.MEDIUM));
            }

            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.of(8, 0));
                else {
                    LocalTime time = (LocalTime) getValue();
                    if (time.isAfter(LocalTime.of(8, 0)))
                        setValue(time.minusHours(steps));
                }
            }

            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.of(19, 0));
                else {
                    LocalTime time = (LocalTime) getValue();
                    if (time.isBefore(LocalTime.of(19, 0)))
                        setValue(time.plusHours(steps));
                }
            }
        });
        spinner.setEditable(true);

        spinner.setPrefWidth(280);

        gridPane.add(new Label("Время           "), 0, 0);
        gridPane.add(spinner, 1, 0);

        return gridPane;
    }


    private GridPane choisePay() {

        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Оплатить сейчас"), 0, 0);
        CheckBox checkBox = new CheckBox();
        gridPane.add(checkBox, 1, 0);

        return gridPane;
    }

    private Button confirmRecord() {
        Button button = new Button("подтвердить");
        button.setOnAction((ae) -> {
            try {
                for (Boolean check : arrayChekerInput)
                    if (check.equals(false)) {
                        arrayChekerInput.remove(check);
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Информация");
                        alert.setHeaderText(null);
                        alert.setContentText("Проверьте все поля ввода");
                        alert.showAndWait();
                        break;
                    }
                if (arrayChekerInput.size() == 7) {
                    clientControler.addRecord(textFildCar, failure, date.getValue(), spinner.getValue(), foreignKey);
                    this.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        button.setPrefSize(170, 20);
        return button;
    }

    private Boolean isCorrectInputToTextField(String newValue, String regex, Boolean checkerInput) {

        if (checkerInput.equals(false))
            return false;

        System.out.println(newValue);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(newValue);

        return matcher.matches();

    }
}
