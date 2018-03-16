package client.model.database;

import client.model.Client;
import client.model.Record;
import com.mysql.cj.jdbc.Blob;
import com.mysql.cj.jdbc.Clob;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 26.09.2017.
 */
public class ClientTable {


    private Connection connection;
    private final static String SQL_ID_IN_CLIENT =
            "SELECT id FROM client WHERE id=?";
    private final static String SQL_CREATE_CLIENT =
            "INSERT INTO client(name,surname,patronymic,country,city,street,house,flat,photo,idDiscontCard) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?)";
    private final static String SQL_KOL_RECORDS = "SELECT kolSuccessfullyRecords from client where id=?";
    private final static String SQL_ICREASE_KOL_RECORDS = "UPDATE client set kolSuccessfullyRecords=kolSuccessfullyRecords+1" +
            " WHERE id=?";
    private final static String SQL_GET_CLIENT_BY_ID = "SELECT * FROM client WHERE id=?";
    private final static String SQL_SET_KOL_RECORDS = "UPDATE client set kolSuccessfullyRecords=?" +
            " WHERE id=?";

    public ClientTable() throws SQLException {
        connection = ConnectorDB.getConnection();
    }

    public Integer getKolSuccessfullyRecordsByClient(Integer id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_KOL_RECORDS);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getInt(1);
    }

    public Boolean setKolSeccessfullRecordsByClient(Integer id, Integer kol) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_SET_KOL_RECORDS);

        preparedStatement.setInt(1, kol);
        preparedStatement.setInt(2, id);

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }

    public Client getClientById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_CLIENT_BY_ID);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Client client = new Client();

        resultSet.next();

        client.setName(resultSet.getString(1));
        client.setSurname(resultSet.getString(2));
        client.setPatronymic(resultSet.getString(3));

        Client.Adres adres = new Client.Adres();
        client.setAdres(adres);

        adres.setCountry(resultSet.getString(4));
        adres.setCity(resultSet.getString(5));
        adres.setStreet(resultSet.getString(6));
        adres.setHouse(resultSet.getString(7));
        adres.setFlat(resultSet.getInt(8));

        // client.setPhoto(resultSet.getBigDecimal(9));

        client.activateDiscontCard(resultSet.getInt(11));

        return client;
    }

    public Boolean icreaseKolSeccessfullRecordsByClient(Integer id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_ICREASE_KOL_RECORDS);

        preparedStatement.setInt(1, id);

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public Boolean add(Client client) throws SQLException, IOException {


        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_CREATE_CLIENT);

        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getAdres().getCountry());
        preparedStatement.setString(5, client.getAdres().getCity());
        preparedStatement.setString(6, client.getAdres().getStreet());
        preparedStatement.setString(7, client.getAdres().getHouse());
        preparedStatement.setInt(8, client.getAdres().getFlat());

        Blob blob = (Blob) ConnectorDB.getConnection().createBlob();
        OutputStream outputStream = blob.setBinaryStream(1);
        ImageIO.write(client.getPhoto(), "jpg", outputStream);

        preparedStatement.setBlob(9, blob);
        preparedStatement.setInt(10, client.getDiscontCard().getId());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public Boolean isExist(Integer id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_ID_IN_CLIENT);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }


    private PreparedStatement getPrepareStatement(String sgl) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sgl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }


}
