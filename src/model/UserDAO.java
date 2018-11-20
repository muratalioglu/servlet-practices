package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private Connection connection;
    private final String connectionString = "jdbc:mysql://localhost/tododb?user=root&password=1D952tnZ";
    
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public boolean login(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE name = ?;");
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String passwordFromDB = resultSet.getString("password");
                
                if (passwordFromDB != null) {
                    if (passwordFromDB.equals(user.getPassword())) {
                        return true;
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
