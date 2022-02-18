package lehska.newwordhelper.database;

import java.sql.*;

public class DataBaseHandler {

    private static DataBaseHandler dataBaseHandler = null;

    private static final String url = "jdbc:mysql://localhost:3306/word_helper";
    private static final String username = "root";
    private static final String password = "password";

    private static Connection connection;
    //RealEstateAgency

    private DataBaseHandler() {
        createConnection();
    }


    private void createConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection succeed");
        } catch (SQLException e) {
            System.out.println("cannot connect to sqlServer with: " + url + " " + username + " " + password);
        }

    }

    public static DataBaseHandler getInstance() {
        if (dataBaseHandler == null) {
            dataBaseHandler = new DataBaseHandler();
        }
        return dataBaseHandler;
    }


    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException exception) {
            System.out.println("Error in executeQuery method");
            return null;
        }
    }

    public boolean executeAction(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException exception) {
            System.out.println("Error in executeAction method");
            return false;
        }
        return true;
    }

}
