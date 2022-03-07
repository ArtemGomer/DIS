package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    private static DatabaseManager instance = null;

    public static DatabaseManager getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new DatabaseManager();
            return instance;
        }
        return instance;
    }

    private DatabaseManager() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    private Connection connection = null;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void createSchema() throws IOException, SQLException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db_script.sql");
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        StringBuilder sql_string = new StringBuilder();
        String sql_line;
        while ((sql_line = bufferedReader.readLine()) != null) {
            sql_string.append(sql_line);
            sql_string.append(" ");
        }
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql_string.toString());
        inputReader.close();
        inputStream.close();
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
