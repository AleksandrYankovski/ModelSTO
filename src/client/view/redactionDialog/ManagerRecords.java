package client.view.redactionDialog;

import client.controler.ClientControler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.text.ParseException;

/**
 * Created by Александр on 29.09.2017.
 */
public class ManagerRecords extends GridPane {


    private Integer foreignKey;
    private ClientControler clientControler;
    private Button addRecordButton;
    private Button deleteRecordButton;
    private DeleteRecordDialog deleteRecordDialog;
    private CreateRecordDialog addRecordDialog;


    public ManagerRecords(ClientControler clientControler) throws ParseException {


        this.clientControler = clientControler;

        this.setAlignment(Pos.CENTER);
        this.setVgap(70);
        this.setPadding(new Insets(25, 25, 25, 25));
        this.setStyle("-fx-border-color:black");

        createAddRecordButton();
        createDeleteRecordButton();

        this.add(addRecordButton, 0, 0);
        this.add(deleteRecordButton, 0, 1);

    }

    private void createAddRecordButton() throws ParseException {

        addRecordButton = new Button("Добавить");
        addRecordButton.setOnAction((ae) -> {
            try {
                createRecord();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        addRecordButton.setPrefSize(180, 20);
    }


    private void createDeleteRecordButton() {

        deleteRecordButton = new Button("Удалить");
        deleteRecordButton.setOnAction((ae) -> {
            deleteRecord();
        });

        deleteRecordButton.setPrefSize(180, 20);
    }


    public DeleteRecordDialog getDeleteRecordDialog() {
        return deleteRecordDialog;
    }

    public CreateRecordDialog getAddRecordDialog() {
        return addRecordDialog;
    }


    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

    private void createRecord() throws ParseException {
        addRecordDialog = new CreateRecordDialog(clientControler, foreignKey);
    }


    private void deleteRecord() {
        deleteRecordDialog = new DeleteRecordDialog(clientControler);
        deleteRecordDialog.inputId();

    }


}
