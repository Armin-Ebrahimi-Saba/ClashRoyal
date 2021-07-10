package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.DBConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!isDataBaseConnected()) {
            System.exit(1);
        }
    }

    public boolean isDataBaseConnected() {
        return this.connection != null;
    }

    public boolean validateUsernameAndPassword(String username,
                                               String password) throws Exception {
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;

        String command = "SELECT * FROM login where username = ? and password = ?";

        try {
            preparedStatement = this.connection.prepareStatement(command);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (resultSet != null)
                resultSet.close();
        }
    }
}
