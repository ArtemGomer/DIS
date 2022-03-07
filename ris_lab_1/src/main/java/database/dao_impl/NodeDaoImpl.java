package database.dao_impl;

import database.dao_api.NodeDao;
import generated.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NodeDaoImpl implements NodeDao {


    private final Connection connection;

    public NodeDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addNodeStatement(Node node) {
        try {
            String sql_query = "INSERT INTO nodes (id, username, longitude, latitude) VALUES (" +
                    node.getId().intValue() + ", " +
                    node.getUser() + ", " +
                    node.getLon() + ", " +
                    node.getLat() + ");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql_query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean addNodePreparedStatement(Node node) {
        try {
            String sql_query = "INSERT INTO nodes (id, username, longitude, latitude) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            preparedStatement.setInt(1, node.getId().intValue());
            preparedStatement.setString(2, node.getUser());
            preparedStatement.setDouble(3, node.getLon());
            preparedStatement.setDouble(4, node.getLat());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    @Override
    public boolean addNodeBatch(List<Node> nodes) {
        try {
            String sql_query = "INSERT INTO nodes (id, username, longitude, latitude) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            for (Node node: nodes) {
                preparedStatement.setInt(1, node.getId().intValue());
                preparedStatement.setString(2, node.getUser());
                preparedStatement.setDouble(3, node.getLon());
                preparedStatement.setDouble(4, node.getLat());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

}
