package client.controler;

import client.model.*;
import client.model.Client.Adres;
import client.model.database.BlackRecordsTable;
import client.model.database.ClientTable;
import client.model.database.DiscontCardTable;
import client.model.database.RecordTable;
import client.view.MainClientDialog;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Александр on 13.09.2017.
 */
public class ClientControler {
    private MainClientDialog mainView;
    private ClientTable clientTable;
    private BlackRecordsTable blackListRecordTable;
    private RecordTable recordTable;
    private DiscontCardTable discontCardTable;

    public ClientControler() throws SQLException, ClassNotFoundException, FileNotFoundException, ParseException {

        clientTable = new ClientTable();

        recordTable = new RecordTable();

        blackListRecordTable = new BlackRecordsTable();

        discontCardTable = new DiscontCardTable();

        mainView = new MainClientDialog(this);
    }

    public void entry(Integer id) throws SQLException, ParseException {

        ArrayList<BlackRecord> blackList = recordTable.update();

        for (BlackRecord blackRecord : blackList)
            blackListRecordTable.create(blackRecord);

        Boolean bool = clientTable.isExist(id);
        if (blackListRecordTable.getIdClientForAddInBlackCLientList().contains(id))
            bool = null;
        mainView.getRedactionClientDialog().answerToConfirm(bool);
    }

    public void addClient(Collection<TextField> fioCollection, Collection<TextField> adresCollection, String photoPass)
            throws SQLException, IOException {


        ArrayList<TextField> arrayListFio = new ArrayList<>();
        arrayListFio.addAll(fioCollection);

        ArrayList<TextField> arrayListAdres = new ArrayList<>();
        arrayListAdres.addAll(adresCollection);

        Client client = new Client();


        client.setName(arrayListFio.get(0).getText());
        client.setSurname(arrayListFio.get(1).getText());
        client.setPatronymic(arrayListFio.get(2).getText());

        client.setPhoto(ImageIO.read(new File(photoPass)));

        Adres adres = new Adres();

        adres.setCountry(arrayListAdres.get(0).getText());
        adres.setCity(arrayListAdres.get(1).getText());
        adres.setStreet(arrayListAdres.get(2).getText());
        adres.setHouse(arrayListAdres.get(3).getText());
        adres.setFlat(Integer.valueOf(arrayListAdres.get(4).getText()));
        client.activateDiscontCard();
        client.setAdres(adres);

        discontCardTable.add(client.getDiscontCard());


        clientTable.add(client);


    }


    public void addRecord(Collection<TextField> collectionCar, Failure failure, LocalDate localDate,
                          LocalTime localTime, Boolean paid, Integer idClient) throws SQLException, ParseException {

        Record record = new Record();

        ArrayList<TextField> textFieldCar = new ArrayList<>();
        textFieldCar.addAll(collectionCar);

        record.getCarModel().setMark(textFieldCar.get(0).getText());
        record.getCarModel().setModel(textFieldCar.get(1).getText());
        record.getCarModel().setYear(Integer.valueOf(textFieldCar.get(2).getText()));
        record.getCarModel().setBulk(Double.valueOf(textFieldCar.get(3).getText()));
        record.getCarModel().setNumberStateRegistration(textFieldCar.get(4).getText());
        record.getCarModel().setColor(textFieldCar.get(5).getText());
        record.setFailure(failure);
        record.setDate(localDate);
        record.setTime(localTime);
        record.getPayment().setPrice(record.getFailure().getPrice());


        DiscontCard discontCard = discontCardTable.getDiscontCard(clientTable.getClientById(idClient).getDiscontCard().getId());
        discontCard.setAccumulationPercentage(clientTable.getKolSuccessfullyRecordsByClient(idClient)/3);


        record.getPayment().setPay(record.getPayment().getPrice(),
                discontCard);
        record.getPayment().setStatus(paid);
        record.setIdClient(idClient);


        recordTable.add(record);

        clientTable.icreaseKolSeccessfullRecordsByClient(idClient);

        discontCardTable.update(clientTable.getKolSuccessfullyRecordsByClient(idClient),
                clientTable.getClientById(idClient).getDiscontCard().getId());

        mainView.getRedactionClientDialog().getRecordsTable().refresh();

    }


    public void deleteRecord(Integer key) throws SQLException {

        discontCardTable.update(discontCardTable.getDiscontCard(clientTable.
                        getClientById(recordTable.getIdCLinent(key)).getDiscontCard().getId()).getAccumulationPercentage() / 2
                , discontCardTable.getDiscontCard(clientTable.
                        getClientById(recordTable.getIdCLinent(key)).getDiscontCard().getId()).getId());

        clientTable.setKolSeccessfullRecordsByClient(recordTable.getIdCLinent(key),clientTable.
                getKolSuccessfullyRecordsByClient(recordTable.getIdCLinent(key))/2);

        recordTable.delete(key);

        mainView.getRedactionClientDialog().getRecordsTable().refresh();
    }


    public ClientTable getClientDatabase() {
        return clientTable;
    }

    public RecordTable getRecordDatabse() {
        return recordTable;
    }

}