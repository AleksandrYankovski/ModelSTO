package client.view.InstractionDialog;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InstractionDialog extends Stage {


    private FlowPane root;
    private Scene scene;
    private Label instractionLabel;



    public InstractionDialog() throws IOException {

        root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: orange");

        scene = new Scene(root, 750, 240);

        createInstractionLabel("Инструцкия по эксплуатации!", 28, Color.BLACK);

        createInstractionForDepartment("registrationInstraction.txt", 15, Color.BLACK);
        createInstractionForDepartment("redactionInstraction.txt", 15, Color.BLACK);

        this.setScene(scene);
        this.show();


    }


    private void createInstractionLabel(String nameDepartmentInstraction, Integer size, Color color) {

        instractionLabel = new Label(nameDepartmentInstraction);
        instractionLabel.setFont(new Font("Arial", size));
        instractionLabel.setTextFill(color);
        root.getChildren().add(instractionLabel);

    }
//
    public void createInstractionForDepartment(String nameFile, Integer size, Color color) throws IOException {

        Label label = new Label();

        String stringLabel = new String();

        File file = new File(nameFile);


        FileReader fileReader = new FileReader(file);

        int b;
        while ((b = fileReader.read()) != -1) {
            stringLabel += (char) b;
        }
        label.setText(stringLabel);
        label.setFont(new Font("Arial", size));
        label.setTextFill(color);

        root.getChildren().add(label);
    }

}
