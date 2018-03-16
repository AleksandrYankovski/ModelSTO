package client.model.database;

import client.model.BlackRecord;
import client.model.Record;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Александр on 26.09.2017.
 */
public class RecordTable {


    private Connection connection;
    private final static String SQL_DELETE_RECORDS =
            "DELETE FROM record WHERE id=?";
    private final static String SQL_INSERT_RECORD =
            "INSERT INTO record(mark,model,year,bulk,numberStateRegistration,color,failure,money,date,time,price,pay,paid,idCLient)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String SQL_GET_ALL_BY_CLIENT = "SELECT * FROM record WHERE idClient=? ORDER BY date,time";
    private final static String SQL_GET_ID_CLIENT_BY_RECORD = "SELECT idCLient FROM record WHERE id=?";
    private final static String SQL_GET_OVERDUE_RECORDS = "SELECT idClient,id FROM record WHERE (CURDATE()>date " +
            "OR (CURDATE()=date AND CURTIME()>time))";
    private final static String SQL_DELETE_OVERDUE_RECORDS = "DELETE  FROM record WHERE (CURDATE()>date " +
            "OR (CURDATE()=date AND CURTIME()>time))";


    public RecordTable() throws SQLException {
        connection = ConnectorDB.getConnection();
    }


/*    public Record get(Integer id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_RECORD);

        preparedStatement.setInt(1, (Integer) id);

        Record record = new Record();

        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {


            record.getCarModel().setMark(resultSet.getString(1));
            record.getCarModel().setModel(resultSet.getString(2));
            record.getCarModel().setYear(resultSet.getInt(3));
            record.getCarModel().setBulk(resultSet.getDouble(4));
            record.getCarModel().setNumberStateRegistration(resultSet.getString(5));
            record.getCarModel().setColor(resultSet.getString(6));
            record.getFailure().setTypeOfFailure(resultSet.getString(7));
            record.setDate(resultSet.getDate(8).toLocalDate());
            record.setTime(resultSet.getTime(9).toLocalTime());
            record.setIdClient(resultSet.getInt(10));
            record.setId(resultSet.getInt(11));
        }

        return record;
    }
*/

    public Boolean delete(Integer id) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_DELETE_RECORDS);
        preparedStatement.setInt(1, id);

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public Integer getIdCLinent(Integer id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_ID_CLIENT_BY_RECORD);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getInt(1);
    }

    public Boolean add(Record record) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_RECORD);

        preparedStatement.setString(1, record.getCarModel().getMark());
        preparedStatement.setString(2, record.getCarModel().getModel());
        preparedStatement.setInt(3, record.getCarModel().getYear());
        preparedStatement.setDouble(4, record.getCarModel().getBulk());
        preparedStatement.setString(5, record.getCarModel().getNumberStateRegistration());
        preparedStatement.setString(6, record.getCarModel().getColor());
        preparedStatement.setString(7, record.getFailure().getTypeOfFailure());
        preparedStatement.setInt(8, record.getFailure().getPrice());
        preparedStatement.setDate(9, Date.valueOf(record.getDate()));
        preparedStatement.setTime(10, Time.valueOf(record.getTime()));
        preparedStatement.setInt(11, record.getPayment().getPrice());
        preparedStatement.setInt(12, record.getPayment().getPay());
        preparedStatement.setBoolean(13, record.getPayment().getStatus());
        preparedStatement.setInt(14, record.getIdClient());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }

    public ArrayList<Record> getAllByIdClient(Integer idClient) throws SQLException {

        ArrayList<Record> arrayList = new ArrayList<>();

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_ALL_BY_CLIENT);

        preparedStatement.setInt(1, idClient);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Record record = new Record();

            record.getCarModel().setMark(resultSet.getString(1));
            record.getCarModel().setModel(resultSet.getString(2));
            record.getCarModel().setYear(resultSet.getInt(3));
            record.getCarModel().setBulk(resultSet.getDouble(4));
            record.getCarModel().setNumberStateRegistration(resultSet.getString(5));
            record.getCarModel().setColor(resultSet.getString(6));
            record.getFailure().setTypeOfFailure(resultSet.getString(7));
            record.getFailure().setPrice(resultSet.getInt(8));
            record.setDate(resultSet.getDate(9).toLocalDate());
            record.setTime(resultSet.getTime(10).toLocalTime());
            record.getPayment().setPrice(resultSet.getInt(11));
            record.getPayment().setPay(resultSet.getInt(12));
            record.getPayment().setStatus(resultSet.getBoolean(13));
            record.setIdClient(resultSet.getInt(14));
            record.setId(resultSet.getInt(15));

            arrayList.add(record);

        }
        return arrayList;
    }

    public ArrayList<BlackRecord> update() throws SQLException {

        ArrayList<BlackRecord> blackList = new ArrayList<>();

        PreparedStatement preparedStatementToGetOverDueRecords = this.getPrepareStatement(SQL_GET_OVERDUE_RECORDS);
        PreparedStatement preparedStatementToDeleteOverdueRecodrs = this.getPrepareStatement(SQL_DELETE_OVERDUE_RECORDS);


        ResultSet resultSet = preparedStatementToGetOverDueRecords.executeQuery();


        while (resultSet.next()) {

            BlackRecord blackRecord = new BlackRecord();

            blackRecord.setIdClient(resultSet.getInt(1));
            blackRecord.setIdRecord(resultSet.getInt(2));

            blackList.add(blackRecord);
        }

        preparedStatementToDeleteOverdueRecodrs.executeUpdate();

        return blackList;
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
