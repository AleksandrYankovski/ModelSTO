package client.view.redactionDialog;

import client.controler.ClientControler;
import client.model.Record;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

/**
 * Created by Александр on 14.09.2017.
 */
public class RedactionClientDialog extends Stage {

    //Переписать

    private ClientControler clientControler;
    private Integer foreignKey;
    private BorderPane root;
    private Scene scene;
    private ManagerRecords managerRecords;
    private RecordsTable recordsTable;


    public RedactionClientDialog(ClientControler clientControler) throws SQLException, ParseException {

        this.clientControler = clientControler;

        root = new BorderPane();
        root.setStyle("-fx-background-color: orange");

        scene = new Scene(root, 1320, 580);

        managerRecords = new ManagerRecords(clientControler);
        root.setLeft(managerRecords);

        recordsTable = new RecordsTable(clientControler);
        root.setCenter(recordsTable);

        this.setScene(scene);

    }

    public void inputIdCLient() throws SQLException, ParseException {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your id:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            foreignKey = Integer.valueOf(result.get());
            clientControler.entry(Integer.valueOf(foreignKey));
        }
    }

    public void answerToConfirm(Boolean bool) throws SQLException {
        if (bool == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Вы добавленны в черный список");
            alert.showAndWait();


        } else if (bool.equals(true)) {

            managerRecords.setForeignKey(foreignKey);
            recordsTable.setForeignKey(foreignKey);
            recordsTable.refresh();
            this.show();


        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Client don't exist");
            alert.showAndWait();

            this.close();

        }
    }


    public ManagerRecords getManagerRecords() {
        return managerRecords;
    }

    public RecordsTable getRecordsTable() {
        return recordsTable;
    }
}
