package client.model.database;

import client.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 26.09.2017.
 */
public class RecordDatabse<K, E> extends DatabaseDAO<K, Record> {

    private final static String SQL_DELETE_RECORD =
            "DELETE FROM record WHERE id=?";
    private final static String SQL_INSERT_RECORD =
            "INSERT INTO record(mark,model,year,bulk,numberStateRegistration,color,failure,date,time,idClient)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?)";
    private final static String SQL_GET_ALL_BY_CLIENT = "SELECT * FROM record WHERE idClient=? ORDER BY date";


    public RecordDatabse() throws SQLException {
    }

    @Override
    public List<Record> getAll() throws SQLException {
        return null;
    }

    @Override
    public Record update(Record entity) {
        return null;
    }

    @Override
    public Record get(K id) {
        return null;
    }

    @Override
    public Boolean delete(K id) throws SQLException {

        Integer integerId = (Integer) id;

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_DELETE_RECORD);
        preparedStatement.setString(1, integerId.toString());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    @Override
    public Boolean create(Record entity) throws SQLException {

        Record record = entity;

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_RECORD);

        preparedStatement.setString(1, record.getCarModel().getMark());
        preparedStatement.setString(2, record.getCarModel().getModel());
        preparedStatement.setInt(3, record.getCarModel().getYear());
        preparedStatement.setDouble(4, record.getCarModel().getBulk());
        preparedStatement.setString(5, record.getCarModel().getNumberStateRegistration());
        preparedStatement.setString(6, record.getCarModel().getColor());
        preparedStatement.setString(7, record.getTypeOfFailure());
        preparedStatement.setDate(8, Date.valueOf(record.getDate()));
        preparedStatement.setTime(9, Time.valueOf(record.getTime()));
        preparedStatement.setInt(10, Integer.parseInt(record.getIdClient()));

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
            record.setTypeOfFailure(resultSet.getString(7));
            record.setDate(resultSet.getDate(8).toLocalDate());
            record.setTime(resultSet.getTime(9).toLocalTime());
            record.setIdClient(resultSet.getString(10));
            record.setId(resultSet.getString(11));

            arrayList.add(record);

        }

        return arrayList;

    }

}
