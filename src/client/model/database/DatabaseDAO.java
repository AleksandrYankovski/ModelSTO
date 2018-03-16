package client.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Александр on 26.09.2017.
 */
public abstract class DatabaseDAO<K,E> {


    private Connection connection;


    public DatabaseDAO() throws SQLException {
        connection = ConnectorDB.getConnection();
    }


    public abstract List<E> getAll() throws SQLException;

    public abstract E update(E entity);

    public abstract E get(K id);

    public abstract Boolean delete(K id) throws SQLException;

    public abstract Boolean create(E entity) throws SQLException;

    public PreparedStatement getPrepareStatement(String sgl) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sgl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
