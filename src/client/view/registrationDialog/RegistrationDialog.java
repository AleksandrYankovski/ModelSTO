package client.view.registrationDialog;

import client.controler.ClientControler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Александр on 18.09.2017.
 */
public class RegistrationDialog extends Stage {


    private ClientControler clientControler;
    private FlowPane root;
    private Scene scene;
    private LinkedHashMap<String, TextField> fioLinkedHashMap;
    private LinkedHashMap<String, TextField> adresLinkedHashMap;
    private HashMap<String, String> regexHashMap;
    private String photoPass;
    private Image image;
    private final String START_PASS_PHOTO = "img\\startPhotoClient.jpg";


    public RegistrationDialog(ClientControler clientControler) throws FileNotFoundException {

        this.clientControler = clientControler;

        fioLinkedHashMap = new LinkedHashMap<>();
        adresLinkedHashMap = new LinkedHashMap<>();
        regexHashMap = new HashMap<>();

        root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: orange");

        scene = new Scene(root, 450, 520);

        root.getChildren().add(photo());
        root.getChildren().add(FIO());
        root.getChildren().add(adres());
        root.getChildren().add(confirmRecord());

        this.setScene(scene);
        this.show();
    }


    private GridPane FIO() {

        GridPane gridPane = new GridPane();

        createTextFieldAndLabel(gridPane, 0, 0, "[A-Z][a-z]+|[А-Я][а-я]+", "Имя",
                fioLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 1, "[A-Z][a-z]+|[А-Я][а-я]+", "Фамилия",
                fioLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 2, "[A-Z][a-z]+|[А-Я][а-я]+", "Отчество",
                fioLinkedHashMap);

        return gridPane;
    }

    private GridPane adres() {

        GridPane gridPane = new GridPane();

        photoPass = START_PASS_PHOTO;

        createTextFieldAndLabel(gridPane, 0, 1, "([A-Z][a-z]+)|([А-Я][а-я]+)", "Страна",
                adresLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 2, "[A-Z][a-z]+|[А-Я][а-я]+", "Город",
                adresLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 3, "[A-Z][a-z]+|[А-Я][а-я]+", "Улица",
                adresLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 4, "[1-9][0-9]*([a-z]*|[а-я]*)", "Дом",
                adresLinkedHashMap);
        createTextFieldAndLabel(gridPane, 0, 5, "[1-9][0-9]*", "Квартира",
                adresLinkedHashMap);

        return gridPane;
    }

    private FlowPane photo() throws FileNotFoundException {

        photoPass = START_PASS_PHOTO;

        FlowPane flowPane = new FlowPane();

        image = new Image(new FileInputStream(photoPass));

        ImageView imageView = new ImageView(image);

        Button button = new Button("загрузить фото");

        button.setOnAction((ae) -> {

            ImageChooser chooser = new ImageChooser();

            chooser.setAvailableFormats("*.png", "*.gif", "*.jpg", "*.jpeg");

            try {
                image = chooser.openImage();

                imageView.setImage(image);
                imageView.setFitHeight(250);
                imageView.setFitWidth(250);

                photoPass = chooser.getURI();

            } catch (NullPointerException pointerExeption) {

            }

        });

        flowPane.getChildren().add(imageView);
        flowPane.getChildren().add(button);

        return flowPane;
    }

    private Button confirmRecord() {
        Button button = new Button("подтвердить");
        button.setOnAction((ae) -> {
            if (isCorectRecord()) {
                try {
                    clientControler.addClient(fioLinkedHashMap.values(), adresLinkedHashMap.values(), photoPass);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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

        for (String stringNameField : fioLinkedHashMap.keySet()) {

            Pattern pattern = Pattern.compile(regexHashMap.get(stringNameField));
            Matcher matcher = pattern.matcher(fioLinkedHashMap.get(stringNameField).getText());

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

        for (String stringNameField : adresLinkedHashMap.keySet()) {

            Pattern pattern = Pattern.compile(regexHashMap.get(stringNameField));
            Matcher matcher = pattern.matcher(adresLinkedHashMap.get(stringNameField).getText());

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