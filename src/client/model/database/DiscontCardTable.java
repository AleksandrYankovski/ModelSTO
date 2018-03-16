package client.model.database;

import client.model.DiscontCard;

import java.sql.*;

public class DiscontCardTable {


    private Connection connection;
    private final static String SQL_INSERT_DISCONT =
            "INSERT INTO discontCard(accumulationPercentage)" +
                    " VALUES(?)";
    private final static String SQL_GET_DISCONT_CARD = "SELECT * FROM discontCard WHERE id=? ";
    private final static String SQL_UPDATE_DISCONT_CARD = "UPDATE discontCard set accumulationPercentage=? WHERE id=?";


    public DiscontCardTable() throws SQLException {
        connection = ConnectorDB.getConnection();
    }


    public Boolean add(DiscontCard discontCard) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_DISCONT);

        preparedStatement.setInt(1, discontCard.getAccumulationPercentage());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }

    public Boolean update(Integer accumulationPercentage, Integer id) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_UPDATE_DISCONT_CARD);

        preparedStatement.setInt(1, accumulationPercentage);
        preparedStatement.setInt(2, id);


        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public DiscontCard getDiscontCard(Integer id) {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_DISCONT_CARD);

        DiscontCard discontCard = new DiscontCard();
        try {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();


            discontCard.setAccumulationPercentage(resultSet.getInt(1));

            discontCard.setId(resultSet.getInt(2));

        } catch (SQLException e) {
            discontCard.setAccumulationPercentage(0);
        }

        return discontCard;
    }

    private PreparedStatement getPrepareStatement(String sgl) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sgl);
        } catch (SQLException e) {
            e.printStackTrace();//Окошки выкидывать
        }

        return preparedStatement;
    }


}
