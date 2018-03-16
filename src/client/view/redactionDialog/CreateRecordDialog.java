package client.view.redactionDialog;

import client.controler.ClientControler;
import client.model.Failure;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Александр on 18.09.2017.
 */
public class CreateRecordDialog extends Stage {


    private ClientControler clientControler;
    private TilePane root;
    private Scene scene;
    private DatePicker date;
    private Spinner<LocalTime> spinner;
    private CheckBox checkBoxPaid;
    private Integer foreignKey;
    private ComboxFailure comboxFailure;
    private LinkedHashMap<String, TextField> carLinkedHashMap;
    private HashMap<String, String> regexHashMap;


    public CreateRecordDialog(ClientControler clientControler, Integer foreignKey) throws ParseException {


        this.clientControler = clientControler;

        this.foreignKey = foreignKey;

        carLinkedHashMap = new LinkedHashMap<>();
        regexHashMap = new HashMap<>();

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

    private GridPane car() {

        GridPane gridPane = new GridPane();

        createTextFieldAndLabel(gridPane, 0, 1, "([A-Z][a-z]+)|([А-Я][а-я]+)", "Марка",
                carLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 2, "([A-Z][a-z]+)|([А-Я][а-я]+)", "Модель",
                carLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 3, "(199[0-9])|(20(0|1)[0-9])", "Год",
                carLinkedHashMap);

        createTextFieldAndLabel(gridPane, 2, 1, "[1-9].?[0-9]", "Объем",
                carLinkedHashMap);
        createTextFieldAndLabel(gridPane, 2, 2, "([0-9]+-?){1,}", "Номер",
                carLinkedHashMap);
        createTextFieldAndLabel(gridPane, 2, 3, "([A-Z][a-z]+)|([А-Я][а-я]+)", "Цвет",
                carLinkedHashMap);

        return gridPane;
    }


    private GridPane selectTypeMalfunction() {

        GridPane gridPane = new GridPane();

        comboxFailure = new ComboxFailure();

        comboxFailure.setPrefWidth(280);

        gridPane.add(new Label("Тип поломки "), 0, 0);
        gridPane.add(comboxFailure, 1, 0);

        return gridPane;
    }

    private GridPane selectFreeDay() throws ParseException {

        GridPane gridPane = new GridPane();

        date = new DatePicker();
        date.setPrefWidth(280);

        gridPane.add(new Label("День              "), 0, 0);
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

        gridPane.add(new Label("Время            "), 0, 0);
        gridPane.add(spinner, 1, 0);

        return gridPane;
    }


    private GridPane choisePay() {

        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Оплатить сейчас"), 0, 0);
        checkBoxPaid = new CheckBox();
        gridPane.add(checkBoxPaid, 1, 0);


        return gridPane;
    }

    private Button confirmRecord() {

        Button button = new Button("подтвердить");
        button.setOnAction((ae) -> {
            if (isCorectRecord()) {
                try {
                    clientControler.addRecord(carLinkedHashMap.values(), comboxFailure.getSelectionModel().getSelectedItem(),
                            date.getValue(), spinner.getValue(),checkBoxPaid.isSelected(), foreignKey);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                this.close();
            }




        });
        button.setPrefSize(170, 20);
        return button;

    }


    private void createTextFieldAndLabel(GridPane gridPane, Integer columnIndex, Integer rowIndex, String regex, String nameField,
                                         LinkedHashMap linkedHashMap) {

        gridPane.add(new Label(nameField), columnIndex, rowIndex);
        TextField textField = new TextField();
        gridPane.add(textField, columnIndex + 1, rowIndex);
        linkedHashMap.put(nameField, textField);
        regexHashMap.put(nameField, regex);
    }

    private Boolean isCorectRecord() {

        for (String stringNameField : carLinkedHashMap.keySet()) {

            Pattern pattern = Pattern.compile(regexHashMap.get(stringNameField));
            Matcher matcher = pattern.matcher(carLinkedHashMap.get(stringNameField).getText());

            if (!matcher.matches()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(stringNameField + " must be type: "
                        + regexHashMap.get(stringNameField));
                alert.showAndWait();
                return false;
            }
        }

        return true;
    }

}
