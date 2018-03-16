package client.view.redactionDialog;

import client.controler.ClientControler;
import client.model.Car;
import client.model.Record;
import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by Александр on 01.10.2017.
 */
public class RecordsTable extends TableView {


    private ClientControler clientControler;
    private ObservableList observableList;
    private Integer foreignKey;
    private LinkedHashMap<String, TableColumn> linkedHashMapTableColumns;


    public RecordsTable(ClientControler clientControler) throws SQLException {

        this.clientControler = clientControler;


        observableList = FXCollections.observableArrayList();

        this.setItems(observableList);

        linkedHashMapTableColumns = new LinkedHashMap<>();

        linkedHashMapTableColumns.put("Car", new TableColumn<Record, String>("Car"));
        linkedHashMapTableColumns.put("Failure", new TableColumn<Record, String>("Failure"));
        linkedHashMapTableColumns.put("Date", new TableColumn<Record, LocalDate>("Date"));
        linkedHashMapTableColumns.put("Time", new TableColumn<Record, LocalTime>("Time"));
        linkedHashMapTableColumns.put("Payment", new TableColumn<Record, Boolean>("Payment"));
        linkedHashMapTableColumns.put("Id", new TableColumn<Record, Integer>("Id"));


        createColumnForRecord(linkedHashMapTableColumns.get("Car"), "carModel");
        createColumnForRecord(linkedHashMapTableColumns.get("Failure"), "failure");
        createColumnForRecord(linkedHashMapTableColumns.get("Date"), "date");
        createColumnForRecord(linkedHashMapTableColumns.get("Time"), "time");
        createColumnForRecord(linkedHashMapTableColumns.get("Payment"), "payment");
        createColumnForRecord(linkedHashMapTableColumns.get("Id"), "id");


        this.getColumns().addAll(linkedHashMapTableColumns.values());


    }

    public void refresh() {
        try {
            observableList.clear();
            observableList.addAll(clientControler.getRecordDatabse().getAllByIdClient(Integer.valueOf(foreignKey)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private <Type, FieldType> void createColumnForRecord(TableColumn<Type, FieldType> tableColumn, String nameField) {

        tableColumn.setMinWidth(180);
        tableColumn.setCellValueFactory(
                new PropertyValueFactory<Type, FieldType>(nameField));

    }

    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

}

