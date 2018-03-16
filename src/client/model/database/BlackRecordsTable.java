package client.model.database;

import client.model.BlackRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlackRecordsTable {


    private Connection connection;
    private final static String SQL_INSERT_RECORD =
            "INSERT INTO blackRecords(idCLient,idRecord)" +
                    " VALUES(?,?)";
    private final static String SQL_DELETE_RECORDS =
            "DELETE FROM blackRecords WHERE idCLient=?";
    private final static String SQL_GET_ID_CLIINT_FOR_ADD_IN_BLACK_CLIENT_LIST = "SELECT IdClient from blackRecords" +
            " GROUP BY IdClient HAVING COUNT(IdRecord)>=3";

    public BlackRecordsTable() throws SQLException {
        connection = ConnectorDB.getConnection();
    }

    public Boolean create(BlackRecord blackRecord) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_RECORD);


        preparedStatement.setInt(1, blackRecord.getIdClient());
        preparedStatement.setInt(2, blackRecord.getIdRecord());


        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }


    public Boolean delete(Integer idCLient) throws SQLException {


        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_DELETE_RECORDS);
        preparedStatement.setInt(1, idCLient);

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public ArrayList<Integer> getIdClientForAddInBlackCLientList() throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_ID_CLIINT_FOR_ADD_IN_BLACK_CLIENT_LIST);

        ArrayList<Integer> arrayList = new ArrayList<>();

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            arrayList.add(resultSet.getInt(1));
        }
        return arrayList;
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
