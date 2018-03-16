package client.model.database;

import client.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Александр on 26.09.2017.
 */
public class ClientDatabase<K, E> extends DatabaseDAO<K, E> {


    private final static String SQL_ID_IN_CLIENT =
            "SELECT id FROM client WHERE id=?";
    private final static String SQL_CREATE_CLIENT =
            "INSERT INTO client(name,surmame,patronymic,country,city,street,house,flat,photo) VALUES(?,?,?,?,?,?,?,?,?)";


    public ClientDatabase() throws SQLException {
    }

    @Override
    public List<E> getAll() {
        return null;
    }

    @Override
    public E update(E entity) {
        return null;
    }

    @Override
    public E get(K id) {
        return null;
    }

    @Override
    public Boolean delete(K id) {
        return null;
    }

    @Override
    public Boolean create(E entity) throws SQLException {

        Client client = (Client) entity;

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_CREATE_CLIENT);

        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getAdres().getCountry());
        preparedStatement.setString(5, client.getAdres().getCity());
        preparedStatement.setString(6, client.getAdres().getStreet());
        preparedStatement.setString(7, client.getAdres().getHouse());
        preparedStatement.setString(8, String.valueOf(client.getAdres().getFlat()));
        preparedStatement.setString(9, client.getPhoto());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public Boolean isExist(String id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_ID_IN_CLIENT);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }


}
