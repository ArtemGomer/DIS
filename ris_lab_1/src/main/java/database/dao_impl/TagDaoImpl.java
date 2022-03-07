package database.dao_impl;

import database.dao_api.TagDao;
import generated.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TagDaoImpl implements TagDao {


    private final Connection connection;

    public TagDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addTagStatement(Tag tag) {
        try {
            String sql_query = "INSERT INTO nodes (key, value) VALUES (" +
                    tag.getK() + ", " +
                    tag.getV() + ");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql_query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean addTagPreparedStatement(Tag tag) {
        try {
            String sql_query = "INSERT INTO nodes (key, value) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            preparedStatement.setString(1, tag.getK());
            preparedStatement.setString(2, tag.getV());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean addTagBatch(List<Tag> tags) {
        try {
            String sql_query = "INSERT INTO nodes (key, value) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            for (Tag tag: tags) {
                preparedStatement.setString(1, tag.getK());
                preparedStatement.setString(2, tag.getV());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
