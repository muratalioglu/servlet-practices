package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

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
        final String query = "SELECT password FROM users WHERE name = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String actualPassword = resultSet.getString("password");
                
                if (actualPassword != null && 
                    actualPassword.equals(user.getPassword())) {
                    return true;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean register(User user) {
        final String query = "SELECT * FROM users WHERE name = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                statement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?);");
                statement.setString(1, user.getName());
                statement.setString(2, user.getPassword());                
                return statement.executeUpdate() == 1;                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
